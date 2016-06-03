package sandbox.pdfbuilder.fo;

import java.util.List;

import sandbox.pdfbuilder.PdfBuilderException;

public interface FoProvider
{
    List<InputStreamProvider> getInputStreams() throws PdfBuilderException;
}
