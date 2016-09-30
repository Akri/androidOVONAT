package de.akricorp.ovonat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameObject {
    protected int originalX;
    protected int originalY;
    public int x;
    public int y;
    public int numFrames;
    protected int originalWidth;
    protected int originalHeight;
    protected int width;
    protected int height;
    private float resolutionControlFactorX;
    private float resolutionControlFactorY;
    private Animation[] animation;
    boolean playing = true;
    Bitmap[] res;
    Bitmap[][] resParts;


    public boolean isShown;

    public GameObject(Bitmap[] res, int positionX, int positionY, int w, int h, float resolutionControlFactorX, float resolutionControlFactorY, int numFrames) {
        this.numFrames = numFrames;
        originalHeight = h;
        originalWidth = w;
        this.resolutionControlFactorX = resolutionControlFactorX;
        this.resolutionControlFactorY = resolutionControlFactorY;
        height = (int) (h * resolutionControlFactorY);
        width = (int) (w * resolutionControlFactorX);
        this.res = new Bitmap[res.length];
        this.originalX = positionX;
        this.originalY = positionY;
        standartScaling(resolutionControlFactorX, resolutionControlFactorY);
        isShown = true;
        setupBitmapAnimation(res);


    }

    public void setupBitmapAnimation(Bitmap[] newRes) {
        this.res = newRes;
        animation = new Animation[res.length];
        resParts = new Bitmap[res.length][numFrames];

        for (int j = 0; j < res.length; j++) {
            this.res[j] = res[j];
            for (int i = 0; i < numFrames; i++) {
                resParts[j][i] = Bitmap.createBitmap(res[j], i * originalWidth, 0, originalWidth, originalHeight);
            }
            for (int y = 0; y < res.length; y++) {
                animation[y] = new Animation();
                animation[y].setFrames(resParts[y]);
                animation[y].setDelay(150);
            }
        }
    }

    public void setX(int newX) {
        x = (int) (newX * resolutionControlFactorX);
    }

    public void setY(int newY) {
        y = (int) (newY * resolutionControlFactorY);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void updateX(int add) {
        x = x + (int) (add * resolutionControlFactorX);
    }

    public void updateY(int add) {
        y = y + (int) (add * resolutionControlFactorY);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }


    public void standartScaling(float resolutionControlFactorX, float resolutionControlFactorY) {
        x = (int) ((float) originalX * resolutionControlFactorX);
        y = (int) ((float) originalY * resolutionControlFactorY);

    }


    public void hide() {
        isShown = false;
    }

    public void show() {
        isShown = true;
    }

    public void update() {
        for (int i = 0; i < res.length; i++) {
            animation[i].update();
        }
    }

    public void draw(Canvas canvas) {
        if (isShown) {


            for (int i = 0; i < res.length; i++) {
                canvas.drawBitmap(Bitmap.createScaledBitmap(animation[i].getImage(), width, height, false), x, y, null);

            }
        }
    }

    public boolean getPlaying() {
        return playing;
    }


}
