package sandbox.test;

import java.io.File;

import org.apache.commons.io.FileUtils;

import sandbox.pdfbuilder.PdfBuilder;
import sandbox.pdfbuilder.fo.FileFoProvider;

public class TestPdfBuilder
{
    public static void main(String [] args) throws Exception
    {
        //System.out.println(new File("src/test/resources/fop/fop.xconf").exists());

        //IOUtils.readLines(new ClassPathResource("/fop.xconf").getInputStream()).forEach(System.out::println);
        
        byte [] pdf = PdfBuilder.foProvider(FileFoProvider.with().file("src/main/resources/fop/test005.fo")).build();
        
        File file = new File("target/generated-pdf/test_" + System.currentTimeMillis() + ".pdf");
        
        FileUtils.writeByteArrayToFile(file, pdf);
        
        System.out.println(file.getAbsolutePath());
    }
}
