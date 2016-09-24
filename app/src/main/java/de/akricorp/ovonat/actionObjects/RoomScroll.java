package de.akricorp.ovonat.actionObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import de.akricorp.ovonat.GameObject;
import de.akricorp.ovonat.R;

/**
 * Created by Hannes on 02.08.2015.
 */
public class RoomScroll   {



    public boolean up = false;
    //private Bitmap bg;
    private Rect bg;
    private boolean active = true;
    private Paint bgPaint = new Paint();
    Bitmap kitchenButton;
    Bitmap playRoomButton;
    Bitmap outsideButton;
    Bitmap bathButton;
    ArrayList<Bitmap> roomButtonArrayList = new ArrayList<Bitmap>();
    float resolutionControlFactor;
    Rect scrollDownRect;
    public int downWidth;
    public int upWidth;
    public int downHeight;
    public int upHeight;
    public int upX;
    public int upY;
    public int downX;
    public int downY;
    public int currentHeight;
    public int currentWidth;
    public int currentX;
    public int currentY;
    public int canvasWidth;
    public int canvasHeight;
    public int strokeWidth;



    public RoomScroll(int canvasWidth, int canvasHeight,float resolutionControlFactor, Bitmap kitchen, Bitmap playRoom, Bitmap outside, Bitmap bath){
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.resolutionControlFactor = resolutionControlFactor;
        this.upWidth =  canvasWidth/2;
        this.upHeight = canvasHeight/8;
        this.upX = (canvasWidth/2) - (this.upWidth/2);
        this.upY= (canvasHeight-upHeight);
        this.downWidth =  canvasWidth/4;
        this.downHeight = canvasHeight/12;
        this.downX = (canvasWidth/2) - (this.downWidth/2);
        this.downY= (canvasHeight-downHeight);
        strokeWidth = (int) ((float) 5 * resolutionControlFactor);
        bgPaint.setColor(Color.rgb(0, 0, 0));
        bgPaint.setStrokeWidth(strokeWidth);
        bgPaint.setStyle(Paint.Style.STROKE);

        kitchenButton =  Bitmap.createScaledBitmap(kitchen,(int)(80*resolutionControlFactor),(int)(80*resolutionControlFactor),false);
        roomButtonArrayList.add(kitchenButton);
        playRoomButton = Bitmap.createScaledBitmap(playRoom,(int)(80*resolutionControlFactor),(int)(80*resolutionControlFactor),false);
        roomButtonArrayList.add(playRoomButton);
        outsideButton = Bitmap.createScaledBitmap(outside,(int)(80*resolutionControlFactor),(int)(80*resolutionControlFactor),false);
        roomButtonArrayList.add(outsideButton);
        bathButton = Bitmap.createScaledBitmap(bath,(int)(80*resolutionControlFactor),(int)(80*resolutionControlFactor),false);
        roomButtonArrayList.add(bathButton);
        scroll();

        Log.d("roomScroll", "screenWidth: "+ canvasWidth);
        Log.d("roomScroll", "currentX: "+currentX);
        Log.d("roomScroll", "downX: "+downX);

    }







    public void update()
    {

    }
    public void draw(Canvas canvas)
    {if(active){
        bg = new Rect(currentX,currentY,currentX+currentWidth, currentY + currentHeight);
        //canvas.drawBitmap(bg, x, y, null);
        canvas.drawRect(bg, bgPaint);
        if(up){
            for(int i = 0; i < 4;i++){
                canvas.drawBitmap(roomButtonArrayList.get(i),upX+resolutionControlFactor*115*i,upY+strokeWidth/2,null);

            }}
    }}


    public void scroll(){      //scrolls the roomScroll scrollbar down by changing its measurements

    if(!up){
        currentX = upX;
        currentY = upY;
        currentHeight = upHeight;
        currentWidth = upWidth;
        up = true;
    }
        else{
        currentX = downX;
        currentY = downY;
        currentHeight = downHeight;
        currentWidth = downWidth;
        up = false;

    }

    }

   public void scrollUp(){
       up = true;
   }
    public void scrollDown(){
        up = false;
    }


    public Rect getRectangle(){

        scrollDownRect = new Rect(currentX,currentY,currentX+currentWidth,currentY+currentHeight);
        return scrollDownRect;
    }

  /*  public void scroll()
    {

        long startTime;
        long timeMillis;
        long waitTime = 3;
        startTime = System.nanoTime();
        while(scrolledOut){

            timeMillis = (System.nanoTime()-startTime)/1000000000;


            if(timeMillis >= waitTime){
                scrollDown();




            }}
            }*/





}