package sandbox.pdfbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import lombok.extern.slf4j.Slf4j;

import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class ClasspathResourceResolver implements ResourceResolver
{

    @Override
    public Resource getResource(URI uri) throws IOException
    {
        ClassPathResource cpr = new ClassPathResource(uri.getPath());
        
        log.debug("RESOURCE=[" + cpr.getFilename() + "], EXISTS=[" + cpr.exists() + "]");
        
        return cpr.exists() ? new Resource(cpr.getInputStream()) : null;
    }

    @Override
    public OutputStream getOutputStream(URI uri) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    
    
    public static InputStream getInputStream(String path)
    {
        ClassPathResource cpr = new ClassPathResource(path);
        
        try
        {
            return cpr.exists() ? cpr.getInputStream() : null;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
