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

    public Player(Bitmap body, Bitmap eyes,int w, int h, int numFrames, int positionX, int positionY,float resolutionControlFactor)
    {super(positionX,positionY, resolutionControlFactor);
        originalX = x = 300;
        originalY = y= 150;
        height = h;
        width = w;
        this.resolutionControlFactor = resolutionControlFactor;
        Bitmap[] bodyImage = new Bitmap[numFrames];
        Bitmap[] eyesImage = new Bitmap[numFrames];
        this.body = body;
        this.eyes = eyes;

        for(int i = 0; i < bodyImage.length;i++)   //eiglt gilt es für alle images also bodyImage vllt falsche variable
        {
            bodyImage[i] = Bitmap.createBitmap(body, i*width,0, width, height);
            eyesImage[i] = Bitmap.createBitmap(eyes, i*width,0,width,height);
        }

        bodyAnimation.setFrames(bodyImage);
        eyesAnimation.setFrames(eyesImage);
        bodyAnimation.setDelay(100);
        eyesAnimation.setDelay(100);

    }

    public void moveToPosition(float moveTo, float currentPosition)
    {

    }

    public void update()
    {
        bodyAnimation.update();
        eyesAnimation.update();
    }
    public void draw(Canvas canvas)
    {
        Log.d("player gets Drawn", "true");
        canvas.drawBitmap(bodyAnimation.getImage(),x*resolutionControlFactor,y*resolutionControlFactor,null);
        canvas.drawBitmap(eyesAnimation.getImage(),x*resolutionControlFactor,y*resolutionControlFactor,null);

    }

    public void setPlaying(boolean b){playing = b;}
    public boolean getPlaying(){return playing;}


}