package sandbox.pdfbuilder.fo.freemarker;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

import sandbox.pdfbuilder.util.StaticMap;

@Slf4j
@Deprecated
public class FreemarkerRegexMarkupUtils
{
    private static final FluentList<MarkupHandler> MARKUP_HANDLERS = FluentList.<FreemarkerRegexMarkupUtils.MarkupHandler>with()
            .item(new ReplaceAll("(?i)<br[\\s/]*>", "<fo:block linefeed-treatment=\"preserve\" line-height=\"0.5\">&#xA;</fo:block>"))
            .item(new ReplaceAll("(?i)<p>(.*?)</p>", "<fo:block>$1</fo:block>"))
            //.item(new ReplaceAll("(?i)<ul>(.*?)</ul>", "<fo:list-block>$1</fo:list-block>"))
            //.item(new ReplaceAll("(?i)<li>(.*?)</li>", "<fo:list-item><fo:list-item-label margin-left=\"5mm\"><fo:block><fo:inline>&#x2022;</fo:inline></fo:block></fo:list-item-label><fo:list-item-body margin-left=\"5mm\" padding-left=\"5mm\"><fo:block linefeed-treatment=\"preserve\" keep-together.within-page=\"always\" text-align=\"justify\">$1</fo:block></fo:list-item-body></fo:list-item>"))
            .item(new FontHandler())
            .item(new UnorderedListHandler())
            .item(new BoldHandler())
            .item(new ReplaceAll("(?i)<i>(.*?)</i>", "<fo:inline font-style=\"italic\">$1</fo:inline>"))
            .item(new ReplaceAll("(?i)<sub>(.*?)</sub>", "<fo:inline vertical-align=\"sub\" font-size=\"75%\">$1</fo:inline>"))
            .item(new ReplaceAll("(?i)<sup>(.*?)</sup>", "<fo:inline vertical-align=\"super\" font-size=\"50%\">$1</fo:inline>"))
            .item(new ReplaceAll("(?i)<u>(.*?)</u>", "<fo:inline text-decoration=\"underline\">$1</fo:inline>"))
            .item(new ReplaceAll("(?i)<a\\s+href=\"(.*?)\">(.*?)</a>", "<fo:basic-link external-destination=\"uri('$1')\">$2</fo:basic-link>"));
    
    public static String process(final String value)
    {
        String retVal = value;
        
        if (StringUtils.isNotBlank(retVal))
        {
            for (MarkupHandler handler : MARKUP_HANDLERS)
            {
                retVal = handler.process(retVal);
            }
        }

        log.debug("PROCESSED_MARKUP=[" + retVal + "]");
        
        return retVal;
    }
    
    private static class FluentList<V> extends ArrayList<V>
    {
        private static final long serialVersionUID = 1L;

        public static <V> FluentList<V> with()
        {
            return new FluentList<V>();
        }
        
        public FluentList<V> item(V item)
        {
            super.add(item);
            return this;
        }
    }
    
    private static String findFirst(String string, String pattern, String defaultValue)
    {
        if (string == null || pattern == null) return defaultValue;
        Matcher matcher = Pattern.compile(pattern).matcher(string);
        return matcher.find() ? matcher.group() : defaultValue;
    }
    
    private interface MarkupHandler
    {
        String process(final String value);
    }
    
    @AllArgsConstructor
    private static class ReplaceAll implements MarkupHandler
    {
        private String find;
        private String replace;

        @Override
        public String process(String value)
        {
            return value.replaceAll(find, replace);
        }
    }
    
    private static class BoldHandler implements MarkupHandler
    {
        private static final Pattern PAT = Pattern.compile("(?i)(<b.*?>)(.*?)</b>");
        
        @Override
        public String process(String value)
        {
            Matcher mat = PAT.matcher(value);
            
            StringBuffer sb = new StringBuffer();
            while (mat.find())
            {
                String colour = findFirst(mat.group(1), "color=\".*?\"", "");
                mat.appendReplacement(sb, String.format("<fo:inline %s font-weight=\"bold\">%s</fo:inline>", colour, mat.group(2)));
            }
            mat.appendTail(sb);
            
            return sb.toString();
        }
    }
    
    private static class FontHandler implements MarkupHandler
    {
        private static final Pattern PAT = Pattern.compile("(?i)(<font.*?>(.*?)</font>)");

        private final Map<String, String> defaultAttr = StaticMap.<String, String>with()
                                                                 .keyValue("color", null)
                                                                 .keyValue("font-size", null)
                                                                 .keyValue("font-weight", null)
                                                                 .keyValue("font-family", null);
        
