package CAMPS.Common;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import java.util.Date;

public class HeaderAndFooter extends PdfPageEventHelper {

    protected PdfPTable header;
    protected PdfPTable footer;

    public HeaderAndFooter(String foot, String iso) throws DocumentException {
        Date d = new Date();

        footer = new PdfPTable(1);
        Font font1 = new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC);
        Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
        header = new PdfPTable(1);
        header.setWidthPercentage(100);
        header.setTotalWidth(800f);
        PdfPCell cell = new PdfPCell(new Phrase(d.toString(), font2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        header.addCell(cell);
        float[] columnWidths = {800f};
        footer.setWidths(columnWidths);

        footer.setTotalWidth(600);
        footer.setLockedWidth(true);
        footer.getDefaultCell().setFixedHeight(90);
        //footer.setTotalWidth(600);
        //footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(new Paragraph(foot, font2));
        cell.setBorder(Rectangle.NO_BORDER);//BOTTOM
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footer.addCell(cell);
        cell = new PdfPCell(new Paragraph(iso, font1));
        cell.setBorder(Rectangle.NO_BORDER);//BOTTOM
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footer.addCell(cell);
    }

    public HeaderAndFooter(String foot, String iso, Document document, String path1, PdfPTable otherTable) throws Exception {
        Date d = new Date();
        Rectangle page = document.getPageSize();
        footer = new PdfPTable(1);
        Font font1 = new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC);
        Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
        header = new PdfPTable(1);
        header.setWidthPercentage(100);
        header.setTotalWidth(page.getWidth());
        PdfPCell cell = new PdfPCell(new Phrase(d.toString(), font2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        header.addCell(cell);
        Image image1 = Image.getInstance(path1);
        image1.scaleToFit(550f, 70f);
        //image1.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(image1);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(cell);

        //header = new Phrase(d.toString(),font2);
        float[] columnWidths = {800f};
        footer.setWidths(columnWidths);

        footer.setTotalWidth(600);
        footer.setLockedWidth(true);
        footer.getDefaultCell().setFixedHeight(90);
        //footer.setTotalWidth(600);
        //footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(new Paragraph(foot, font2));
        cell.setBorder(Rectangle.NO_BORDER);//BOTTOM
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footer.addCell(cell);
        cell = new PdfPCell(new Paragraph(iso, font1));
        cell.setBorder(Rectangle.NO_BORDER);//BOTTOM
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footer.addCell(cell);
        cell = new PdfPCell(otherTable);
        cell.setBorder(Rectangle.NO_BORDER);
        header.addCell(cell);
    }

    public void onStartPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        // ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), 30, 800, 0);
        //    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Right"), 550, 800, 0);
        header.writeSelectedRows(0, -1, (document.right() - document.left() - 600) / 2 + document.leftMargin(), document.top() + document.topMargin(), cb);
    }

    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        //ColumnText.showTextAligned(cb, Element.ALIGN_BOTTOM, header,(document.right() - document.left()+250) / 2+ document.leftMargin(), document.top() + 10, 0);

        footer.writeSelectedRows(0, -1, (document.right() - document.left() - 600) / 2 + document.leftMargin(), document.bottom() + 5, cb);
        //footer.writeSelectedRows(0, -1, 150, 30, cb);
    }
}
