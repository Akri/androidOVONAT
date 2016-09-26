package de.akricorp.ovonat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.ArrayList;


//import de.akricorp.ovonat.actionObjects.RoomScroll;
import de.akricorp.ovonat.actionObjects.Playroom.StoneScissorPaperObject;
import de.akricorp.ovonat.actionObjects.RoomScroll;

import de.akricorp.ovonat.repository.DataRepository;
import de.akricorp.ovonat.repository.MiniGames.StoneScissorPaper.StoneScissorPaperGame;

/**
 * Created by Hannes on 23.07.2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private StoneScissorPaperGame stoneScissorPaperGame;
    private BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
    private static GameSettings gameSettings = new GameSettings();
    private MainThread thread;
    public int height = gameSettings.GAME_HEIGHT;
    public int width = gameSettings.GAME_WIDTH;
    private int currentRoom = R.drawable.room2;
    int screenHeight;
    private Room room;
    private RoomScroll roomScroll;
    private Player player;
    private ArrayList<GameObject> playRoomObjects = new ArrayList<>();
    private ArrayList<GameObject> kitchenObjects = new ArrayList<>();
    private ArrayList<GameObject> bathRoomObjects = new ArrayList<>();
    private ArrayList<GameObject> outsideObjects = new ArrayList<>();
    private StoneScissorPaperObject stoneScissorPaperObject;
    private float resolutionControlFactorX;
    private float resolutionControlFactorY;
    StatusBar funBar;
    StatusBar hygeneBar;
    StatusBar saturationBar;
    DataRepository repository;
    Context context;
    ArrayList<StatusBar> statusBarArrayList = new ArrayList<>();
    boolean currentWaitingProcess = false;
    public enum GameState {OUTSIDE, KITCHEN, PLAYROOM, BATH, STONEPAPER}

    GameState state = GameState.PLAYROOM;


    public GamePanel(Context context) {
        super(context);
        this.context = context;
        // add callback to surfaceholder to intercept events
        getHolder().addCallback(this);
        initDb(context);
        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events

        setFocusable(true);


    }

    private void initDb(Context context) {

        repository = new DataRepository(context);
        repository.open();
        repository.firstSetup();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            try {
                counter++;
                thread.setRunning(false);
                thread.join();
                retry = false;
                repository.close();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        repository.deleteDb(context);    //nur zu testzwecken, unbeding wieder löschen
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double) width / (double) dens;
        double hi = (double) height / (double) dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);
        bitmapFactoryOptions.inScaled = false;

       Log.d("resolution", "x: "+screenWidth+"  y: "+screenHeight);

        resolutionControlFactorX = screenWidth / (float) gameSettings.GAME_WIDTH;
        resolutionControlFactorY = screenHeight/ (float) gameSettings.GAME_HEIGHT;
        createStatusBars();
        Bitmap[] playerRes = new Bitmap[2];
        playerRes[0]= BitmapFactory.decodeResource(getResources(), R.drawable.ovo3,bitmapFactoryOptions);
        playerRes[1] = BitmapFactory.decodeResource(getResources(), R.drawable.eyes3,bitmapFactoryOptions);

        room = new Room(BitmapFactory.decodeResource(getResources(), currentRoom,bitmapFactoryOptions), resolutionControlFactorX,resolutionControlFactorY);
        player =new Player( playerRes, (int)(100), (int)(100), 3, 300, 150, resolutionControlFactorX, resolutionControlFactorY);


        roomScroll = new RoomScroll(screenWidth, screenHeight, resolutionControlFactorX,resolutionControlFactorY, BitmapFactory.decodeResource(getResources(), R.drawable.kitchenbutton,bitmapFactoryOptions), BitmapFactory.decodeResource(getResources(), R.drawable.playroombutton), BitmapFactory.decodeResource(getResources(), R.drawable.outsidebutton), BitmapFactory.decodeResource(getResources(), R.drawable.bathbutton));
        roomScroll.scrollUp();

        playRoomStart();

        //start gameloop




        thread.setRunning(true);
        thread.start();

    }

    private void playRoomStart() {
        if(stoneScissorPaperObject == null){
            Bitmap[] stoneScissorPaperObjectRes = new Bitmap[1];
            stoneScissorPaperObjectRes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.stonescissorpaper,bitmapFactoryOptions);
            playRoomObjects.add(stoneScissorPaperObject = new StoneScissorPaperObject(stoneScissorPaperObjectRes,
                    (int) (100), (int) (100), 1, 200, 200, resolutionControlFactorX, resolutionControlFactorY));
        }
        state = GameState.PLAYROOM;


    }


    private void stonePaperStarted() {
        state = GameState.STONEPAPER;
        stoneScissorPaperGame = new StoneScissorPaperGame(context, resolutionControlFactorX,resolutionControlFactorY);
        player.setX(400); player.setY(150);





    }

    private void createStatusBars() {
        hygeneBar = new StatusBar(resolutionControlFactorX,resolutionControlFactorY, Integer.parseInt(repository.getData("hygene")), 1);
        statusBarArrayList.add(hygeneBar);
        funBar = new StatusBar(resolutionControlFactorX,resolutionControlFactorY, Integer.parseInt(repository.getData("fun")), 2);
        statusBarArrayList.add(funBar);
        saturationBar = new StatusBar(resolutionControlFactorX,resolutionControlFactorY, Integer.parseInt(repository.getData("foodSaturation")), 3);
        statusBarArrayList.add(saturationBar);
    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {


        Rect click = new Rect((int) event.getX(), (int) event.getY(), (int) event.getX() + 2, (int) event.getY() + 2);


        if (roomScroll.up) {
            for (int i = 0; i < roomScroll.getRoomButtonRectangles().length; i++) {

                if (collision(click, roomScroll.getRoomButtonRectangles()[i])) {
                    if (i == 0) {

                        state = GameState.KITCHEN;
                    }
                    if (i == 1) {
                        state = GameState.PLAYROOM;
                    }
                    if (i == 2) {
                        state = GameState.OUTSIDE;
                    }
                    if (i == 3) {
                        state = GameState.BATH;
                    }
                }
            }
        }
        if (collision(click, roomScroll.getRectangle())) {


            roomScroll.scroll();

        }

        if (collision(click, stoneScissorPaperObject.getRectangle())) {

            stonePaperStarted();

        }


        if(stoneScissorPaperGame !=null){
        for(int i = 0; i < stoneScissorPaperGame.stoneScissorPaperObjects.size(); i++){

            if (collision(click, stoneScissorPaperGame.stoneScissorPaperObjects.get(i).getRectangle())&& !stoneScissorPaperGame.gameWon && !stoneScissorPaperGame.gameLost) {
                Log.d("SSPText", i+"");
                switch (i){
                    case 0:
                        stoneScissorPaperGame.paperUsed();break;
                    case 1:
                        stoneScissorPaperGame.stoneUsed();break;
                    case 2:
                        stoneScissorPaperGame.scissorUsed();break;



                }

            }}}


        return super.onTouchEvent(event);
    }

    private boolean collision(Rect click, Rect rectangle) {
        if (Rect.intersects(click, rectangle)) {

            return true;
        } else return false;
    }


    public void update() throws InterruptedException {


        if (player.getPlaying()) {
            room.update(BitmapFactory.decodeResource(getResources(), currentRoom,bitmapFactoryOptions));
            player.update();
        }
        if(state == GameState.STONEPAPER && stoneScissorPaperGame!= null){
            Log.d("SSPTest", "in 1");
            if((stoneScissorPaperGame.gameWon || stoneScissorPaperGame.gameLost)&& !currentWaitingProcess){
                Log.d("SSPTest", "in 2");
                if(stoneScissorPaperGame.gameWon){
                    repository.putIntoDb("fun","10");
                    statusBarArrayList.get(1).setValue(Integer.parseInt(repository.getData("fun")));
                    statusBarArrayList.get(1).update(); }
                currentWaitingProcess = true;
                thread.sleep(3000);
                currentWaitingProcess = false;
                playRoomStart();
                stoneScissorPaperGame = null;


            }

        }




    }

    private void setCurrentBackground() {
        switch (state) {

            case KITCHEN:

                for(GameObject object : playRoomObjects){
                    object.hide();
                }
                for(GameObject object : kitchenObjects){
                    object.show();
                }
                for(GameObject object : bathRoomObjects){
                    object.hide();
                }
                for(GameObject object : outsideObjects){
                    object.hide();
                }
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.kitchen;
                break;
            case PLAYROOM:
                for(GameObject object : playRoomObjects){
                    object.show();
                }
                for(GameObject object : kitchenObjects){
                    object.hide();
                }
                for(GameObject object : bathRoomObjects){
                    object.hide();
                }
                for(GameObject object : outsideObjects){
                    object.hide();
                }
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.playroom;
                break;
            case STONEPAPER:
                for(GameObject object : playRoomObjects){
                    object.hide();
                }
                for(GameObject object : kitchenObjects){
                    object.hide();
                }
                for(GameObject object : bathRoomObjects){
                    object.hide();
                }
                for(GameObject object : outsideObjects){
                    object.hide();
                }

                currentRoom = R.drawable.stonepaperroom;
                break;
            case BATH:
                for(GameObject object : playRoomObjects){
                    object.hide();
                }
                for(GameObject object : kitchenObjects){
                    object.hide();
                }
                for(GameObject object : bathRoomObjects){
                    object.show();
                }
                for(GameObject object : outsideObjects){
                    object.hide();
                }
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.latest;
                break;
            case OUTSIDE:
                for(GameObject object : playRoomObjects){
                    object.hide();
                }
                for(GameObject object : kitchenObjects){
                    object.hide();
                }
                for(GameObject object : bathRoomObjects){
                    object.hide();
                }
                for(GameObject object : outsideObjects){
                    object.show();

                }
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.room2;
                break;

        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d("currentRoom", ""+currentRoom);
        setCurrentBackground();
        Log.d("currentRoom", ""+currentRoom);

        final float scaleFactorX = 1;
        final float scaleFactorY = 1;


        if (canvas != null) {
            final int savedState = canvas.save();

            //canvas.scale(scaleFactorX, scaleFactorY);


            room.draw(canvas);
            player.draw(canvas);
            stoneScissorPaperObject.draw(canvas);

            roomScroll.draw(canvas);
            drawStatusBars(canvas);
            if(stoneScissorPaperGame != null){
            stoneScissorPaperGame.draw(canvas);}


            canvas.restoreToCount(savedState);


        }
    }

    private void drawStatusBars(Canvas canvas) {
        for (int i = 0; i < statusBarArrayList.size(); i++) {
            statusBarArrayList.get(i).draw(canvas);
        }


    }

}