        @Override
        public String process(String value)
        {
            log.debug("FontHander=[Input=[" + value + "]]");
            
            Matcher mat = PAT.matcher(value);
            
            StringBuffer sb = new StringBuffer();
            while (mat.find())
            {
                Tag font = HtmlParser2.html(mat.group(1)).findFirst("font");
                
                if (font != null)
                {
                    font.getAttr(defaultAttr);
                    String fontAttr = defaultAttr.entrySet().stream().filter(e -> e.getValue() != null).map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
                    mat.appendReplacement(sb, String.format("<fo:inline %s>%s</fo:inline>", fontAttr, mat.group(2)));
                }
            }
            mat.appendTail(sb);

            String retVal = sb.toString();

            log.debug("FontHander=[Output=[" + retVal + "]]");

            return retVal;
        }

    }
    
    private static class UnorderedListHandler implements MarkupHandler
    {
        private static final Pattern PAT = Pattern.compile("(?i)(<ul.*?>.*?</ul>)");
        
        private final Map<String, String> defaultAttr = StaticMap.<String, String>with()
                                                                 .keyValue("bullet", "&#x2022;")
                                                                 .keyValue("bullet-size", "11pt")
                                                                 .keyValue("bullet-colour", "black")
                                                                 .keyValue("bullet-space-before", "0mm")
                                                                 .keyValue("bullet-space-after", "5mm");
        
        @Override
        public String process(String value)
        {
            log.debug("UnorderedListHandler=[Input=[" + value + "]]");

            Matcher mat = PAT.matcher(value);
            
            StringBuffer sb = new StringBuffer();
            while(mat.find())
            {
                HtmlParser2 markup = HtmlParser2.html(mat.group(1));
                Tag ul = markup.findFirst("ul");
                List<Tag> li = markup.find("li");

                if (ul != null && li.size() > 0)
                {
                    defaultAttr.putAll(ul.attr());
                    
                    StringBuffer fo = new StringBuffer();
                    fo.append("<fo:list-block>");
                    li.forEach(i -> {
                        fo.append(String.format("<fo:list-item><fo:list-item-label margin-left=\"%s\"><fo:block><fo:inline color=\"%s\" font-size=\"%s\">%s</fo:inline></fo:block></fo:list-item-label><fo:list-item-body margin-left=\"%s\" padding-left=\"%s\"><fo:block linefeed-treatment=\"preserve\" keep-together.within-page=\"always\" text-align=\"justify\">%s</fo:block></fo:list-item-body></fo:list-item>", 
                                defaultAttr.get("bullet-space-before"), 
                                defaultAttr.get("bullet-colour"),
                                defaultAttr.get("bullet-size"),
                                defaultAttr.get("bullet"),
                                defaultAttr.get("bullet-space-before"),
                                defaultAttr.get("bullet-space-after"),
                                i.value()));
                    });
                    fo.append("</fo:list-block>");
                    mat.appendReplacement(sb, fo.toString());
                }

            }
            mat.appendTail(sb);
            
            String retVal = sb.toString();

            log.debug("UnorderedListHandler=[Output=[" + retVal + "]]");

            return retVal;
        }
    }

    
    @Data(staticConstructor = "namedNodeMap")
    private static class AttributeParser
    {
        private final NamedNodeMap namedNodeMap;
        
        public Map<String, String> parse(Map<String, String> attrMap)
        {
            if (namedNodeMap != null)
            {
                attrMap.keySet().forEach(k -> {
                    Optional.ofNullable(namedNodeMap.getNamedItem(k)).ifPresent(a -> attrMap.put(k, ((Attr)a).getValue()));
                });
            }
            
            return attrMap;
        }
    }
    
    @Data(staticConstructor = "html")
    private static class HtmlParser2
    {
        private final String html;
        
        public List<Tag> find(String tag)
        {
            List<Tag> tags = new ArrayList<>();
            
            Pattern pattern = Pattern.compile("<" + tag + "(?<attr>.*?)>(?<value>.*?)</" + tag + ">", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(html);

            while(matcher.find())
            {
                tags.add(Tag.name(tag).attr(matcher.group("attr")).value(matcher.group("value")));
            }
            
            return tags;
        }
        
        public Tag findFirst(String tag)
        {
            List<Tag> tags = find(tag);
            return tags.isEmpty() ? null : tags.get(0);
        }
        
    }
    
    @Data(staticConstructor = "name")
    @Accessors(fluent = true)
    private static class Tag
    {
        private final String name;
        private String value;
        private Map<String, String> attr = new HashMap<>();
        
        public Tag attr(String unprocessedAttr)
        {
            if (!StringUtils.isBlank(unprocessedAttr))
            {
                attr = Arrays.asList(StringUtils.trimToEmpty(unprocessedAttr).split(" "))
                             .stream()
                             .map(e -> e.split("="))
                             .filter(e -> e.length == 2)
                             .collect(Collectors.toMap(e -> StringUtils.trimToEmpty(e[0]), e -> StringUtils.trimToEmpty(StringUtils.remove(e[1], '"'))));
            }
            
            return this;
        }
        
        public Map<String, String> getAttr(Map<String, String> defaultAttr)
        {
            defaultAttr.keySet().forEach(k -> {if (attr.containsKey(k)) defaultAttr.put(k, attr.get(k));});
            return defaultAttr;
        }
    }
    

    

    
    
}
