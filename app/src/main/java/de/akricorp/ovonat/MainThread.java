package de.akricorp.ovonat;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by Hannes on 23.07.2015.
 */
public class MainThread extends Thread {

    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    private static Canvas canvas;
    private boolean scaleDone = false;


    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void run(){

        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing

            try{



                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){

                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);

                }


            }catch(Exception e){}

            finally{
                if(canvas != null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){e.printStackTrace();}
                }
            }
            timeMillis = (System.nanoTime()-startTime)/1000000;
            waitTime = targetTime-timeMillis;
            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);

            }


        }
    }

    public void setRunning(boolean b)
    {running = b;}
}