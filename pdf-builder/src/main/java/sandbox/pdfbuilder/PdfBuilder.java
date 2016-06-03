package sandbox.pdfbuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.EnvironmentProfile;
import org.apache.fop.apps.EnvironmentalProfileFactory;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.MimeConstants;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.xml.sax.SAXException;

import sandbox.pdfbuilder.fo.FoProvider;
import sandbox.pdfbuilder.fo.InputStreamProvider;

@Slf4j
@Data(staticConstructor = "foProvider")
@Accessors(fluent = true)
public class PdfBuilder 
{
	private final FoProvider foProvider;
	private String mimeType = MimeConstants.MIME_PDF;
	private String userConfig = "/fop.xconf";
	private ResourceResolver resourceResolver = new ClasspathResourceResolver();
	
	public void build(OutputStream outputStream) throws PdfBuilderException
	{
	    for (InputStreamProvider inputStreamProvider : foProvider().getInputStreams())
	    {
	        InputStream inputStream = inputStreamProvider.getInputStream();
	        transform(inputStream, outputStream, mimeType);
	        try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                log.error("Failed to close xsl-fo input stream", e);
            }
	    }
	}
	
	private void transform(InputStream inputStream, OutputStream outputStream, String mimeType) throws PdfBuilderException
	{
	    try
	    {
	        Source source = new StreamSource(inputStream);

	        EnvironmentProfile enviro = EnvironmentalProfileFactory.createDefault(new File(".").toURI(), resourceResolver);
	        
	        DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
	        Configuration cfg = cfgBuilder.build(ClasspathResourceResolver.getInputStream(userConfig));
	        
	        FopFactoryBuilder fopFactoryBuilder = new FopFactoryBuilder(enviro).setConfiguration(cfg);
	        FopFactory fopFactory = fopFactoryBuilder.build(); 

	        Fop fop = fopFactory.newFop(mimeType, outputStream);
	        
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Transformer transformer = factory.newTransformer();
	        Result res = new SAXResult(fop.getDefaultHandler());

	        transformer.transform(source, res);
	        
	        outputStream.flush();
	    }
	    catch (ConfigurationException | SAXException | IOException | TransformerException e)
	    {
	        throw new PdfBuilderException(e);
	    }
	}
}
