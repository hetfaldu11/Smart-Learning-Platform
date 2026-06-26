package com.fm.smartlearningplatform.payment.service;

import com.fm.smartlearningplatform.payment.model.Invoice;
import com.fm.smartlearningplatform.payment.model.Order;
import com.fm.smartlearningplatform.user.dto.userProfile.response.UserProfileResponse;
import com.fm.smartlearningplatform.user.model.UserProfile;
import com.fm.smartlearningplatform.user.service.UserProfileService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class InvoicePdfService {

    private final UserProfileService userProfileService;

    public byte[] generate(Invoice invoice) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            addHeader(document, invoice);
            addBillingInfo(document, invoice);
            addItemTable(document, invoice);
            addTotals(document, invoice);
            addFooter(document);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice PDF.", e);
        }
    }

    // ─── Header ───────────────────────────────────────────────

    private void addHeader(Document document, Invoice invoice) throws DocumentException {
        Font titleFont = new Font(Font.HELVETICA, 24, Font.BOLD, Color.DARK_GRAY);
        Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.DARK_GRAY);

        Paragraph title = new Paragraph("INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_RIGHT);
        document.add(title);

        Paragraph meta = new Paragraph();
        meta.setAlignment(Element.ALIGN_RIGHT);
        meta.add(new Chunk("Invoice No : " + invoice.getInvoiceNumber() + "\n", normalFont));
        meta.add(new Chunk("Order No   : " + invoice.getOrder().getOrderNumber() + "\n", normalFont));
        meta.add(new Chunk("Date       : " + invoice.getGeneratedAt().toLocalDate() + "\n", normalFont));
        meta.add(new Chunk("Status     : " + invoice.getStatus().name() + "\n", normalFont));
        document.add(meta);

        document.add(Chunk.NEWLINE);
    }

    // ─── Billing Info ─────────────────────────────────────────

    private void addBillingInfo(Document document, Invoice invoice) throws DocumentException {
        Font labelFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.DARK_GRAY);
        Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.DARK_GRAY);

        Order order = invoice.getOrder();
        UserProfileResponse userProfile = userProfileService.findByUserId(order.getUser().getId());

        document.add(new Paragraph("Bill To:", labelFont));
        document.add(new Paragraph(userProfile.firstName() + " " + userProfile.lastName(), normalFont));
        document.add(new Paragraph(order.getUser().getEmail(), normalFont));
        document.add(Chunk.NEWLINE);

        // Payment info
        document.add(new Paragraph("Payment:", labelFont));
        document.add(new Paragraph("Method  : " + invoice.getPayment().getMethod().name(), normalFont));
        document.add(new Paragraph("Currency: " + invoice.getCurrency(), normalFont));
        document.add(Chunk.NEWLINE);
    }

    // ─── Item Table ───────────────────────────────────────────

    private void addItemTable(Document document, Invoice invoice) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{0.5f, 3f, 1f, 1.5f});

        addTableHeader(table);
        addTableRows(table, invoice);

        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);
        Color headerBg = new Color(63, 81, 181);

        for (String col : new String[]{"#", "Course", "Qty", "Price"}) {
            PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
            cell.setBackgroundColor(headerBg);
            cell.setPadding(8);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
    }

    private void addTableRows(PdfPTable table, Invoice invoice) {
        Font rowFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.DARK_GRAY);
        Color rowBg = new Color(245, 245, 245);

        int index = 1;
        for (var item : invoice.getOrder().getItems()) {
            Color bg = index % 2 == 0 ? rowBg : Color.WHITE;

            addCell(table, String.valueOf(index++), rowFont, bg);
            addCell(table, item.getCourse().getTitle(), rowFont, bg);
            addCell(table, "1", rowFont, bg);
            addCell(table, invoice.getCurrency() + " " + item.getFinalPrice(), rowFont, bg);
        }
    }

    private void addCell(PdfPTable table, String text, Font font, Color bg) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bg);
        cell.setPadding(7);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    // ─── Totals ───────────────────────────────────────────────

    private void addTotals(Document document, Invoice invoice) throws DocumentException {
        Font boldFont = new Font(Font.HELVETICA, 11, Font.BOLD, Color.DARK_GRAY);
        Font normalFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.DARK_GRAY);

        PdfPTable totals = new PdfPTable(2);
        totals.setWidthPercentage(40);
        totals.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totals.setSpacingBefore(10f);

        String currency = invoice.getCurrency();

        addTotalRow(totals, "Subtotal :", currency + " " + invoice.getSubtotalAmount(), normalFont);
        addTotalRow(totals, "Discount :", "- " + currency + " " + invoice.getDiscountAmount(), normalFont);
        addTotalRow(totals, "Tax      :", currency + " " + invoice.getTaxAmount(), normalFont);
        addTotalRow(totals, "Total    :", currency + " " + invoice.getTotalAmount(), boldFont);

        document.add(totals);
    }

    private void addTotalRow(PdfPTable table, String label, String value, Font font) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, font));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(5);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(5);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    // ─── Footer ───────────────────────────────────────────────

    private void addFooter(Document document) throws DocumentException {
        Font footerFont = new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY);
        Paragraph footer = new Paragraph("Thank you for your purchase!", footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20f);
        document.add(footer);
    }
}