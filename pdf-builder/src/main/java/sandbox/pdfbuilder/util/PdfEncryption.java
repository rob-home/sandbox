package sandbox.pdfbuilder.util;

import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

import org.apache.fop.pdf.PDFEncryptionParams;

@Data(staticConstructor = "with")
@Accessors(fluent = true)
public class PdfEncryption
{
    private String userPassword = null;
    private String ownerPassword = UUID.randomUUID().toString();
    private boolean allowAccessContent = false;
    private boolean allowCopyContent = false;
    private boolean allowEditAnnotations = false;
    private boolean allowEditContent = false;
    private boolean allowFillInForms = false;
    private boolean allowPrint = false;
    private boolean encryptMetadata = true;
    private int encryptionLength = 128;

    public PDFEncryptionParams getEncryptionParam()
    {
        PDFEncryptionParams pdfEncryptionParams = new PDFEncryptionParams();
        pdfEncryptionParams.setUserPassword(userPassword);
        pdfEncryptionParams.setOwnerPassword(ownerPassword);
        pdfEncryptionParams.setAllowAccessContent(allowAccessContent);
        pdfEncryptionParams.setAllowAssembleDocument(allowAccessContent);
        pdfEncryptionParams.setAllowCopyContent(allowCopyContent);
        pdfEncryptionParams.setAllowEditAnnotations(allowEditAnnotations);
        pdfEncryptionParams.setAllowEditContent(allowEditContent);
        pdfEncryptionParams.setAllowFillInForms(allowFillInForms);
        pdfEncryptionParams.setAllowPrint(allowPrint);
        pdfEncryptionParams.setAllowPrintHq(allowPrint);
        pdfEncryptionParams.setEncryptMetadata(encryptMetadata);
        pdfEncryptionParams.setEncryptionLengthInBits(encryptionLength);
        
        return pdfEncryptionParams;
    }
}
