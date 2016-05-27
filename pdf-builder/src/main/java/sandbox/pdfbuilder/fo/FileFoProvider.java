package sandbox.pdfbuilder.fo;

import java.io.File;
import java.io.IOException;

import lombok.Data;
import lombok.experimental.Accessors;

import org.apache.commons.io.FileUtils;

import sandbox.pdfbuilder.PdfBuilderException;

@Data(staticConstructor = "with")
@Accessors(fluent = true)
public class FileFoProvider implements FoProvider
{
    private File file;

    @Override
    public byte[] getBytes() throws PdfBuilderException
    {
        try
        {
            return FileUtils.readFileToByteArray(file);
        }
        catch (IOException e)
        {
            throw new PdfBuilderException(e);
        }
    }
    
    public FileFoProvider file(File file)
    {
        this.file = file;
        return this;
    }

    public FileFoProvider file(String file)
    {
        return file(new File(file));
    }
}
