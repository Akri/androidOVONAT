package de.akricorp.ovonat.repository.MiniGames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import de.akricorp.ovonat.GameObject;
import de.akricorp.ovonat.R;


/**
 * Created by Hannes on 25.08.2015.
 */
public class StoneScissorPaper extends Object {

    private BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();

    int ovoWinCount=0;
    int playerWinCount=0;
    int drawCount=0;
    private GameObject stone;
    private GameObject scissor;
    private GameObject paper;


    public ArrayList<GameObject> stoneScissorPaperObjects = new ArrayList<>();

    private static final int STONE = 0;
    private static final int SCISSOR = 1;
    private static final int PAPER = 2;

    Random r = new Random();

    public StoneScissorPaper(Context context, float resolutionControlFactorX,float resolutionControlFactorY){
        bitmapFactoryOptions.inScaled = false;
        Bitmap[] paperRes = new Bitmap[1];
        paperRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.paper,bitmapFactoryOptions);
        stoneScissorPaperObjects.add(paper = new GameObject(paperRes,  100,  100,  350, 300, resolutionControlFactorX,resolutionControlFactorY,1));
        Bitmap[] stoneRes = new Bitmap[1];
        stoneRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.stone,bitmapFactoryOptions);
        stoneScissorPaperObjects.add(stone = new GameObject(stoneRes, 100 , 100, 150, 300, resolutionControlFactorX, resolutionControlFactorY,1));
        Bitmap[] scissorRes = new Bitmap[1];
        scissorRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scissor,bitmapFactoryOptions);
        stoneScissorPaperObjects.add(scissor = new GameObject(scissorRes, 100,  100, 550, 300, resolutionControlFactorX,resolutionControlFactorY,1));

    }





    public void stoneUsed(){
        Log.d("SSP","Stone used");
        int ovonatsChoice = ovoRandom();

        if(ovonatsChoice==STONE){
            updateDrawCount();
        }
        if(ovonatsChoice==SCISSOR){
            updatePlayerCount();
        }
        if(ovonatsChoice== PAPER){
            ovoWinUpdate();
        }
    }

    public void scissorUsed(){
        int ovonatsChoice = ovoRandom();

        if(ovonatsChoice==STONE){
            ovoWinUpdate();
        }
        if(ovonatsChoice==SCISSOR){
            updateDrawCount();
        }
        if(ovonatsChoice== PAPER){
            updatePlayerCount();
        }
    }

    public void paperUsed(){

        int ovonatsChoice = ovoRandom();

        if(ovonatsChoice==STONE){
            updatePlayerCount();
        }
        if(ovonatsChoice==SCISSOR){
            ovoWinUpdate();
        }
        if(ovonatsChoice== PAPER){
            updateDrawCount();
        }
    }

    private int ovoRandom() {
        int ovoChoice = r.nextInt(2);
        return ovoChoice;
    }

    private void ovoWinUpdate() {
        ovoWinCount++;
    }

    private void updatePlayerCount() {
        playerWinCount++;
    }

    private void updateDrawCount() {
        drawCount++;
    }



    public void draw(Canvas canvas){

        for(int i = 0; i < stoneScissorPaperObjects.size();i++){
            stoneScissorPaperObjects.get(i).draw(canvas);
        }

    }

}