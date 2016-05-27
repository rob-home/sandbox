package sandbox.pdfbuilder.fo;

import java.io.InputStream;

import sandbox.pdfbuilder.PdfBuilderException;

public interface InputStreamProvider
{
    InputStream getInputStream() throws PdfBuilderException;
}
