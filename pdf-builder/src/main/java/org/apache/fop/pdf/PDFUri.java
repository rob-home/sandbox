package org.apache.fop.pdf;

/**
 * class used to create a PDF Uri link
 */
public class PDFUri extends PDFAction {

    private String uri;

    /**
     * create a Uri instance.
     *
     * @param uri the uri to which the link should point
     */
    public PDFUri(String uri) {
        this.uri = uri;
    }

    /**
     * returns the action ncecessary for a uri
     *
     * @return the action to place next to /A within a Link
     */
    public String getAction() {
        if (hasObjectNumber()) {
            return referencePDF();
        } else {
            return getDictString();
        }
    }

    private String getDictString() {
        return "<< /URI (" + encodeURI(uri) + ")\n/S /URI >>";
    }

    /** {@inheritDoc} */
    public String toPDFString() {
        //TODO Convert this class into a dictionary
        return getDictString();
    }

  private String encodeURI(String text) {
      if (getDocument() != null && getDocumentSafely().isEncryptionActive()) {
          final byte[] buf = PDFText.encode(text);
          byte[] enc = getDocument().getEncryption().encrypt(buf, this);
          return PDFText.toHex(enc, true);
      } else {
          return "(" + text + ")";
      }
  }
    
}
