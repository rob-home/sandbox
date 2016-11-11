package sandbox.pdfbuilder.fo.freemarker;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class FreemarkerImageUtils
{
    public static String encode(String path) throws IOException
    {
        if (path != null)
        {
            try
            {
                ClassPathResource cpr = new ClassPathResource(path);

                if (cpr.exists())
                {
                    String mimeType = Files.probeContentType(cpr.getFile().toPath());
                    
                    if (!StringUtils.isBlank(mimeType))
                    {
                        return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(IOUtils.toByteArray(cpr.getInputStream()));
                    }
                }
            }
            catch (Exception e)
            {
                log.error("Failed to process embeded reesource [" + path + "]", e);
            }
            
        }
        
        log.error("Failed to process embeded reesource [" + path + "]");

        return null;
    }
}
