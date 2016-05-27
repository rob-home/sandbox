package sandbox.pdfbuilder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;

public class OldClasspathResourceResolver implements ResourceResolver
{
    @Override
    public Resource getResource(URI uri) throws IOException
    {
        if (uri != null) return new Resource(this.getClass().getClassLoader().getResourceAsStream("." + uri.getPath()));
        return null;
    }

    @Override
    public OutputStream getOutputStream(URI uri) throws IOException
    {
        System.out.println("URI=[" + uri.getPath() + "]");
        return null;
    }
    
    public static File getFile(String u)
    {
        try
        {
            URI uri = new URI(u);
            
            System.out.println(uri.getPath()); 
            
            return new File(OldClasspathResourceResolver.class.getClassLoader().getResource("." + uri.getPath()).getFile());
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
