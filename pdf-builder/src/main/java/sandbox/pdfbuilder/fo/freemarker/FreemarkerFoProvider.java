package sandbox.pdfbuilder.fo.freemarker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;

import sandbox.pdfbuilder.PdfBuilderException;
import sandbox.pdfbuilder.fo.FoProvider;
import sandbox.pdfbuilder.fo.InputStreamProvider;
import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

@Slf4j
@Data(staticConstructor = "config")
@Accessors(fluent = true)
public class FreemarkerFoProvider implements FoProvider
{
    private final Configuration config; 
    private Map<String, Object> model = new HashMap<>();
    private List<InputStreamProvider> inputStreams = new ArrayList<>();
    
    public FreemarkerFoProvider template(String ... template)
    {
        if (template != null) Arrays.asList(template).forEach(t -> {inputStreams.add(FreemarkerInputStreamProvider.with(t, this));});
        return this;
    }
    
    public FreemarkerFoProvider modelParam(String key, Object value)
    {
        this.model.put(key, value);
        return this;
    }
    
    public FreemarkerFoProvider modelHelper(String key, Object value) throws TemplateModelException
    {
        TemplateHashModel staticModel = new BeansWrapperBuilder(Configuration.VERSION_2_3_23).build().getStaticModels();
        TemplateHashModel helperModel = (TemplateHashModel)staticModel.get(value.getClass().getName());
        this.model.put(key, helperModel);
        return this;
    }
    
    @Override
    public List<InputStreamProvider> getInputStreams() throws PdfBuilderException
    {
        return this.inputStreams;
    }

    @Data(staticConstructor = "templateLoader")
    public static class Builder
    {
        private final TemplateLoader templateLoader;
        
        public FreemarkerFoProvider build() throws TemplateModelException
        {
            Configuration config = new Configuration(Configuration.VERSION_2_3_23);
            config.setTemplateLoader(templateLoader);
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
            return FreemarkerFoProvider.config(config)
                                       .modelHelper("markupUtils", new FreemarkerSaxMarkupUtils())
                                       .modelHelper("textUtils", new FreemarkerTextUtils())
                                       .modelHelper("imageUtils", new FreemarkerImageUtils());
        }
    }
    
    private static class FreemarkerInputStreamProvider implements InputStreamProvider
    {
        private final String template;
        private final FreemarkerFoProvider provider;

        private FreemarkerInputStreamProvider(String template, FreemarkerFoProvider provider)
        {
            this.template = template;
            this.provider = provider;
        }
        
        public static FreemarkerInputStreamProvider with(String template, FreemarkerFoProvider provider)
        {
            return new FreemarkerInputStreamProvider(template, provider);
        }
        
        @Override
        public InputStream getInputStream() throws PdfBuilderException
        {
            byte [] fo = getBytes(this.template);
            try
            {
                FileUtils.writeByteArrayToFile(new File("target/generated-pdf/pdf.fo"), fo);
                //System.out.println(new String(fo));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (log.isDebugEnabled()) log.debug(new String(fo));
            return new ByteArrayInputStream(fo);
        }
        
        private byte[] getBytes(String template) throws PdfBuilderException
        {
            if (template != null)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Writer writer = new OutputStreamWriter(baos);
                
                try
                {
                    this.provider.config().getTemplate(template).process(this.provider.model(), writer);
                    baos.flush();
                    baos.close();
                    
                    return baos.toByteArray();
                }
                catch (TemplateException | IOException e)
                {
                    throw new PdfBuilderException(e);
                }
            }
            
            return null;
        }

    }
}
