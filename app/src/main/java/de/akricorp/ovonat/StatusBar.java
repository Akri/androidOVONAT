package de.akricorp.ovonat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;



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
    private Bitmap icon;
    private int width;
    private int height;


    public StatusBar(float resolutionControlFactorX,float resolutionControlFactorY,int value, int position, int w,int h, Bitmap icon){
        this.icon = icon;
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY = resolutionControlFactorY;
        width =(int) (w*resolutionControlFactorX);
        height = (int)(h*resolutionControlFactorY);
        xPosition = (int)(xPosition*resolutionControlFactorX);
        yPosition = (int)(yPosition*resolutionControlFactorY);
        barHeight = (int)(barHeight*resolutionControlFactorY);
        barWidth = (int)(barWidth*resolutionControlFactorX);
        setValue(value);
        xPosition += position*barOffset*resolutionControlFactorX;
        setupVisualisation();
        setupIcon();
    }
    public void setValue(int value){

        currentValue = maxValue-value;
        if(maxValue-currentValue < 0){currentValue = maxValue;}
        if(currentValue < 0){currentValue = 0;}

    }
    public void addValue(int value){
        currentValue -= value;
        if(maxValue-currentValue < 0){currentValue = maxValue;}
        if(currentValue < 0){currentValue = 0;}
        update();
    }
    public int getValue(){
        return maxValue - currentValue;
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


    public void setupIcon(){
        icon = Bitmap.createScaledBitmap(icon,(int)(width*0.8),(int)(height*0.8),false);

    }

    public void update(){
        bar.set(xPosition, yPosition + currentValue * barHeight / maxValue, xPosition + barWidth, yPosition + barHeight);
    }


    public void draw(Canvas canvas){
        canvas.drawRect(bar, barPaint);
        canvas.drawRect(barBorder, barBorderPaint);
        canvas.drawBitmap(icon,xPosition-(int)(5*resolutionControlFactorX),yPosition-height,null);

    }
}