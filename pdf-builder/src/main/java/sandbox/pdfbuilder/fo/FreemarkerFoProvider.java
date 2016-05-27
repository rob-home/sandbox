package sandbox.pdfbuilder.fo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;
import sandbox.pdfbuilder.PdfBuilderException;
import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

@Data(staticConstructor = "config")
@Accessors(fluent = true)
public class FreemarkerFoProvider implements FoProvider
{
    private final Configuration config; 
    private Map<String, Object> model = new HashMap<>();
    private String template;
    
    @Override
    public byte[] getBytes() throws PdfBuilderException
    {
        if (template != null)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Writer writer = new OutputStreamWriter(baos);
            
            try
            {
                config.getTemplate(template).process(model, writer);
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
            return FreemarkerFoProvider.config(config).modelHelper("fmUtils", new FreemarkerMarkupUtils());
        }
    }
}
