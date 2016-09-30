package de.akricorp.ovonat.repository.Games.StonePaperScissor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


import java.util.ArrayList;
import java.util.Random;

import de.akricorp.ovonat.GameObject;
import de.akricorp.ovonat.GameSettings;
import de.akricorp.ovonat.R;


public class StoneScissorPaperGame extends Object {

    private BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();

    private int ovoWinCount = 0;
    private int playerWinCount = 0;
    private int drawCount = 0;
    ScoreBoard scoreBoard;
    public boolean gameWon = false;
    public boolean gameLost = false;
    Bitmap winScreen;
    Bitmap loseScreen;
    Bitmap ovoScissor;
    Bitmap ovoStone;
    Bitmap ovoPaper;
    GameSettings gameSettings;

    public enum OvosChoice {OVOSTONE, OVOPAPER, OVOSCISSOR, OVONOTHING}

    float resolutionControlFactorX;
    float resolutionControlFactorY;
    OvosChoice ovosChoice = OvosChoice.OVONOTHING;


    public ArrayList<GameObject> stoneScissorPaperObjects = new ArrayList<>();

    private static final int STONE = 0;
    private static final int SCISSOR = 1;
    private static final int PAPER = 2;

    Random r = new Random();

    public StoneScissorPaperGame(Context context, float resolutionControlFactorX, float resolutionControlFactorY) {
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY = resolutionControlFactorY;
        gameSettings = new GameSettings(context);

        bitmapFactoryOptions.inScaled = false;
        winScreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.winnerscreen, bitmapFactoryOptions);
        winScreen = Bitmap.createScaledBitmap(winScreen, (int) (gameSettings.GAME_WIDTH * resolutionControlFactorX), (int) (gameSettings.GAME_HEIGHT * resolutionControlFactorY), false);
        loseScreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.loserscreen, bitmapFactoryOptions);
        loseScreen = Bitmap.createScaledBitmap(loseScreen, (int) (gameSettings.GAME_WIDTH * resolutionControlFactorX), (int) (gameSettings.GAME_HEIGHT * resolutionControlFactorY), false);

        ovoStone = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock, bitmapFactoryOptions);
        ovoStone = Bitmap.createScaledBitmap(ovoStone, (int) (ovoStone.getWidth() * resolutionControlFactorX * 0.6), (int) (ovoStone.getHeight() * resolutionControlFactorY * 0.6), false);

        ovoScissor = BitmapFactory.decodeResource(context.getResources(), R.drawable.scissors, bitmapFactoryOptions);
        ovoScissor = Bitmap.createScaledBitmap(ovoScissor, (int) (ovoScissor.getWidth() * resolutionControlFactorX * 0.6), (int) (ovoScissor.getHeight() * resolutionControlFactorY * 0.6), false);

        ovoPaper = BitmapFactory.decodeResource(context.getResources(), R.drawable.paper, bitmapFactoryOptions);
        ovoPaper = Bitmap.createScaledBitmap(ovoPaper, (int) (ovoPaper.getWidth() * resolutionControlFactorX * 0.6), (int) (ovoPaper.getHeight() * resolutionControlFactorY * 0.6), false);

        Bitmap[] paperRes = new Bitmap[1];
        paperRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.paperplayer, bitmapFactoryOptions);
        stoneScissorPaperObjects.add(new GameObject(paperRes, 350, 300, 80, 80, resolutionControlFactorX, resolutionControlFactorY, 1));
        Bitmap[] stoneRes = new Bitmap[1];
        stoneRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rockplayer, bitmapFactoryOptions);
        stoneScissorPaperObjects.add(new GameObject(stoneRes, 150, 300, 80, 80, resolutionControlFactorX, resolutionControlFactorY, 1));
        Bitmap[] scissorRes = new Bitmap[1];
        scissorRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scissorsplayer, bitmapFactoryOptions);
        stoneScissorPaperObjects.add(new GameObject(scissorRes, 550, 300, 80, 80, resolutionControlFactorX, resolutionControlFactorY, 1));
        scoreBoard = new ScoreBoard(context, 250, 80, resolutionControlFactorX, resolutionControlFactorY);
    }


    public void stoneUsed() {

        int ovonatsChoice = ovoRandom();

        if (ovonatsChoice == STONE) {
            ovosChoice = OvosChoice.OVOSTONE;
            updateDrawCount();
        }
        if (ovonatsChoice == SCISSOR) {
            ovosChoice = OvosChoice.OVOSCISSOR;
            updatePlayerCount();
        }
        if (ovonatsChoice == PAPER) {
            ovosChoice = OvosChoice.OVOPAPER;
            ovoWinUpdate();
        }
    }

    public void scissorUsed() {
        int ovonatsChoice = ovoRandom();

        if (ovonatsChoice == STONE) {
            ovosChoice = OvosChoice.OVOSTONE;
            ovoWinUpdate();
        }
        if (ovonatsChoice == SCISSOR) {
            ovosChoice = OvosChoice.OVOSCISSOR;
            updateDrawCount();
        }
        if (ovonatsChoice == PAPER) {
            ovosChoice = OvosChoice.OVOPAPER;
            updatePlayerCount();
        }
    }

    public void paperUsed() {

        int ovonatsChoice = ovoRandom();

        if (ovonatsChoice == STONE) {
            ovosChoice = OvosChoice.OVOSTONE;
            updatePlayerCount();
        }
        if (ovonatsChoice == SCISSOR) {
            ovosChoice = OvosChoice.OVOSCISSOR;
            ovoWinUpdate();
        }
        if (ovonatsChoice == PAPER) {
            ovosChoice = OvosChoice.OVOPAPER;
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

    private void checkForFinish() {
        if (ovoWinCount == 20) {
            gameLost = true;

        }
        ;
        if (playerWinCount == 20) {
            gameWon = true;
        }
    }

    public void draw(Canvas canvas) {


        switch (ovosChoice) {
            case OVOPAPER:
                canvas.drawBitmap(ovoPaper, 380 * resolutionControlFactorX, 160 * resolutionControlFactorY, null);
                break;
            case OVOSCISSOR:
                canvas.drawBitmap(ovoScissor, 380 * resolutionControlFactorX, 160 * resolutionControlFactorY, null);
                break;
            case OVOSTONE:
                canvas.drawBitmap(ovoStone, 380 * resolutionControlFactorX, 160 * resolutionControlFactorY, null);
                break;

        }

        if (!gameWon && !gameLost) {
            for (int i = 0; i < stoneScissorPaperObjects.size(); i++) {
                stoneScissorPaperObjects.get(i).draw(canvas);

            }
            scoreBoard.draw(canvas);

        }
        if (gameWon) {
            canvas.drawBitmap(winScreen, 0, 0, null);
        }
        if (gameLost) {
            canvas.drawBitmap(loseScreen, 0, 0, null);
        }

    }

}