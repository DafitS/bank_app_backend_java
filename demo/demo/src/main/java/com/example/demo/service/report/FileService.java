package com.example.demo.service.report;

import com.example.demo.dto.history.OperationHistoryDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.dto.utility.RaportResponseDto;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.service.report.utils.PageNumber;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AccountRepository accountRepository;
    private final OperationHistoryRepository operationHistoryRepository;

    static public byte[] generateReportPdf(RaportResponseDto report, UserResponseDto user) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 40, 40, 50, 40);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setPageEvent(new PageNumber());
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String generateDate = LocalDate.now().format(formatterDate);

            Paragraph date = new Paragraph("Data wygenerowania: " + generateDate, normalFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            date.setSpacingAfter(10f);

            document.add(date);

            Paragraph title = new Paragraph("RAPORT FINANSOWY", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(15f);
            document.add(title);

            document.add(new LineSeparator());
            document.add(new Paragraph(" "));

            PdfPTable userTable = new PdfPTable(2);
            userTable.setWidthPercentage(100);
            userTable.setSpacingBefore(10f);
            userTable.setSpacingAfter(20f);
            userTable.setWidths(new float[]{2, 3});

            userTable.addCell(getLabelCell("Imię i nazwisko:"));
            userTable.addCell(getValueCell(user.getName() + " " + user.getSurname()));

            userTable.addCell(getLabelCell("Email:"));
            userTable.addCell(getValueCell(user.getEmail()));

            userTable.addCell(getLabelCell("PESEL:"));
            userTable.addCell(getValueCell(user.getPesel()));

            if (user.getUserAddress() != null) {
                userTable.addCell(getLabelCell("Adres:"));
                userTable.addCell(getValueCell(
                        user.getUserAddress().getStreet() + " " +
                                user.getUserAddress().getBuildingNumber() +
                                (user.getUserAddress().getApartmentNumber() != null
                                        ? "/" + user.getUserAddress().getApartmentNumber()
                                        : "") + ", " +
                                user.getUserAddress().getPostalCode() + " " +
                                user.getUserAddress().getCity() + ", " +
                                user.getUserAddress().getCountry()
                ));
            }

            document.add(userTable);

            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(40);
            summaryTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            summaryTable.setSpacingBefore(10f);
            summaryTable.setSpacingAfter(20f);
            summaryTable.setWidths(new float[]{2, 2});

            summaryTable.addCell(getLabelCell("Przychody:"));
            summaryTable.addCell(getValueCell(String.format("%.2f", report.getIncome())));

            summaryTable.addCell(getLabelCell("Wydatki:"));
            summaryTable.addCell(getValueCell(String.format("%.2f", report.getExpenses())));

            summaryTable.addCell(getLabelCell("Bilans:"));
            summaryTable.addCell(getValueCell(String.format("%.2f", report.getBalance())));

            document.add(summaryTable);

            Paragraph transactionTitle = new Paragraph("Transakcje", headerFont);
            transactionTitle.setSpacingAfter(10f);
            document.add(transactionTitle);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.5f, 1.5f, 1, 1.5f, 2.1f});

            table.addCell(getHeaderCell("Data"));
            table.addCell(getHeaderCell("Typ"));
            table.addCell(getHeaderCell("Kwota"));
            table.addCell(getHeaderCell("Saldo"));
            table.addCell(getHeaderCell("Konto"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            for (OperationHistoryDto op : report.getOperations()) {
                table.addCell(getCell(op.getCreatedAt().format(formatter), smallFont));
                table.addCell(getCell(op.getOperationType().toString(), smallFont));
                table.addCell(getCell(String.format("%.2f", op.getAmount()), smallFont));
                table.addCell(getCell(String.format("%.2f", op.getBalanceAfter()), smallFont));
                table.addCell(getCell(op.getRelatedAccountNumber() != null ? op.getRelatedAccountNumber() : "-", smallFont));
            }

            document.add(table);
            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PdfPCell getCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private static PdfPCell getHeaderCell(String text) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(new Color(230, 230, 230));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private static PdfPCell getLabelCell(String text) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }

    private static PdfPCell getValueCell(String text) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }
}