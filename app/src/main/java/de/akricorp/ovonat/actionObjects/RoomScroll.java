package de.akricorp.ovonat.actionObjects;

import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


import java.util.ArrayList;

public class RoomScroll {


    public boolean up = false;

    private Rect bg;
    private boolean active = true;
    private Paint bgPaint = new Paint();
    Bitmap kitchenButton;
    Bitmap playRoomButton;
    Bitmap outsideButton;
    Bitmap bathButton;
    ArrayList<Bitmap> roomButtonArrayList = new ArrayList<Bitmap>();
    float resolutionControlFactorX;
    float resolutionControlFactorY;
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


    public RoomScroll(int canvasWidth, int canvasHeight, float resolutionControlFactorX, float resolutionControlFactorY, Bitmap kitchen, Bitmap playRoom, Bitmap outside, Bitmap bath) {
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY = resolutionControlFactorY;
        this.upWidth = canvasWidth / 2;
        this.upHeight = canvasHeight / 8;
        this.upX = (canvasWidth / 2) - (this.upWidth / 2);
        this.upY = (canvasHeight - upHeight);
        this.downWidth = canvasWidth / 4;
        this.downHeight = canvasHeight / 12;
        this.downX = (canvasWidth / 2) - (this.downWidth / 2);
        this.downY = (canvasHeight - downHeight);
        strokeWidth = (int) ((float) 5 * resolutionControlFactorX);
        bgPaint.setColor(Color.rgb(0, 0, 0));
        bgPaint.setStrokeWidth(strokeWidth);
        bgPaint.setStyle(Paint.Style.STROKE);

        bathButton = Bitmap.createScaledBitmap(bath, (int) (80 * resolutionControlFactorX), (int) (80 * resolutionControlFactorY), false);
        roomButtonArrayList.add(bathButton);
        playRoomButton = Bitmap.createScaledBitmap(playRoom, (int) (80 * resolutionControlFactorX), (int) (80 * resolutionControlFactorY), false);
        roomButtonArrayList.add(playRoomButton);
        outsideButton = Bitmap.createScaledBitmap(outside, (int) (80 * resolutionControlFactorX), (int) (80 * resolutionControlFactorY), false);
        roomButtonArrayList.add(outsideButton);
        kitchenButton = Bitmap.createScaledBitmap(kitchen, (int) (80 * resolutionControlFactorX), (int) (80 * resolutionControlFactorY), false);
        roomButtonArrayList.add(kitchenButton);
        scroll();


    }

    public void hide() {
        active = false;
    }

    public void show() {
        active = true;
    }


    public void draw(Canvas canvas) {
        if (active) {
            bg = new Rect(currentX - strokeWidth / 2, currentY - strokeWidth / 2, currentX + currentWidth + strokeWidth / 2, currentY + strokeWidth / 2 + currentHeight);

            canvas.drawRect(bg, bgPaint);
            if (up) {
                for (int i = 0; i < 4; i++) {
                    canvas.drawBitmap(roomButtonArrayList.get(i), upX + resolutionControlFactorX * 115 * i, upY, null);

                }
            }
        }
    }


    public void scroll() {

        if (!up) {
            currentX = upX;
            currentY = upY;
            currentHeight = upHeight;
            currentWidth = upWidth;
            up = true;
        } else {
            currentX = downX;
            currentY = downY;
            currentHeight = downHeight;
            currentWidth = downWidth;
            up = false;

        }

    }

    public void scrollUp() {
        up = true;
    }

    public void scrollDown() {
        up = false;
    }


    public Rect getRectangle() {

        scrollDownRect = new Rect(currentX, currentY, currentX + currentWidth, currentY + currentHeight);
        return scrollDownRect;
    }


    public Rect[] getRoomButtonRectangles() {
        Rect[] roomRectangles = new Rect[4];
        for (int i = 0; i < roomRectangles.length; i++) {

            roomRectangles[i] = new Rect((int) (upX + 115 * i * resolutionControlFactorX), currentY, (int) (upX + (115) * i * resolutionControlFactorX + 80 * resolutionControlFactorX), (int) ((upY + 80 * resolutionControlFactorY) * resolutionControlFactorY));
        }


        return roomRectangles;
    }


}