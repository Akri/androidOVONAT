package de.akricorp.ovonat;

import android.graphics.Bitmap;

/**
 * Created by Hannes on 28.07.2015.
 */
public class Animation {

    //comment
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;



    public void setFrames(Bitmap[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    public void setDelay(long d){delay = d;}
    public void setFrame(int i){currentFrame = i;}

    public void update(){
        long elapsedTime = (System.nanoTime()-startTime)/1000000;

        if(elapsedTime>delay)
        {
            currentFrame++;
            startTime = System.nanoTime();

        }
        if(currentFrame == frames.length)
        {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public Bitmap getImage(){
        return frames[currentFrame];
    }

    public int getCurrentFrame(){return currentFrame;}
    public boolean getPlayedOnce(){return playedOnce;}

}