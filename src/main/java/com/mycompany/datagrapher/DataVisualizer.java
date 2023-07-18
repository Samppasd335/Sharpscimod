package com.mycompany.datagrapher;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataVisualizer {
    private final List<List<Shape>> drawables;
    private float pageWidth;
    private float pageHeight;

    public DataVisualizer() {
        this.drawables = new ArrayList<>();
    }

    public void addDrawable(List<Shape> row) {
        drawables.add(row);
    }

    private void calculatePageSize() throws AppException {
        float rowSpacing = ImageSettings.getSetting("rowSpacing");
        float colSpacing = ImageSettings.getSetting("colSpacing");
        float symbolSize = ImageSettings.getSetting("symbolSize");
        float pageMargin = ImageSettings.getSetting("pageMargin");
        float lineWidth = ImageSettings.getSetting("lineWidth");

        int totalRows = drawables.size();
        int maxRowLength = drawables.stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        pageWidth = (lineWidth + pageMargin) * 2 + colSpacing * symbolSize * maxRowLength;
        pageHeight = (lineWidth + pageMargin) * 2 + rowSpacing * symbolSize * totalRows;
    }
    public byte[] generatePdfAsByteArray() throws AppException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        calculatePageSize();
        pdfDoc.setDefaultPageSize(new PageSize(pageWidth, pageHeight));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

            for (List<Shape> drawableList : drawables) {
                for (Shape drawable : drawableList) {
                    drawable.drawShape(canvas);
                }
            }

        pdfDoc.close();
        return outputStream.toByteArray();
    }
}