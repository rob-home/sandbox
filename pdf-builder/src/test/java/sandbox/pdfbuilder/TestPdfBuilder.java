package sandbox.pdfbuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.batik.svggen.font.Font;
import org.apache.batik.svggen.font.SvgFontGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.fonts.apps.TTFReader;
import org.apache.fop.fonts.truetype.TTFFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import sandbox.pdfbuilder.fo.file.FileFoProvider;
import sandbox.pdfbuilder.fo.freemarker.FreemarkerFoProvider;
import freemarker.cache.ClassTemplateLoader;

public class TestPdfBuilder
{
    
    @Test
    public void createPdfFromFreemarker() throws Exception
    {
        Map<String, String> dictionary = new HashMap<>();
        
        dictionary.put("gdar.general.footerurl", "<a href=\"#\">gov.uk/greendeal</a>");
        dictionary.put("style", "<a href=\"#\">gov.uk/greendeal</a>");
     
        try {
            PdfBuilder.foProvider(FreemarkerFoProvider.Builder
                    .templateLoader(new ClassTemplateLoader(this.getClass(), "/fop")).build()
                    .template("test005.ftl")
                    .modelParam("rrn", "1111-2222-3333-4444-5555")
                    .modelParam("dictionary", dictionary))
                    .build(FileUtils.openOutputStream(outputFile));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Before
    public void setup()
    {
        outputFile = new File("target/generated-pdf/test_" + System.currentTimeMillis() + ".pdf");
    }
    
    @After
    public void tearDown()
    {
        System.out.println(outputFile.getAbsolutePath());
    }
    
    private File outputFile;
    
    @Test
    public void test() throws Exception
    {
        String fontName = "Arial";
        String fileName = "ariali";

        String fontPath = "src/main/resources/fonts/" + fileName + ".ttf";
        String outputXml = "src/main/resources/fonts/" + fileName + ".xml";

        String glyphFontId = StringUtils.remove(fontName, " ");
        String outputGlyph = "src/main/resources/fonts/" + fileName + ".svg";

        /* Generate XML font */
        TTFReader ttfReader = new TTFReader();
        TTFFile ttf = ttfReader.loadTTF(fontPath, fontName, true, true);
        Document doc = ttfReader.constructFontXML(ttf, fontName, null, null, null, false, fontName);
        ttfReader.writeFontXML(doc, new File(outputXml));
        
        /* Generate SVG font */
        SvgFontGenerator.font(Font.create(fontPath), glyphFontId).lowCharIndex(163).highCharIndex(163).testCard(true).generate(FileUtils.openOutputStream(new File(outputGlyph)));

    }
}
