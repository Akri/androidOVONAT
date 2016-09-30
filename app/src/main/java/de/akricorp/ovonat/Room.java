package de.akricorp.ovonat;

import android.graphics.Bitmap;
import android.graphics.Canvas;



public class Room {

    private Bitmap image;
    private int x, y;
    private float resolutionControlFactorX;
    private float resolutionControlFactorY;


    public Room(Bitmap image,float resolutionControlFactorX,float resolutionControlFactorY){
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY = resolutionControlFactorY;
        this.image = image;

    }

    public void update(Bitmap image)
    {
        this.image = image;

    }

    public Bitmap getBitmap(){
        return image;
    }

    public void draw(Canvas canvas)
    {
        Bitmap scaledImage = Bitmap.createScaledBitmap(image, (int)(image.getWidth()*resolutionControlFactorX),(int)(image.getHeight()*resolutionControlFactorY),false);
        canvas.drawBitmap(scaledImage, 0,0,null);
    }
}