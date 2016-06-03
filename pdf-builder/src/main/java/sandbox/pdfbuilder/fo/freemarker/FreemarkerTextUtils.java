package sandbox.pdfbuilder.fo.freemarker;

import java.text.MessageFormat;

public class FreemarkerTextUtils
{
    public static String format(String definition, Object ... param)
    {
        return MessageFormat.format(definition, param);
    }
    
    
}
