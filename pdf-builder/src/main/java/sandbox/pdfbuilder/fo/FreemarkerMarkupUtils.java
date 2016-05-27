package sandbox.pdfbuilder.fo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

@Slf4j
public class FreemarkerMarkupUtils
{
    private static final FluentList<MarkupHandler> MARKUP_HANDLERS = FluentList.<FreemarkerMarkupUtils.MarkupHandler>with()
            .item(new ReplaceAll("(?i)<br[\\s/]*>", "<fo:block linefeed-treatment=\"preserve\" line-height=\"0.5\">&#xA;</fo:block>"))
            .item(new ReplaceAll("(?i)<p>(.*?)</p>", "<fo:block>$1</fo:block>"))
            .item(new ReplaceAll("(?i)<ul>(.*?)</ul>", "<fo:list-block>$1</fo:list-block>"))
            .item(new ReplaceAll("(?i)<li>(.*?)</li>", "<fo:list-item><fo:list-item-label margin-left=\"5mm\"><fo:block><fo:inline>&#x2022;</fo:inline></fo:block></fo:list-item-label><fo:list-item-body margin-left=\"5mm\" padding-left=\"5mm\"><fo:block linefeed-treatment=\"preserve\" keep-together.within-page=\"always\" text-align=\"justify\">$1</fo:block></fo:list-item-body></fo:list-item>"))
            //.item(new FontHandler())
            .item(new BoldHandler())
            .item(new ReplaceAll("(?i)<i>(.*?)</i>", "<fo:inline font-style=\"italic\">$1</fo:inline>"))
            .item(new ReplaceAll("(?i)<sub>(.*?)</sub>", "<fo:inline vertical-align=\"sub\" font-size=\"75%\">$1</fo:inline>"))
            .item(new ReplaceAll("(?i)<sup>(.*?)</sup>", "<fo:inline vertical-align=\"super\" font-size=\"50%\">$1</fo:inline>"))
            .item(new ReplaceAll("(?i)<u>(.*?)</u>", "<fo:inline text-decoration=\"underline\">$1</fo:inline>"))
            .item(new ReplaceAll("(?i)<a\\s+href=\"(.*?)\">(.*?)</a>", "<fo:basic-link keep-together=\"always\" external-destination=\"uri('$1')\" text-decoration=\"underline\" color=\"rgb(0,112,192)\">$2</fo:basic-link>"));
    
    public static String processMarkup(final String value)
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
        //private static final Pattern PAT = Pattern.compile("(?i)(?<tag>(<b.*?>))(?<value>(.*?))</b>");
        
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
        private static final Pattern PAT = Pattern.compile("(?i)(<font.*?>)(.*?)</font>");
        //private static final Pattern PAT = Pattern.compile("(?i)(?<tag>(<font.*?>))(?<value>(.*?))</font>");

        @Override
        public String process(String value)
        {
            Matcher mat = PAT.matcher(value);
            
            StringBuffer sb = new StringBuffer();
            while (mat.find())
            {
                String colour = findFirst(mat.group(1), "color=\".*?\"", "");
                String fontSize = findFirst(mat.group(1), "font-size=\".*?\"", "");
                String fontWeight = findFirst(mat.group(1), "font-weight=\".*?\"", "");
                String fontFamily = findFirst(mat.group(1), "font-family=\".*?\"", "");
                mat.appendReplacement(sb, String.format("<fo:inline %s %s %s %s>%s</fo:inline>", colour, fontSize, fontWeight, fontFamily, mat.group(2)));
            }
            mat.appendTail(sb);
            
            return sb.toString();
        }

    }
}
