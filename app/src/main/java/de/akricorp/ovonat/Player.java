package de.akricorp.ovonat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;


/**
 * Created by Hannes on 28.07.2015.
 */
public class Player extends GameObject{
    Bitmap body;
    Bitmap eyes;

    private boolean playing = true;
    private Animation bodyAnimation = new Animation();
    private Animation eyesAnimation = new Animation();
    float resolutionControlFactor;

    public Player(Bitmap[] res, int w, int h, int numFrames, int positionX, int positionY,float resolutionControlFactorX,float resolutionControlFactorY)
    {super(res,positionX,positionY,w,h, resolutionControlFactorX,resolutionControlFactorY,numFrames);
        Log.d("player", "x: "+x);
        Log.d("player", "y: "+y);



    }

    public void moveToPosition(float moveTo, float currentPosition)
    {

    }






    public void setPlaying(boolean b){playing = b;}
    public boolean getPlaying(){return playing;}


}