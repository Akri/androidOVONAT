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
    protected int originalWidth;
    protected int originalHeight;
    protected int width;
    protected int height;
    private float resolutionControlFactorX;
    private float resolutionControlFactorY;


    public boolean isShown;

    public GameObject(int positionX,int positionY,int w,int h, float resolutionControlFactorX,float resolutionControlFactorY){
        originalHeight =h;
        originalWidth = w;
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY = resolutionControlFactorY;
        height = (int)(h*resolutionControlFactorY);
        width =(int)( w*resolutionControlFactorX);

        this.originalX = positionX;
        this.originalY = positionY;
        newScale(resolutionControlFactorX, resolutionControlFactorY);

    }

    public void setX(int newX)
    {
        x = (int)(newX*resolutionControlFactorX);
    }

    public void setY(int newY)
    {
        y = (int)(newY*resolutionControlFactorY);
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




    public void newScale(float resolutionControlFactorX, float resolutionControlFactorY){
        x= (int)((float)originalX*resolutionControlFactorX);
        y = (int)((float)originalY*resolutionControlFactorY);
        Log.d("Positions new scale",this.getClass() + ": x: " + x + ", y: " + y);
    }

    public void clickReaction(){}

    public void hide(){
        isShown = false;
    }

    public void show(){
        isShown = true;
    }
}
