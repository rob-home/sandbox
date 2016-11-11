package sandbox.pdfbuilder.fo.raw;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import sandbox.pdfbuilder.PdfBuilderException;
import sandbox.pdfbuilder.fo.FoProvider;
import sandbox.pdfbuilder.fo.InputStreamProvider;

@Data(staticConstructor = "with")
@Accessors(fluent = true)
public class ByteFoProvider implements FoProvider
{
    List<InputStreamProvider> dataStreamProvider = new ArrayList<>();

    @Override
    public List<InputStreamProvider> getInputStreams() throws PdfBuilderException
    {
        return dataStreamProvider;
    }

    public ByteFoProvider data(byte [] data)
    {
        this.dataStreamProvider.add(ByteInputStreamProvider.data(data));
        return this;
    }
    
    @Data(staticConstructor = "data")
    private static class ByteInputStreamProvider implements InputStreamProvider
    {
        private final byte [] data;

        @Override
        public InputStream getInputStream() throws PdfBuilderException
        {
            return new ByteArrayInputStream(data);
        }
    }

}
