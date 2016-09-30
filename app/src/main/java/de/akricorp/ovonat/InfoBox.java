package de.akricorp.ovonat;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class InfoBox {

    private float resolutionControlFactorX;
    private float resolutionControlFactorY;
    BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
    private int boxWidth = 200;
    private int boxHeight = 100;
    private int screenWidth = 856;
    private int screenHeight = 480;
    Bitmap infoBox;
    Bitmap infoScreen;
    private int x = screenWidth - boxWidth;
    private int y = screenHeight - boxHeight;
    Paint textOptions = new Paint();
    String text1;
    String text2;
    String text3;
    String text4;
    public boolean screenShowed = false;
    public boolean boxShowed = true;


    public InfoBox(Context context, float resolutionControlFactorX, float resolutionControlFactorY, String lifeTimeRecord, String currentLifeTime) {


        bitmapFactoryOptions.inScaled = false;
        this.boxWidth = (int) (this.boxWidth * resolutionControlFactorX);
        this.boxHeight = (int) (this.boxHeight * resolutionControlFactorY);
        this.screenWidth = (int) (this.screenWidth * resolutionControlFactorX);
        this.screenHeight = (int) (this.screenHeight * resolutionControlFactorY);
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY = resolutionControlFactorY;
        infoBox = BitmapFactory.decodeResource(context.getResources(), R.drawable.infoboxbackground, bitmapFactoryOptions);
        infoBox = Bitmap.createScaledBitmap(infoBox, boxWidth, boxHeight, false);
        infoScreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.infoscreen, bitmapFactoryOptions);
        infoScreen = Bitmap.createScaledBitmap(infoScreen, screenWidth, screenHeight, false);
        textOptions.setColor(Color.BLACK);
        textOptions.setTextSize(18 * resolutionControlFactorX);
        text1 = "Dein Rekord: ";
        setLifeTimeRecord(lifeTimeRecord);
        text3 = "Aktuell:";
        setCurrentLifeTime(currentLifeTime);
        this.x = (int) (this.x * resolutionControlFactorX);
        this.y = (int) (this.y * resolutionControlFactorY);

    }

    public void setLifeTimeRecord(String record) {
        text2 = record;

    }

    public void setCurrentLifeTime(String time) {
        text4 = time;
    }

    public void showScreen() {
        screenShowed = true;
    }

    public void hideScreen() {
        screenShowed = false;
    }

    public void showBox() {
        boxShowed = true;
    }

    public void hideBox() {
        boxShowed = false;
    }


    public void setX(int newX) {
        x = (int) (newX * resolutionControlFactorX);
    }

    public void setY(int newY) {
        x = (int) (newY * resolutionControlFactorY);
    }

    public Rect getRectangle() {
        return new Rect(x, y, x + boxWidth, y + boxHeight);
    }

    public void draw(Canvas canvas) {


        if (screenShowed) {

            canvas.drawBitmap(infoScreen, 0, 0, null);
        }
        if (boxShowed) {
            canvas.drawBitmap(infoBox, x, y, null);
            canvas.drawText(text1, x + (int) (90 * resolutionControlFactorX), y + (int) (30 * resolutionControlFactorY), textOptions);
            canvas.drawText(text2, x + (int) (90 * resolutionControlFactorX), y + (int) (45 * resolutionControlFactorY), textOptions);
            canvas.drawText(text3, x + (int) (90 * resolutionControlFactorX), y + (int) (65 * resolutionControlFactorY), textOptions);
            canvas.drawText(text4, x + (int) (90 * resolutionControlFactorX), y + (int) (80 * resolutionControlFactorY), textOptions);
        }

    }
}
