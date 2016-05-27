package sandbox.pdfbuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import lombok.Data;
import lombok.experimental.Accessors;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.EnvironmentProfile;
import org.apache.fop.apps.EnvironmentalProfileFactory;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.MimeConstants;
import org.apache.xmlgraphics.io.ResourceResolver;

import sandbox.pdfbuilder.fo.FoProvider;

@Data(staticConstructor = "foProvider")
@Accessors(fluent = true)
public class PdfBuilder 
{
	private final FoProvider foProvider;
	private String userConfig = "/fop.xconf";
	private ResourceResolver resourceResolver = new ClasspathResourceResolver();
	
	public byte [] build()
	{
	    try
        {
            return transform(foProvider.getBytes(), MimeConstants.MIME_PDF);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	    
	    return null;
	}

	private byte [] transform(byte [] foBytes, String mimeType) throws Exception
	{
        ByteArrayInputStream bais = new ByteArrayInputStream(foBytes);
        Source source = new StreamSource(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    
        EnvironmentProfile enviro = EnvironmentalProfileFactory.createDefault(new File(".").toURI(), resourceResolver);
	    
	    DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
	    Configuration cfg = cfgBuilder.build(ClasspathResourceResolver.getInputStream(userConfig));
	    
	    FopFactoryBuilder fopFactoryBuilder = new FopFactoryBuilder(enviro).setConfiguration(cfg);
        FopFactory fopFactory = fopFactoryBuilder.build(); 
        
        Fop fop = fopFactory.newFop(mimeType, baos);
        
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        Result res = new SAXResult(fop.getDefaultHandler());

        transformer.transform(source, res);
        
        return baos.toByteArray();
	}
}
