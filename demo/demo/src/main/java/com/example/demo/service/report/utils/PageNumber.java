package com.example.demo.service.report.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;

public class PageNumber extends PdfPageEventHelper {
    Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.ITALIC, Color.GRAY);
    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        Rectangle pageSize = document.getPageSize();
        String text = "Strona " + writer.getPageNumber();
        ColumnText.showTextAligned(
                writer.getDirectContent(),
                Element.ALIGN_CENTER,
                new Phrase(text, footerFont),
                (pageSize.getLeft() + pageSize.getRight()) / 2,
                pageSize.getBottom() + 20,
                0
        );
    }
}
