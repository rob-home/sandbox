package sandbox.pdfbuilder.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class ClasspathResourceResolver implements ResourceResolver
{

    @Override
    public Resource getResource(URI uri) throws IOException
    {
        ClassPathResource cpr = null;
        cpr = new ClassPathResource(uri.getPath());
        log.debug("RESOURCE_RESOLVER=[URI=[" + uri + "], ABSOLUTE=[" + uri.isAbsolute() + "], RESOURCE=[" + cpr.getFilename() + "], EXISTS=[" + cpr.exists() + "]]");
        
        if (cpr.exists())
        {
            return new Resource(cpr.getInputStream());
        }
        else
        {
            Path path = Paths.get(uri);
            for (int i = 0; i < path.getNameCount(); i++)
            {
                Path subpath = path.subpath(i, path.getNameCount());
                
                cpr = new ClassPathResource(subpath.toString());
                log.debug("RESOURCE_RESOLVER=[URI=[" + uri + "], PATH=[" + subpath + "], RESOURCE=[" + cpr.getFilename() + "], EXISTS=[" + cpr.exists() + "]]");
                
                if (cpr.exists()) return new Resource(cpr.getInputStream());
            }
        }
        
        log.error("Failed to locate resource [" + (uri != null ? uri.getPath() : null) + "]");
        
        return null;
    }
    
    @Override
    public OutputStream getOutputStream(URI uri) throws IOException
    {
        return FileUtils.openOutputStream(new File(uri));
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
