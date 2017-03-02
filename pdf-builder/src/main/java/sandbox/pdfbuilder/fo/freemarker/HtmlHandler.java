package sandbox.pdfbuilder.fo.freemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import lombok.Data;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import freemarker.template.utility.StringUtil;


public class HtmlHandler extends DefaultHandler
{
    private StringBuffer sb = new StringBuffer();
    
    private SaxAttributes parentAttributes = null;
    
    public String result()
    {
        return sb.toString();
    }
    
    @Override
    public void characters(char[] characters, int start, int length) throws SAXException
    {
        sb.append(StringUtil.XMLEnc(new String(characters, start, length)));
        //sb.append(characters, start, length);
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        HtmlElement currentElement = EnumUtils.getEnum(HtmlElement.class, StringUtils.upperCase(qName));
        if (currentElement != null)
        {
            switch(currentElement)
            {
                case LI:
                    break;
                case UL : 
                    parentAttributes = null;
                default:
                    break;
                   
            }
            sb.append(currentElement.endTag());
        }
        currentElement = null;
    }

    @Data(staticConstructor = "with")
    @Accessors(fluent = true)
    private static class SaxAttributes extends FluentMap<String, String>
    {
        public SaxAttributes attributes(Attributes attributes)
        {
            if (attributes != null)
            {
                for (int i = 0; i < attributes.getLength(); i++)
                {
                    this.put(attributes.getQName(i), attributes.getValue(i));
                }
            }
            return this;
        }
        
        public SaxAttributes attribute(String key, String value)
        {
            this.put(key, value);
            return this;
        }
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        HtmlElement currentElement = EnumUtils.getEnum(HtmlElement.class, StringUtils.upperCase(qName));
        if (currentElement != null)
        {
            switch(currentElement)
            {
                case UL:
                    parentAttributes = SaxAttributes.with().attributes(attributes).attribute("list-type", "ul");
                    sb.append(currentElement.startTag(null));
                    break;
                case OL:
                    parentAttributes = SaxAttributes.with().attributes(attributes).attribute("list-type", "ol");
                    sb.append(currentElement.startTag(null));
                    break;
                case LI:
                    sb.append(currentElement.startTag(parentAttributes));                        
                    break;
                default : 
                    sb.append(currentElement.startTag(SaxAttributes.with().attributes(attributes)));                        
            }
        }
    }
    
    private enum HtmlElement 
    {        
        BR("<fo:block %s>&#xA;</fo:block>", 
           "", 
           AttrMap.with().attr("linefeed-treatment", FoAttr.attrFormat("linefeed-treatment=\"%s\"").defaultVal("preserve"))
                         .attr("line-height", FoAttr.attrFormat("line-height=\"%s\"").defaultVal("0.5"))),

        A("<fo:basic-link %s>", 
          "</fo:basic-link>", 
          AttrMap.with().attr("href", FoAttr.attrFormat("external-destination=\"url('%s')\""))), 

        P("<fo:block>", 
          "</fo:block>", 
          AttrMap.with().attr("line-height", FoAttr.attrFormat("line-height=\"%s\"").defaultVal("inherit"))), 
        
        B("<fo:inline %s>", 
          "</fo:inline>", 
          AttrMap.with().attr("font-weight", FoAttr.attrFormat("font-weight=\"%s\"").defaultVal("bold"))
                        .attr("color", FoAttr.attrFormat("color=\"%s\""))), 
        
        I("<fo:inline font-style=\"italic\">", "</fo:inline>", null), 
        
        U("<fo:inline text-decoration=\"underline\">", "</fo:inline>", null), 
        
        SUP("<fo:inline %s>", 
            "</fo:inline>", 
            AttrMap.with().attr("vertical-align", FoAttr.attrFormat("vertical-align=\"%s\"").defaultVal("super"))
                          .attr("font-size", FoAttr.attrFormat("font-size=\"%s\"").defaultVal("50%"))), 
            
        SUB("<fo:inline %s>", 
             "</fo:inline>", 
              AttrMap.with().attr("vertical-align", FoAttr.attrFormat("vertical-align=\"%s\"").defaultVal("sub"))
                            .attr("font-size", FoAttr.attrFormat("font-size=\"%s\"").defaultVal("75%"))), 
                            
        FONT("<fo:inline %s>", 
            "</fo:inline>", 
            AttrMap.with().attr("color", FoAttr.attrFormat("color=\"%s\""))
                          .attr("font-size", FoAttr.attrFormat("font-size=\"%s\""))
                          .attr("font-weight", FoAttr.attrFormat("font-weight=\"%s\""))
                          .attr("font-family", FoAttr.attrFormat("font-family=\"%s\""))), 
        
        UL("<fo:list-block>", "</fo:list-block>", null), 
        
        OL("<fo:list-block>", "</fo:list-block>", null), 
        
