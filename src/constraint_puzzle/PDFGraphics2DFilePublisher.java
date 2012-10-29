/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package constraint_puzzle;

import java.awt.Graphics2D;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfGraphics2D;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author rm108s04
 */
public class PDFGraphics2DFilePublisher extends Graphics2DFilePublisher {
    public PDFGraphics2DFilePublisher() {
        reset();
    }

    @Override protected void finalize() {
        document.close();
        graphics2D.dispose();
    }

    @Override public byte[] getByteRepresentation() {
        document.close();
        byte[] returnValue = outputStream.toByteArray();
        reset();
        return returnValue;
    }

    @Override public String getFormatName() {
        return "PDF";
    }

    @Override public String getExtensionName() {
        return "pdf";
    }

    @Override public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    private void reset() {
        outputStream = new ByteArrayOutputStream();
        document = new Document(PageSize.A5, 36, 72, 108, 180);

        try {
            writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            PdfContentByte pcb = writer.getDirectContentUnder();
            graphics2D = pcb.createGraphics(640, 480);
        }
        catch(Exception e) {
            System.out.println("Exception Thrown in PDFG2DFileP " + e.toString());
        }
    }

    private Document document;
    private Graphics2D graphics2D;
    private ByteArrayOutputStream outputStream;
    private PdfWriter writer;
}
