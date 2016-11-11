package sandbox.pdfbuilder.fo.freemarker;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

@Slf4j
public class FreemarkerSaxMarkupUtils
{
    public static String process(final String value)
    {
        String retVal = value;
        
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser      saxParser = factory.newSAXParser();
            HtmlHandler htmlHandler = new HtmlHandler();

            saxParser.parse(IOUtils.toInputStream("<HtmlHandler>" + value + "</HtmlHandler>"), htmlHandler);
            retVal = htmlHandler.result();
        }
        catch (IOException | ParserConfigurationException | SAXException e)
        {
            log.error("Failed to process markup", e);
        }
        
        return retVal;
    }
}
