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


    Context context;
    BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();

    public GameSettings(Context context){
        this.context = context;
        bitmapFactoryOptions.inScaled = false;
    }


    //body
    public Bitmap[] getPlayer(int bodyType) {
        if (bodyType == 1) {
            Bitmap[] playerRes = new Bitmap[2];
            playerRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovo3, bitmapFactoryOptions);
            playerRes[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyes3, bitmapFactoryOptions);
            return playerRes;
        }
        if (bodyType == 2) {
            Bitmap[] playerRes = new Bitmap[3];
            playerRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovo3, bitmapFactoryOptions);
            playerRes[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyes3, bitmapFactoryOptions);
            playerRes[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyepatch, bitmapFactoryOptions);
            return playerRes;
        }

        if (bodyType == 3) {
            Bitmap[] playerRes = new Bitmap[3];
            playerRes[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovo3, bitmapFactoryOptions);
            playerRes[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyes3, bitmapFactoryOptions);
            playerRes[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.glasses, bitmapFactoryOptions);
            return playerRes;
        }
        else{return null;}


    }
}