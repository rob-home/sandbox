package sandbox.pdfbuilder.util;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

@Slf4j
public class NodeUtils
{
    public static String nodeToXmlBetween(Node node, String afterFirst, String beforeLast)
    {
        return StringUtils.substringAfter(StringUtils.substringBeforeLast(nodeToXml(node), beforeLast), afterFirst);
    }
    
    public static String nodeToXml(Node node)
    {
        StringWriter sw = new StringWriter();
        try
        {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "no");
            t.transform(new DOMSource(node), new StreamResult(sw));
        }
        catch (TransformerException te)
        {
            log.debug("nodeToString Transformer Exception");
        }
        
        return sw.toString();
    }
}