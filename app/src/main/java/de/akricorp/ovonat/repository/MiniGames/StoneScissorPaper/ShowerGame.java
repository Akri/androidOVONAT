package de.akricorp.ovonat.repository.MiniGames.StoneScissorPaper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

import de.akricorp.ovonat.GameObject;
import de.akricorp.ovonat.Player;
import de.akricorp.ovonat.R;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Hännes on 27.09.2016.
 */

public class ShowerGame  {

    private Context context;
    private BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();

    ArrayList<GameObject> dropList1 = new ArrayList<>();
    ArrayList<GameObject> dropList2 = new ArrayList<>();
    float resolutionControlFactorX;
    float resolutionControlFactorY;
    private static final int SENSOR_DELAY_MICROS = 50 * 1000; // 50ms
    private SensorManager mSensorManager;
    private float[] mValuesMagnet      = new float[3];
    private float[] mValuesAccel       = new float[3];
    private float[] mValuesOrientation = new float[3];
    private Sensor mAccelerometer;
    float ovoMoveFactor;
    private Player playerWet;
    private Bitmap[] playerWetRes = new Bitmap[3];


    private Bitmap[] dropRes = new Bitmap[1];
    boolean dropList2Ready = true;
    boolean dropList1Ready = false;
    boolean dropList2Fall = false;
    boolean dropList1Fall = true;
    int timer = 0;
    int timerEnd = 280;
    public int hygiene;

    Random r = new Random();


    private float[] mRotationMatrix    = new float[9];

    public ShowerGame(Context context, float resolutionControlFactorX, float resolutionControlFactorY, int currentHygiene) {
        this.context = context;
        hygiene = currentHygiene;
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY = resolutionControlFactorY;
        bitmapFactoryOptions.inScaled = false;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        playerWetRes[0]=BitmapFactory.decodeResource(context.getResources(), R.drawable.ovo3,bitmapFactoryOptions);
        playerWetRes[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyes3,bitmapFactoryOptions);
        playerWetRes[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovo3wetlayer,bitmapFactoryOptions);

        playerWet = new Player( playerWetRes, (100), (100), 3, 50, 350, resolutionControlFactorX, resolutionControlFactorY);

        mSensorManager.registerListener(mEventListener,mAccelerometer ,mSensorManager.SENSOR_DELAY_NORMAL);
        fillDropList(dropList1);
    }


    public void ovoController() {


        ovoMoveFactor = mValuesAccel [1];
        if(playerWet.getX() < 750*resolutionControlFactorY ){
            if(ovoMoveFactor > 0){
            Log.d("MoveFactor", "+1");
            playerWet.updateX((int)(10*ovoMoveFactor));
        }}
        if(playerWet.getX() > 0 ){
            if(ovoMoveFactor < 0){
                Log.d("MoveFactor", "-1");
                playerWet.updateX((int)(10*ovoMoveFactor));
            }}}



    public void fillDropList(ArrayList<GameObject> list){
        for(int i = 0; i < 3; i++){
            int xPosition = r.nextInt((int)(750));
            dropRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.drop,bitmapFactoryOptions);

        list.add(new GameObject(dropRes,xPosition,  (int)(-100*resolutionControlFactorX),47, 80,   resolutionControlFactorX,resolutionControlFactorY,1));
        }
    }

    private void rainDropFall() {
        Log.d("drops", "timer :"+timer);
        if(dropList1.isEmpty()  && dropList2Ready){
            fillDropList(dropList1);
            dropList1Fall = true;
        }
        if(dropList1Fall) {
            Log.d("drop","1 wird geupdated");
            if (timer > 10) {
                if (dropList1.get(0).getY() < 420 * resolutionControlFactorY) {
                    dropList1.get(0).updateY(10);
                } else {
                    dropList1.get(0).hide();
                }
            }
            if (timer > 40) {
                if (dropList1.get(1).getY() < 420 * resolutionControlFactorY) {
                    dropList1.get(1).updateY(10);
                } else {
                    dropList1.get(1).hide();
                }
            }
            if (timer > 70) {
                if (dropList1.get(2).getY() < 420* resolutionControlFactorY) {
                    dropList1.get(2).updateY(10);
                }else{dropList2Ready = false;  }

            if (timer ==140) {

                dropList1Fall = false;
                dropList1.clear();
                dropList1Ready = true;
                Log.d("drops","1fertig");
            }
        }}
             Log.d("drops","list2 : " +dropList2+"  1rdy "+ dropList1Ready);

            if(dropList2.isEmpty()  && dropList1Ready){
                Log.d("drops","2 wird erstellt");
                dropList2Fall = true;
                fillDropList(dropList2);

            }
            if(dropList2Fall) {
                Log.d("drop", "2 wird geupdated");
                if (timer > 150) {
                    if (dropList2.get(0).getY() < 420 * resolutionControlFactorY) {
                        dropList2.get(0).updateY(10);
                    } else {
                        dropList2.get(0).hide();
                    }
                }
                if (timer > 180) {
                    if (dropList2.get(1).getY() < 420 * resolutionControlFactorY) {
                        dropList2.get(1).updateY(10);
                    } else {
                        dropList2.get(1).hide();
                    }
                }
                if (timer > 210) {
                    if (dropList2.get(2).getY() < 420 * resolutionControlFactorY) {
                        dropList2.get(2).updateY(10);
                    } else {
                        dropList1Ready = false;
                    }

                    if (timer == 280) {
                        dropList2Fall = false;
                        dropList2.clear();
                        dropList2Ready = true;

                    }
                }

            }

    }


    public void dropCollisionCheck(){
        if(dropList1Fall){
            for(int i = 0; i < dropList1.size();i++){
               if(Rect.intersects(dropList1.get(i).getRectangle(), playerWet.getRectangle())&&dropList1.get(i).getY()<(int)( 420*resolutionControlFactorY)){
                   if(hygiene < 10&& dropList1.get(i).isShown){hygiene++;}
                   dropList1.get(i).hide();
                }
            }
        }
        if(dropList2Fall){
            for(int i = 0; i < dropList2.size();i++){
                if(Rect.intersects(dropList2.get(i).getRectangle(), playerWet.getRectangle())&&dropList2.get(i).getY()<(int)( 420*resolutionControlFactorY)){
                    if(hygiene < 10&& dropList2.get(i).isShown){hygiene++;}
                    dropList2.get(i).hide();
                }
            }
        }


    }


    public int update(){
        if(timer < timerEnd){timer++;}
        else{timer = 0;}

        ovoController();
        rainDropFall();
        dropCollisionCheck();
        playerWet.update();
        return hygiene;
    }




    public void draw(Canvas canvas) {
        playerWet.draw(canvas);
        if(dropList2Ready){
        for(int i = 0;  i < dropList1.size(); i++){
            dropList1.get(i).draw(canvas);
        }}
        if(dropList1Ready){
            for(int i = 0;  i < dropList2.size(); i++){
                dropList2.get(i).draw(canvas);
            }}


    }

    private final SensorEventListener mEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            // Handle the events for which we registered
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    System.arraycopy(event.values, 0, mValuesAccel, 0, 3);
                    break;

                case Sensor.TYPE_MAGNETIC_FIELD:
                    System.arraycopy(event.values, 0, mValuesMagnet, 0, 3);
                    break;
            }
        }

};
}


