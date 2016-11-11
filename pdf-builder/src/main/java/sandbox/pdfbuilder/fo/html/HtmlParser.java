package sandbox.pdfbuilder.fo.html;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import freemarker.template.utility.StringUtil;

public class HtmlParser extends DefaultHandler
{
    private StringBuffer sb = new StringBuffer();
    
    @Override
    public void characters(char[] characters, int start, int length) throws SAXException
    {
        sb.append(StringUtil.XMLEnc(new String(characters, start, length)));
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        // TODO Auto-generated method stub
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        // TODO Auto-generated method stub
        super.endElement(uri, localName, qName);
    }
}
