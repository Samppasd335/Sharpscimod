package com.mycompany.datagrapher;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class Circle extends Shape {
    private final float x;
    private final float y;
    private final boolean filled;
    private final boolean hasBorder;

    public Circle(float x, float y, boolean filled, boolean hasBorder) 
            throws AppException {
        super();
        
        this.x = convertIndexToPageLocation(x) * colSpacing;
        this.y = convertIndexToPageLocation(y) * rowSpacing;
        this.filled = filled;
        this.hasBorder = hasBorder;
    }

    @Override
    public void drawShape(PdfCanvas canvas) throws AppException {
        canvas.setLineWidth(ImageSettings.getSetting("lineWidth"));
        canvas.circle(x, y, symbolSize/2);

        if (filled && hasBorder) {
            canvas.setStrokeColor(ColorConstants.RED);
            canvas.setFillColor(ColorConstants.YELLOW);
        } else {
            canvas.setStrokeColor(ColorConstants.BLACK); 
            canvas.setFillColor(ColorConstants.BLACK);
        }
        
        if (filled) {             
            canvas.fillStroke();
        } else {
            canvas.stroke();
        }
    }
}