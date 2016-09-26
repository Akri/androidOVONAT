package de.akricorp.ovonat.repository.MiniGames.StoneScissorPaper;

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
public class StoneScissorPaperGame extends Object {

    private BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();

    private int ovoWinCount=0;
    private int playerWinCount=0;
    private int drawCount=0;
    ScoreBoard scoreBoard;
    public boolean gameWon = false;
    public boolean gameLost = false;




    public ArrayList<GameObject> stoneScissorPaperObjects = new ArrayList<>();

    private static final int STONE = 0;
    private static final int SCISSOR = 1;
    private static final int PAPER = 2;

    Random r = new Random();

    public StoneScissorPaperGame(Context context, float resolutionControlFactorX, float resolutionControlFactorY){
        bitmapFactoryOptions.inScaled = false;
        Bitmap[] paperRes = new Bitmap[1];
        paperRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.paper,bitmapFactoryOptions);
        stoneScissorPaperObjects.add(new GameObject(paperRes,   350, 300,100,  100,  resolutionControlFactorX,resolutionControlFactorY,1));
        Bitmap[] stoneRes = new Bitmap[1];
        stoneRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.stone,bitmapFactoryOptions);
        stoneScissorPaperObjects.add(new GameObject(stoneRes,  150, 300,100 , 100, resolutionControlFactorX, resolutionControlFactorY,1));
        Bitmap[] scissorRes = new Bitmap[1];
        scissorRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scissor,bitmapFactoryOptions);
        stoneScissorPaperObjects.add(new GameObject(scissorRes,550, 300, 100,  100,  resolutionControlFactorX,resolutionControlFactorY,1));
        scoreBoard = new ScoreBoard(context,250,80,resolutionControlFactorX,resolutionControlFactorY);
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
        int ovoChoice = r.nextInt(3);
        return ovoChoice;
    }

    private void ovoWinUpdate() {
        ovoWinCount++;
        scoreBoard.updateScore(playerWinCount, ovoWinCount);
        checkForFinish();
    }

    private void updatePlayerCount() {
        playerWinCount++;
        scoreBoard.updateScore(playerWinCount, ovoWinCount);
        checkForFinish();
    }

    private void updateDrawCount() {
        drawCount++;

    }

   private void checkForFinish(){
       if(ovoWinCount == 20){
           gameLost = true;

       }
       if(playerWinCount == 20){gameWon = true;
   }}

    public void draw(Canvas canvas){
        if(!gameWon && !gameLost){
        for(int i = 0; i < stoneScissorPaperObjects.size();i++){
            stoneScissorPaperObjects.get(i).draw(canvas);

        }
         scoreBoard.draw(canvas);

    }

    }

}