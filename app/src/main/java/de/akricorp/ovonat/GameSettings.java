package de.akricorp.ovonat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Hannes on 27.07.2015.
 */
public class GameSettings {

    public int GAME_WIDTH = 856;
    public int GAME_HEIGHT = 480;
    public int bodyStyleCount = 3;


    Context context;
    BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();

    public GameSettings(Context context){
        this.context = context;
        bitmapFactoryOptions.inScaled = false;
    }


    //body
    public Bitmap[] getPlayer(int bodyType) {
        if (bodyType == 1) {
            Bitmap[] body1Res = new Bitmap[2];
            body1Res[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovo3, bitmapFactoryOptions);
            body1Res[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyes3, bitmapFactoryOptions);
            return body1Res;
        }
        if (bodyType == 2) {
            Bitmap[] body2Res = new Bitmap[3];
            body2Res[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovo3, bitmapFactoryOptions);
            body2Res[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyes3, bitmapFactoryOptions);
            body2Res[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyepatch, bitmapFactoryOptions);
            return body2Res;
        }

        if (bodyType == 3) {
            Bitmap[] body3Res = new Bitmap[3];
            body3Res[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovo3, bitmapFactoryOptions);
            body3Res[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyes3, bitmapFactoryOptions);
            body3Res[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.glasses, bitmapFactoryOptions);
            return body3Res;
        }
        else{return null;}


    }
}