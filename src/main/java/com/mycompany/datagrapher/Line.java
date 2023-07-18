package com.mycompany.datagrapher;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class Line extends Shape {
    private final float beginX;
    private final float endX;
    private final float beginY;
    private final float endY;

    public Line(float x, float y) throws AppException {
        super();
        this.beginX = convertIndexToPageLocation(x) * colSpacing - symbolSize / 2;
        this.endX = beginX + symbolSize;
        this.beginY = convertIndexToPageLocation(y) * rowSpacing;
        this.endY = beginY;
    }

    @Override
    public void drawShape(PdfCanvas canvas) throws AppException {
        canvas.setLineWidth(lineWidth);

        canvas.moveTo(beginX, beginY);
        canvas.lineTo(endX, endY);
        canvas.stroke();
    }
}