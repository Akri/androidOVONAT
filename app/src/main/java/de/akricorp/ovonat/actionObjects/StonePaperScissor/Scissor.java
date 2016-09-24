package de.akricorp.ovonat.actionObjects.StonePaperScissor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import de.akricorp.ovonat.Animation;
import de.akricorp.ovonat.GameObject;

/**
 * Created by Hännes on 21.09.2016.
 */

public class Scissor extends GameObject {
    Bitmap spritesheed;
    private Animation animation = new Animation();

    public Scissor(Bitmap res, int w, int h, int numFrames,int positionX, int positionY,float resolutionControlFactor)
    {super(positionX,positionY,w,h, resolutionControlFactor);
        setX(400);
        setY(200);
        height = h;
        width = w;
        isShown = true;
        Bitmap[] image = new Bitmap[numFrames];
        spritesheed = res;

        for(int i = 0; i < image.length;i++)
        {
            image[i] = Bitmap.createBitmap(spritesheed, i*width,0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);

    }

    public void update()
    {
        if(isShown){ animation.update();}
    }
    public void draw(Canvas canvas)
    {  if(isShown){


        canvas.drawBitmap(animation.getImage(), x, y, null); }

    }


    @Override
    public void clickReaction(){//open fridge menu
    }


}


