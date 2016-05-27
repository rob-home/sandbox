package sandbox.pdfbuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.fop.fonts.apps.TTFReader;
import org.apache.fop.fonts.truetype.TTFFile;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

import sandbox.pdfbuilder.fo.FileFoProvider;
import sandbox.pdfbuilder.fo.FreemarkerFoProvider;
import freemarker.cache.ClassTemplateLoader;

public class TestPdfBuilder
{
    
    @Test
    public void createPdfFromXml() throws Exception
    {
        byte [] pdf = PdfBuilder.foProvider(FileFoProvider.with().file("src/test/resources/fop/test005.fo")).build();
        
    }
    
    @Test
    public void createPdfFromFreemarker() throws Exception
    {
        Map<String, String> dictionary = new HashMap();
        
        dictionary.put("gdar.general.footerurl", "<a href=\"#\">gov.uk/greendeal</a>");
        
        writePdf(PdfBuilder.foProvider(FreemarkerFoProvider.Builder
                                                           .templateLoader(new ClassTemplateLoader(this.getClass(), "/fop"))
                                                           .build()
                                                           .template("test005.ftl")
                                                           .modelParam("rrn", "1111-2222-3333-4444-5555")
                                                           .modelParam("dictionary", dictionary)).build());
    }
    
    private void writePdf(byte [] pdf) throws Exception
    {
        File file = new File("target/generated-pdf/test_" + System.currentTimeMillis() + ".pdf");
        
        FileUtils.writeByteArrayToFile(file, pdf);
        
        System.out.println(file.getAbsolutePath());
    }
    
    @Ignore
    public void test() throws Exception
    {
        String fontName = "Century Gothic";
        String fontPath = "src/main/resources/fonts/Century-Gothic-Bold.ttf";
        String output = "src/main/resources/fonts/Century-Gothic-Bold.xml";
        
        TTFReader ttfReader = new TTFReader();
        TTFFile ttf = ttfReader.loadTTF(fontPath, fontName, true, true);
        
        Document doc = ttfReader.constructFontXML(ttf, fontName, null, null, null, false, fontName);

        ttfReader.writeFontXML(doc, new File(output));
        
    }
}
