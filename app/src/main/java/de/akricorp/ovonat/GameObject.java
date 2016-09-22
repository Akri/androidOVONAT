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


    public boolean isShown;

    public GameObject(int positionX,int positionY, float resolutionControlFactor){
        this.originalX = positionX;
        this.originalY = positionY;
        scale(resolutionControlFactor);

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




    public void scale(float resolutionControlFactor){
        x= (int)((float)originalX*resolutionControlFactor);
        y = (int)((float)originalY*resolutionControlFactor);
        Log.d("Positions",this.getClass() + ": x: " + x + ", y: " + y);
    }

    public void clickReaction(){}

    public void hide(){
        isShown = false;
    }

    public void show(){
        isShown = true;
    }
}
