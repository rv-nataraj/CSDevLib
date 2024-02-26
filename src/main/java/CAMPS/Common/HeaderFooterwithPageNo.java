package CAMPS.Common;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.Date;

public class HeaderFooterwithPageNo extends PdfPageEventHelper {

    private PdfTemplate t;
    private Image total;
    protected PdfPTable header;
    protected String foot, iso;

    public HeaderFooterwithPageNo(String f, String i) throws DocumentException {
        Date d = new Date();
        Font font1 = new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC);
        Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
        header = new PdfPTable(1);
        header.setWidthPercentage(100);
        header.setTotalWidth(800f);
        PdfPCell cell = new PdfPCell(new Phrase(d.toString(), font2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        header.addCell(cell);
        foot = f;
        iso = i;
    }

    public HeaderFooterwithPageNo(String f, String i, String h) throws DocumentException {
        Date d = new Date();
        Font font1 = new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC);
        Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
        header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setTotalWidth(800f);
        PdfPCell cell = new PdfPCell(new Phrase(h, font2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        header.addCell(cell);
        cell = new PdfPCell(new Phrase(d.toString(), font2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        header.addCell(cell);
        foot = f;
        iso = i;
    }

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            //total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer, document);
        addFooter(writer, document);
    }

    private void addHeader(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        try {
            header.writeSelectedRows(0, -1, document.leftMargin(), document.getPageSize().getHeight() - document.topMargin() + header.getTotalHeight(), cb);
            //ColumnText.showTextAligned(cb, Element.ALIGN_BOTTOM, header,(document.right() - document.left()+250) / 2+ document.leftMargin(), document.top() + 10, 0);

        } catch (Exception de) {
            throw new ExceptionConverter(de);
        }
    }

    private void addFooter(PdfWriter writer, Document document) {
        PdfPTable footer = new PdfPTable(2);
        // set defaults
        //footer.setWidths(new int[]{24, 2, 1});
        footer.setTotalWidth(700);
        //footer.setLockedWidth(true);
        //footer.getDefaultCell().setFixedHeight(10);
        footer.getDefaultCell().setBorder(Rectangle.TOP);
        footer.getDefaultCell().setBorderColor(BaseColor.WHITE);
        // add copyright
        PdfPCell cell = new PdfPCell(new Phrase(foot, new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC)));
        cell.setColspan(2);
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        footer.addCell(cell);
        cell = new PdfPCell(new Phrase(iso, new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC)));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        footer.addCell(cell);
        // add current page count
        footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
        } catch (Exception e) {
        }
        //footer.addCell(new Phrase(String.format("Page %d of ", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));
        footer.addCell(new Phrase(String.format("%d", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));
        // add placeholder for total page count
        PdfPCell totalPageCount = new PdfPCell(total);
        totalPageCount.setBorder(Rectangle.TOP);
        totalPageCount.setBorderColor(BaseColor.WHITE);
        footer.addCell(totalPageCount);
        // write page
        PdfContentByte canvas = writer.getDirectContent();
        canvas.beginMarkedContentSequence(PdfName.AA);
        footer.writeSelectedRows(0, -1, (document.right() - document.left() - 600) / 2 + document.leftMargin(), document.bottom() + 8, canvas);
        canvas.endMarkedContentSequence();
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
       // ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                //new Phrase(String.valueOf(writer.getPageNumber() - 1), new Font(Font.FontFamily.HELVETICA, 8)),
               // totalWidth, 6, 0);
    }
}
