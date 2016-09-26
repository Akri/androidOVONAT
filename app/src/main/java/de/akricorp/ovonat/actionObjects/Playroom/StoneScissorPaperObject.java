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


    private Animation animation = new Animation();

    public StoneScissorPaperObject(Bitmap[] res, int w, int h, int numFrames,int positionX, int positionY,float resolutionControlFactorX,float resolutionControlFactorY)
    {super(res,positionX,positionY,w,h, resolutionControlFactorX, resolutionControlFactorY, numFrames);


    }



    public void update()
    {
       if(isShown){ animation.update();}
    }

    @Override
    public void clickReaction(){//open fridge menu
    }


}
