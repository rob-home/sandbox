package org.apache.batik.svggen.font;

import java.io.OutputStream;
import java.io.PrintStream;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import org.apache.batik.svggen.font.table.Table;

@Slf4j
@Data(staticConstructor = "font")
@Accessors(fluent = true)
public class SvgFontGenerator extends SVGFont
{
    private final Font font;
    private final String fontId;
    private int lowCharIndex = -1;
    private int highCharIndex = -1;
    private boolean autoRange = false;
    private boolean asciiCharMap = false;
    private boolean testCard = false;
    
    public void generate(OutputStream outputStream)
    {
        try {
            
            PrintStream ps = new PrintStream(outputStream);

            writeSvgBegin(ps);
            writeSvgDefsBegin(ps);
            writeFontAsSVGFragment(ps, font, fontId, lowCharIndex, highCharIndex, autoRange, asciiCharMap);
            writeSvgDefsEnd(ps);
            
            if (testCard) 
            {
                String fontFamily = font.getNameTable().getRecord(Table.nameFontFamilyName);
                writeSvgTestCard(ps, fontFamily);
            }
            writeSvgEnd(ps);
            
        } 
        catch (Exception e) 
        {
            log.error("Failed to generate SVG font", e);
        }
    }
}
