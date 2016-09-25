package de.akricorp.ovonat.actionObjects.StonePaperScissor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import de.akricorp.ovonat.Animation;
import de.akricorp.ovonat.GameObject;

/**
 * Created by Hännes on 21.09.2016.
 */

public class Paper extends GameObject{

    Bitmap spritesheed;
    private Animation animation = new Animation();

    public Paper(Bitmap res, int w, int h, int numFrames,int positionX, int positionY,float resolutionControlFactorX,float resolutionControlFactorY)
    {super(positionX,positionY,w,h, resolutionControlFactorX,resolutionControlFactorY);

        isShown = true;


        Bitmap[] image = new Bitmap[numFrames];
        spritesheed = res;

        for(int i = 0; i < image.length;i++)
        {
            image[i] = Bitmap.createScaledBitmap(spritesheed,  width/2, height/2,false);
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



