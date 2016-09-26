package de.akricorp.ovonat.actionObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import de.akricorp.ovonat.Animation;
import de.akricorp.ovonat.GameObject;

/**
 * Created by Hannes on 29.07.2015.
 */
public class Fridge extends GameObject {

    Bitmap spritesheed;
    private Animation animation = new Animation();

    public Fridge(Bitmap[] res, int w, int h, int numFrames,int canvasWidth, int canvasHeight,float resolutionControlfactorX, float resolutionControlfactorY)
    {super(res,canvasWidth,canvasHeight,w,h,resolutionControlfactorX, resolutionControlfactorY,numFrames);
        setX(20);
        setY(20);
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];


        for(int i = 0; i < image.length;i++)
        { spritesheed = res[1];
            image[i] = Bitmap.createBitmap(spritesheed, i*width,0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);

    }



    public void update()
    {
        animation.update();
    }
    public void draw(Canvas canvas)
    {  Log.d("fridgePosition", "x: " + x + ", y: " + y);
        canvas.drawBitmap(animation.getImage(), x, y, null);

    }

    @Override
    public void clickReaction(){//open fridge menu
    }


}

