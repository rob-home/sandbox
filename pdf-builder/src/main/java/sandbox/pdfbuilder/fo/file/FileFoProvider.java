package sandbox.pdfbuilder.fo.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

import org.apache.commons.io.FileUtils;

import sandbox.pdfbuilder.PdfBuilderException;
import sandbox.pdfbuilder.fo.FoProvider;
import sandbox.pdfbuilder.fo.InputStreamProvider;

@Data(staticConstructor = "with")
@Accessors(fluent = true)
public class FileFoProvider implements FoProvider
{
    private List<InputStreamProvider> files = new ArrayList<InputStreamProvider>();
    
    public FileFoProvider file(File ... file)
    {
        if (file != null) Arrays.asList(file).forEach(f -> {files.add(FileInputStreamProvider.file(f));});
        return this;
    }

    public FileFoProvider file(String file)
    {
        return file(new File(file));
    }
    
    @Override
    public List<InputStreamProvider> getInputStreams() throws PdfBuilderException
    {
        return files;
    }

    @Data(staticConstructor = "file")
    private static class FileInputStreamProvider implements InputStreamProvider
    {
        private final File file;

        @Override
        public InputStream getInputStream() throws PdfBuilderException
        {
            try
            {
                return FileUtils.openInputStream(this.file);
            }
            catch (IOException e)
            {
                throw new PdfBuilderException(e);
            }
        }
    }
}
