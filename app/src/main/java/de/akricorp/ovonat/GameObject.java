package de.akricorp.ovonat;

import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Hannes on 29.07.2015.
 */
public abstract class GameObject {
    protected int originalX; //x value for standart screenWidth
    protected int originalY; //y value for standart screenWHeight
    public int x;
    public int y;
    protected int width;
    protected int height;
    protected int savedX;
    protected int savedY;
    protected int savedHeight;
    protected int savedWidth;
    protected int canvasWidth;
    protected int canvasHeight;
    public float resolutionControlFactor;

    public GameObject(int canvasWidth,int canvasHeight){
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;

    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getWidth()
    {
        return this.width;
    }

    public Rect getRectangle()
    {
        return new Rect(x,y, x+width,y+height);
    }


    public void scale(int canvasWidth, int canvasHeight,int gameWidth,int gameHeight){

    }


    public void scale(int newCanvasHeight, int newCanvasWidth){
        x= (int)((float)originalX*((float)canvasWidth/(float)newCanvasWidth));
        y = (int)((float)originalY*((float)canvasHeight/(float)newCanvasHeight));
    }

    public void clickReaction(){}
}
