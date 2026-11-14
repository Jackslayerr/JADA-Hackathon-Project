import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import org.jfree.chart.JFreeChart;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;

public class ChartExport
{
    public static void exportToPdf(JFreeChart chart, String filePath, int width, int height) throws Exception
    {
        Document document = new Document(new Rectangle(width, height));
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Creates a PdfTemplate and wraps it in a Graphics2D context
        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
        Graphics2D g2d = template.createGraphics(width, height, new DefaultFontMapper());

        // Draws the JFreeChart onto the Graphics2D canvas
        Rectangle2D area = new Rectangle2D.Double(0, 0, width, height);
        chart.draw(g2d, area);

        // Cleans up and close the document
        g2d.dispose();
        contentByte.addTemplate(template, 0, 0);
        document.close();
    }
}
