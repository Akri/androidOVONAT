package de.akricorp.ovonat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by Hannes on 28.07.2015.
 */
public class Room {

    private Bitmap image;
    private int x, y;
    private float resolutionControlFactor;


    public Room(Bitmap image,float resolutionControlFactor){
        this.resolutionControlFactor = resolutionControlFactor;
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
    {   Log.d("scale", resolutionControlFactor+"");
        Bitmap scaledImage = Bitmap.createScaledBitmap(image, (int)(image.getWidth()/3*resolutionControlFactor),(int)(image.getHeight()/3*resolutionControlFactor),false);
        canvas.drawBitmap(scaledImage, 0,0,null);
    }
}