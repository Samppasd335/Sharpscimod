package com.mycompany.datagrapher;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.io.IOException;

public abstract class Shape {
    protected final float symbolSize;
    protected final float rowSpacing;
    protected final float colSpacing;
    protected final float pageMargin;
    protected final float lineWidth;
    protected final float centerAdjustment;

    protected Shape() throws IOException {
        this.rowSpacing = ImageSettings.getSetting("rowSpacing");
        this.colSpacing = ImageSettings.getSetting("colSpacing");
        this.symbolSize = ImageSettings.getSetting("symbolSize");
        this.pageMargin = ImageSettings.getSetting("pageMargin");
        this.lineWidth = ImageSettings.getSetting("lineWidth");
        
        this.centerAdjustment = pageMargin + symbolSize / 2 + lineWidth;
    }

    public abstract void drawShape(PdfCanvas canvas) throws IOException;
    
    protected float convertIndexToPageLocation(float index) {
        return index * symbolSize + centerAdjustment;
    }
}