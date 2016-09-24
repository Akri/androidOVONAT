package de.akricorp.ovonat.actionObjects.Playroom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import de.akricorp.ovonat.Animation;
import de.akricorp.ovonat.GameObject;

/**
 * Created by Hännes on 21.09.2016.
 */

public class StoneScissorPaperObject extends GameObject {

    Bitmap spritesheed;
    private Animation animation = new Animation();

    public StoneScissorPaperObject(Bitmap res, int w, int h, int numFrames,int positionX, int positionY,float resolutionControlFactor)
    {super(positionX,positionY,w,h, resolutionControlFactor);
        height = h*(int)resolutionControlFactor;
        width = w*(int)resolutionControlFactor;
        isShown = true;


        Bitmap[] image = new Bitmap[numFrames];
        spritesheed = res;

        for(int i = 0; i < image.length;i++)
        {
            image[i] = Bitmap.createScaledBitmap(spritesheed,  width, height,false);
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
