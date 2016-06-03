package sandbox.pdfbuilder.util;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MarkupParser
{
    public void parse(String markup) throws Exception
    {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        
        MarkupHandler markupHandler = new MarkupHandler();
        
        saxParser.parse(IOUtils.toInputStream(markup), markupHandler);
    }
    
    private class MarkupHandler extends DefaultHandler
    {
        boolean value = false;
        StringBuffer sb = new StringBuffer();
        
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
            
            // System.out.println("SAX=[URI=[" + uri + "], LOCALNAME=[" + localName + "], QNAME=[" + qName + "]]");

            for (int i = 0; i < attributes.getLength(); i++)
            {
                // System.out.println(attributes.getLocalName(i) + " = " + attributes.getValue(i));
            }
            
            if (qName.equalsIgnoreCase("li"))
            {
                sb.setLength(0);
                value = true;
            }
        }
        
        

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException
        {
            if (value)
            {
                // System.out.println("VAL=[" + sb.toString() + "]");
                value = false;
            }

        }



        @Override
        public void characters(char[] ch, int start, int length) throws SAXException
        {
            sb.append(new String(ch, start, length));
        }


        
    }
    
    
}
