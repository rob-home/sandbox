package org.apache.fop.apps;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.transform.stream.StreamSource;

import lombok.extern.slf4j.Slf4j;

import org.apache.fop.apps.io.InternalResourceResolver;

/*
 * NOTE: Work-around for current bug in Batik (https://issues.apache.org/jira/browse/FOP-2489)
 */
@Slf4j
public class LocalFOUserAgent extends FOUserAgent
{
    InternalResourceResolver resourceResolver;
    
    public LocalFOUserAgent(FopFactory fopFactory, InternalResourceResolver resourceResolver)
    {
        super(fopFactory, resourceResolver);
        this.resourceResolver = resourceResolver;
    }

    @Override
    public StreamSource resolveURI(String uri)
    {
        log.debug("GdFOUserAgent=[uri=[" + uri + "]]");
        
        // TODO: What do we want to do when resources aren't found??? We also need to remove this method entirely
        try
        {
            // Have to do this so we can resolve data URIs
            StreamSource src = new StreamSource(resourceResolver.getResource(uri));

            // A systemId is always expected to be absolute.
            // Anyway, without this, SVG files using markers (e.g. marker-start:url(#TriangleInM)) cannot be rendered.
            if (!uri.startsWith("data:"))
            {
                uri = resourceResolver.getBaseURI().resolve(uri).toASCIIString();
            }

            src.setSystemId(uri);
            return src;
        }
        catch (URISyntaxException use)
        {
            return null;
        }
        catch (IOException ioe)
        {
            return null;
        }
    }    
}