        LI("<fo:list-item><fo:list-item-label margin-left=\"%s\"><fo:block><fo:inline color=\"%s\" font-size=\"%s\" vertical-align=\"%s\" font-family=\"%s\">%s</fo:inline></fo:block></fo:list-item-label><fo:list-item-body margin-left=\"%s\" padding-left=\"%s\"><fo:block linefeed-treatment=\"preserve\" keep-together.within-page=\"always\" text-align=\"justify\">", 
               "</fo:block></fo:list-item-body></fo:list-item>", 
               AttrMap.with().attr("start", FoAttr.attrFormat("").defaultVal("1"))
                             .attr("type", FoAttr.attrFormat("").defaultVal("1"))
                             .attr("size", FoAttr.attrFormat("").defaultVal("inherit"))
                             .attr("vertical-align", FoAttr.attrFormat("").defaultVal("inherit"))
                             .attr("font-family", FoAttr.attrFormat("").defaultVal("inherit"))
                             .attr("color", FoAttr.attrFormat("").defaultVal("inherit"))
                             .attr("space-before", FoAttr.attrFormat("").defaultVal("0mm"))
                             .attr("space-after", FoAttr.attrFormat("").defaultVal("5mm")));
     
        private static final String BULLET = "&#x2022;";

        private AttrMap defaultParam;
        private String foStartTag;
        private String foEndTag;
        private HtmlElement(String foStartTag, String foEndTag, AttrMap defaultParam)
        {
            this.foStartTag = foStartTag;
            this.foEndTag = foEndTag;
            this.defaultParam = defaultParam;
        }

        private String getBulletValue(SaxAttributes attributes)
        {
            if (attributes != null)
            {
                if (attributes.getOrDefault("list-type", "ol").equals("ol"))
                {
                    String retVal = "?";
                    int counter = NumberUtils.toInt(attributes.get("list-counter"), 0);
                    counter++;

                    switch (attributes.getOrDefault("type", "1"))
                    {
                        case "A":
                            retVal = counter > 0 && counter < 27 ? String.valueOf((char)(counter + 64)) : "?";
                            break;
                        case "a":
                            retVal =  counter > 0 && counter < 27 ? String.valueOf((char)(counter + 96)) : "?";
                        case "I":
                            retVal = RomanNumeralUtils.toRomanNumeral(counter);
                            break;
                        case "i":
                            retVal = StringUtils.lowerCase(RomanNumeralUtils.toRomanNumeral(counter));
                            break;
                        default:
                            retVal = "" + counter;
                    }

                    attributes.put("list-counter", "" + counter);
                    return retVal + ".";
                }
                else
                {
                    return attributes.getOrDefault("type", BULLET);
                }
            }
            
            return BULLET;
                
        }
        
        public String startTag(SaxAttributes attributes)
        {
            String retVal = "";
            
            switch (this)
            {
                case LI: 
                    retVal = String.format(foStartTag, 
                            attributes.getOrDefault("space-before", defaultParam.get("space-before").defaultVal()),
                            attributes.getOrDefault("color", defaultParam.get("color").defaultVal()),
                            attributes.getOrDefault("size", defaultParam.get("size").defaultVal()),
                            attributes.getOrDefault("vertical-align", defaultParam.get("vertical-align").defaultVal()),
                            attributes.getOrDefault("font-family", defaultParam.get("font-family").defaultVal()),
                            getBulletValue(attributes),
                            attributes.getOrDefault("space-before", defaultParam.get("space-before").defaultVal()),
                            attributes.getOrDefault("space-after", defaultParam.get("space-after").defaultVal()));
                    break;

                default:
                    List<String> attrList = new ArrayList<>();
                    if (defaultParam != null)
                    {
                        defaultParam.keySet().forEach(k -> {
                            String attrVal = attributes.getOrDefault(k, defaultParam.get(k).defaultVal());
                            if (attrVal != null) attrList.add(defaultParam.get(k).getAttribute(attrVal));
                        });                
                    }
                    retVal = String.format(foStartTag, StringUtils.join(attrList, " "));
            }
            
            return retVal;
        }
        
        public String endTag()
        {
            return foEndTag;
        }
    }
    
    @Data(staticConstructor = "attrFormat")
    @Accessors(fluent = true)
    private static class FoAttr
    {
        private final String attrFormat;
        private String defaultVal = null;
        
        public String getAttribute(Object value)
        {
            String valueToFormat = value != null ? "" + value : defaultVal;
            return valueToFormat != null ? String.format(attrFormat, valueToFormat) : "";
        }
    }
    
    @Data(staticConstructor = "with")
    private static class AttrMap extends FluentMap<String, FoAttr>
    {
        public AttrMap attr(String markupAttr, FoAttr foAttr)
        {
            this.put(markupAttr, foAttr);
            return this;
        }
    }
    
    private static class RomanNumeralUtils
    {
        private static final FluentMap<String, Integer> ROMAN_NUMERALS = 
                FluentMap.<String, Integer>with().keyValue("M", 1000).keyValue("CM", 900).keyValue("D", 500).keyValue("CD", 400)
                                                 .keyValue("C", 100).keyValue("XC", 90).keyValue("L", 50).keyValue("XL", 40)
                                                 .keyValue("X", 10).keyValue("IX", 9).keyValue("V", 5).keyValue("IV", 4).keyValue("I", 1);
        
        public static String toRomanNumeral(final int value)
        {
            int calculate = value;
            String retVal = "";
            for (Entry<String, Integer> e : ROMAN_NUMERALS.entrySet())
            {
                int count = calculate / e.getValue();
                retVal += StringUtils.rightPad("", count, e.getKey());
                calculate = calculate % e.getValue();
            }
            
            return retVal;
        }
    }

    @Data(staticConstructor = "with")
    private static class FluentMap<K, V> extends LinkedHashMap<K, V>
    {
        public FluentMap<K, V> keyValue(K key, V value)
        {
            super.put(key, value);
            return this;
        }
    }

}

