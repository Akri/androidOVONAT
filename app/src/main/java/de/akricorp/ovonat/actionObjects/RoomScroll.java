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
/*public class RoomScroll  {



    boolean collisionRectsDone = false;
    //private Bitmap bg;
    private Rect bg;
    private boolean active = true;
    private Paint bgPaint = new Paint();
    public boolean scrolledOut;
    Bitmap kitchenButton;
    Bitmap playRoomButton;
    Bitmap outsideButton;
    Bitmap bathButton;
    ArrayList<Bitmap> roomButtonArrayList = new ArrayList<Bitmap>();
    float resolutionControlFactor;
    Rect scrollDownRect;


    public RoomScroll(int canvasWidth, int canvasHeight,float resolutionControlFactor, Bitmap kitchen, Bitmap playRoom, Bitmap outside, Bitmap bath){
        super(canvasWidth, canvasHeight,resolutionControlFactor);
        this.resolutionControlFactor = resolutionControlFactor;
        this.width =  canvasWidth/3;
        this.height = canvasHeight/10;
        this.x = (canvasWidth/2) - (this.width/2);
        this.y = (canvasHeight-height)  ;
        bg = new Rect(x,y,x+width,y + height);
        bgPaint.setColor(Color.rgb(0, 0, 0));
        bgPaint.setStrokeWidth((int) ((float) 5 * resolutionControlFactor));
        bgPaint.setStyle(Paint.Style.STROKE);

        kitchenButton = kitchen;
        roomButtonArrayList.add(kitchenButton);
        playRoomButton = playRoom;
        roomButtonArrayList.add(playRoomButton);
        outsideButton = outside;
        roomButtonArrayList.add(outsideButton);
        bathButton = bath;
        roomButtonArrayList.add(bathButton);

        scrollDown();

    }


    public void scale(int canvasWidth,int canvasHeight){

        this.canvasHeight=canvasHeight;
        this.canvasWidth=canvasWidth;


    }

    public void update()
    {

    }
    public void draw(Canvas canvas)
    {if(active){
        //canvas.drawBitmap(bg, x, y, null);
        canvas.drawRect(bg, bgPaint);
        if(scrolledOut){
            for(int i = 0; i < 4;i++){
                canvas.drawBitmap(roomButtonArrayList.get(i),x+200*resolutionControlFactor*i,y,null);

            }}
    }}


    public void scrollDown(){      //scrolls the roomScroll scrollbar down by changing its measurements
        height = canvasHeight/20;
        width= canvasWidth/8;
        this.x = canvasWidth/2-width/2;
        this.y = canvasHeight-height;
        bg.set(this.x,this.y,x+width,y+height);
        if(!collisionRectsDone){
            scrollDownRect = new Rect(bg);
            collisionRectsDone= true;}
        scrolledOut = false;


    }

    public void scrollUp(){   //scrolls the roomScroll scrollbar up by changing its measurements
        width = (int)(canvasWidth*0.8);
        height = canvasHeight/6;
        this.x = (canvasWidth/2)- (width / 2);
        this.y = canvasHeight - height;
        Log.d("scroll y" , "scroll y: "+this.y);
        bg.set(this.x, this.y, this.x + width, this.y + height);

        scrolledOut= true;
    }

    @Override
    public Rect getRectangle(){
        return scrollDownRect;
    }

    public void scroll()
    {

        long startTime;
        long timeMillis;
        long waitTime = 3;
        startTime = System.nanoTime();
        while(scrolledOut){

            timeMillis = (System.nanoTime()-startTime)/1000000000;


            if(timeMillis >= waitTime){
                scrollDown();




            }}}





}*/