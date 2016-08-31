package de.akricorp.ovonat;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Hannes on 28.07.2015.
 */
public class Room {

    private Bitmap image;
    private int x, y;


    public Room(Bitmap image){

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
        canvas.drawBitmap(image, 0,0,null);
    }
}