package secret.santa.mailer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import lombok.Data;
import lombok.experimental.Accessors;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FluentFreemarker
{
    private Template template;
    
    
    public FluentFreemarker(final Builder builder)
    {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(builder.templateClass != null ? builder.templateClass : this.getClass(), builder.pathPrefix);
        try {
            this.template = configuration.getTemplate(builder.template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String writeString(final Object model) {
        final StringWriter populatedTemplate = new StringWriter();
        write(model, populatedTemplate);
        return populatedTemplate.toString();
    }

    public void write(final Object model, final Writer out) {
        try
        {
            template.process(model, out);
        }
        catch (TemplateException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    @Data(staticConstructor = "with")
    @Accessors(fluent = true)
    public static class Builder {
        private Class templateClass;
        private String pathPrefix = "/";
        private String template;
        
        public FluentFreemarker build() {
            return new FluentFreemarker(this);
        }
    }
}
