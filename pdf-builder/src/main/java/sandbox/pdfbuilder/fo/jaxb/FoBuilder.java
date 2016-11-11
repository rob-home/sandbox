package sandbox.pdfbuilder.fo.jaxb;

import org.w3._1999.xsl.format.Root;

import lombok.Data;

@Data
public class FoBuilder
{
    private final Root root = new Root();
}
