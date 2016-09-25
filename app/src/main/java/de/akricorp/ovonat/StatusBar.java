package de.akricorp.ovonat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Hannes on 01.09.2015.
 */
public class StatusBar {
    private int currentValue;
    private int maxValue = 10;
    private int xPosition = 700;
    private int yPosition = 50;
    private int barHeight = 70;
    private int barWidth = 30;
    private int barOffset = 40;
    private Rect barBorder;
    private Paint barBorderPaint = new Paint();
    private Paint barPaint = new Paint();
    private Rect bar;
    private float resolutionControlFactorX;
    private float resolutionControlFactorY;

    public StatusBar(float resolutionControlFactorX,float resolutionControlFactorY,int value, int position){
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY = resolutionControlFactorY;
        xPosition = (int)(xPosition*resolutionControlFactorX);
        yPosition = (int)(yPosition*resolutionControlFactorY);
        barHeight = (int)(barHeight*resolutionControlFactorY);
        barWidth = (int)(barWidth*resolutionControlFactorX);
        setValue(maxValue-value);
        xPosition += position*barOffset*resolutionControlFactorX;
        setupVisualisation();
    }
    public void setValue(int value){
        currentValue = value;
    }
    public int getValue(){
        return currentValue;
    }

    public void setupVisualisation() {
        barBorder = new Rect(xPosition, yPosition, xPosition + barWidth, yPosition + barHeight);
        barBorderPaint.setColor(Color.BLACK);
        barBorderPaint.setStrokeWidth((int)((float)4*resolutionControlFactorX));
        barBorderPaint.setStyle(Paint.Style.STROKE);
        bar = new Rect(xPosition, yPosition + currentValue * barHeight / maxValue, xPosition + barWidth, yPosition + barHeight);
        barPaint.setColor(Color.RED);
        barPaint.setStrokeWidth(1);
        barPaint.setStyle(Paint.Style.FILL);
    }


    public void draw(Canvas canvas){
        canvas.drawRect(bar, barPaint);
        canvas.drawRect(barBorder, barBorderPaint);
    }
}