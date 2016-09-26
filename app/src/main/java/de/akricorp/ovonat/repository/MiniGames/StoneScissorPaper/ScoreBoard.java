package de.akricorp.ovonat.repository.MiniGames.StoneScissorPaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import de.akricorp.ovonat.GameSettings;
import de.akricorp.ovonat.R;

/**
 * Created by Hännes on 26.09.2016.
 */

public class ScoreBoard {
    private BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();

    GameSettings gameSettings = new GameSettings();
    Bitmap backGround;
    int width;
    int height;
    int x ;
    int y;
    int team1Score = 0;
    int team2Score = 0;
    Paint scoreTextOptions = new Paint();
    Paint headerTextOptions = new Paint();
    float resolutionControlFactorX;
    float resolutionControlFactorY;




    public ScoreBoard(Context context, int w, int h, float resolutionControlFactorX, float resolutionControlFactorY){
        bitmapFactoryOptions.inScaled = false;
        this.width = (int)(w*resolutionControlFactorX);
        this.height = (int)(h*resolutionControlFactorY);
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY= resolutionControlFactorY;
        backGround =BitmapFactory.decodeResource(context.getResources(), R.drawable.scoreboardbg,bitmapFactoryOptions);
        backGround =Bitmap.createScaledBitmap(backGround,width,height,false);

        x = (int)(gameSettings.GAME_WIDTH*resolutionControlFactorX/2)-backGround.getWidth()/2;
        y = (int)(gameSettings.GAME_HEIGHT*resolutionControlFactorY/8);

        scoreTextOptions.setColor(Color.BLACK);
        scoreTextOptions.setTextSize(20*resolutionControlFactorX);
        headerTextOptions.setColor(Color.YELLOW);
        headerTextOptions.setTextSize(25*resolutionControlFactorX);

    }

    public void updateScore(int team1, int team2){
        team1Score = team1;
        team2Score = team2;
    }











    public void draw(Canvas canvas){
        canvas.drawBitmap(backGround,x,y,null);
        canvas.drawText("Winner with 20 Points ", x+(int)(5*resolutionControlFactorX), y+(int)(25*resolutionControlFactorY), headerTextOptions);
        canvas.drawText("You: "+team1Score, x+(int)(20*resolutionControlFactorX), y+(int)(60*resolutionControlFactorY), scoreTextOptions);
        canvas.drawText("Ovo: "+team2Score, x+(int)(170*resolutionControlFactorX), y+(int)(60*resolutionControlFactorY), scoreTextOptions);
    }
}
