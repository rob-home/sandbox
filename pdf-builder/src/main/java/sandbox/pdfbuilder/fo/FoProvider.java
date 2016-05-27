package sandbox.pdfbuilder.fo;

import java.io.InputStream;
import java.util.List;

import sandbox.pdfbuilder.PdfBuilderException;

public interface FoProvider
{
    byte [] getBytes() throws PdfBuilderException;
    
    //List<InputStreamProvider> getInputStreams() throws PdfBuilderException;
}
