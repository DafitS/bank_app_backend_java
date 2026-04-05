package com.example.demo.service.report;

import com.example.demo.dto.history.OperationHistoryDto;
import com.example.demo.dto.utility.RaportResponseDto;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;


@Service
@RequiredArgsConstructor
public class FileService {

    private final AccountRepository accountRepository;
    private final OperationHistoryRepository operationHistoryRepository;

    static public void generateReportPdf(RaportResponseDto report, String filePath) {

       try {
           Document document = new Document();
           PdfWriter.getInstance(document, new FileOutputStream(filePath));

           document.open();

           Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
           Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

           Paragraph title = new Paragraph("Raport finansowy", titleFont);
           title.setSpacingAfter(15f);
           title.setAlignment(Element.ALIGN_LEFT);

           document.add(title);

           LineSeparator separator = new LineSeparator();
           document.add(separator);

           Paragraph space = new Paragraph(" ");
           space.setSpacingAfter(10f);
           document.add(space);

           document.add(new Paragraph("Przychody: " + report.getIncome(), normalFont));
           document.add(new Paragraph("Wydatki: " + report.getExpenses(), normalFont));
           document.add(new Paragraph("Bilans: " + report.getBalance(), normalFont));

           Paragraph transactionTitle = new Paragraph("Transakcje:", titleFont);
           transactionTitle.setSpacingAfter(10f);
           document.add(transactionTitle);

           PdfPTable table = new PdfPTable(5);
           table.setWidthPercentage(100);
           table.setSpacingBefore(5f);
           table.setSpacingAfter(10f);

           table.addCell("Data");
           table.addCell("Typ operacji");
           table.addCell("Kwota");
           table.addCell("Saldo po operacji");
           table.addCell("Powiązane konto");

           for (OperationHistoryDto op : report.getOperations()) {
               table.addCell(op.getCreatedAt().toString());
               table.addCell(op.getOperationType().toString());
               table.addCell(op.getAmount().toString());
               table.addCell(op.getBalanceAfter().toString());
               table.addCell(op.getRelatedAccountNumber() != null ? op.getRelatedAccountNumber() : "-");
           }

           document.add(table);
           document.close();

           System.out.println("PDF zapisany: " + filePath);

       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }
}
