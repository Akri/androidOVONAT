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
import de.akricorp.ovonat.actionObjects.RoomScroll;

import de.akricorp.ovonat.repository.DataRepository;
import de.akricorp.ovonat.repository.MiniGames.StoneScissorPaper.ShowerGame;
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
    private int currentRoom;
    int screenHeight;
    private Room room;
    private RoomScroll roomScroll;
    private GameObject player;
    private ArrayList<GameObject> playRoomObjects = new ArrayList<>();
    private ArrayList<GameObject> kitchenObjects = new ArrayList<>();
    private ArrayList<GameObject> bathRoomObjects = new ArrayList<>();
    private ArrayList<GameObject> outsideObjects = new ArrayList<>();
    private GameObject stoneScissorPaperObject;
    private GameObject showerGameObject;
    private GameObject fridgeObject;
    private float resolutionControlFactorX;
    private float resolutionControlFactorY;
    StatusBar funBar;
    StatusBar hygeneBar;
    StatusBar saturationBar;
    DataRepository repository;
    Context context;
    ArrayList<StatusBar> statusBarArrayList = new ArrayList<>();
    boolean currentWaitingProcess = false;
    public enum GameState {OUTSIDE, KITCHEN, PLAYROOM, BATH, STONEPAPER, SHOWERGAME}
    ShowerGame showerGame;
    GameState state = GameState.PLAYROOM;
    private TimeStatusChanger timeStatusChanger;
    int barChangerTimer = 0;

    String firstStartTime;
    String lastCloseTime;
    int miniCount;
    int currentBoots;
    int currentHair;
    int currentBody;
    int foodSaturation;
    int hygiene;
    int fun;


    public GamePanel(Context context) {
        super(context);
        this.context = context;
        // add callback to surfaceholder to intercept events
        getHolder().addCallback(this);
        timeStatusChanger = new TimeStatusChanger();
        initDb(context);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events

        setFocusable(true);


    }

    private void initDb(Context context) {

        repository = new DataRepository(context);
        repository.open();
        repository.setCursor();

        this.firstStartTime = repository.getData("firstStartTime");
        this.lastCloseTime = repository.getData("lastCloseTime");
        this.miniCount = Integer.parseInt(repository.getData("miniCount"));
        this.currentBoots =Integer.parseInt(repository.getData("boots"));
        this.currentHair =Integer.parseInt(repository.getData("hair"));
        this.currentBody= Integer.parseInt(repository.getData("body"));
        this.foodSaturation=Integer.parseInt(repository.getData("foodSaturation"));
        this.hygiene=Integer.parseInt(repository.getData("hygiene"));
        this.fun=Integer.parseInt(repository.getData("fun"));



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
                saveDataToRepository();
                repository.close();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }




    }

    private void saveDataToRepository(){
        repository.putIntoDb("lastCloseTime", timeStatusChanger.getCurrentDate());
        repository.putIntoDb("miniCount",""+miniCount);
        repository.putIntoDb("boots",""+currentBoots );
        repository.putIntoDb("hair",""+currentHair);
        repository.putIntoDb("body",""+currentBody);
        repository.putIntoDb("foodSaturation",""+foodSaturation);
        repository.putIntoDb("hygiene",""+hygiene);
        repository.putIntoDb("fun",""+fun);

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
        timeStatusChanger.getDataFromRepository(firstStartTime, lastCloseTime);
        resolutionControlFactorX = screenWidth / (float) gameSettings.GAME_WIDTH;
        resolutionControlFactorY = screenHeight/ (float) gameSettings.GAME_HEIGHT;
        createStatusBars();
        initActionObjects();
        Bitmap[] playerRes = new Bitmap[2];
        playerRes[0]= BitmapFactory.decodeResource(getResources(), R.drawable.ovo3,bitmapFactoryOptions);
        playerRes[1] = BitmapFactory.decodeResource(getResources(), R.drawable.eyes3,bitmapFactoryOptions);

        room = new Room(BitmapFactory.decodeResource(getResources(), currentRoom,bitmapFactoryOptions), resolutionControlFactorX,resolutionControlFactorY);
        player =new GameObject( playerRes, 300, 150,  100, 100, resolutionControlFactorX, resolutionControlFactorY,3);


        roomScroll = new RoomScroll(screenWidth, screenHeight, resolutionControlFactorX,resolutionControlFactorY, BitmapFactory.decodeResource(getResources(), R.drawable.kitchenbutton,bitmapFactoryOptions), BitmapFactory.decodeResource(getResources(), R.drawable.playroombutton), BitmapFactory.decodeResource(getResources(), R.drawable.outsidebutton), BitmapFactory.decodeResource(getResources(), R.drawable.bathbutton));
        roomScroll.scrollUp();
        showerGameStarted();
        playRoomStart();

        //start gameloop




        thread.setRunning(true);
        thread.start();

    }

    private void playRoomStart() {



        state = GameState.PLAYROOM;
        player.setX(300); player.setY(150);
        player.show();


    }

    private void kitchenRoomStart(){

        state = GameState.KITCHEN;
        player.setX(600); player.setY(350);
        player.show();
    }

    private void bathRoomStart() {

        state = GameState.BATH;
        player.setX(50); player.setY(350);
        player.show();
    }

    private void outsideRoomStart() {

        state = GameState.OUTSIDE;
        player.setX(520); player.setY(280);
        player.show();
    }


    public void initActionObjects(){
        Bitmap[] showerGameObjectRes = new Bitmap[1];
        showerGameObjectRes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.stonescissorpaper,bitmapFactoryOptions);
        bathRoomObjects.add(showerGameObject = new GameObject(showerGameObjectRes,
                200, 200,  100,100, resolutionControlFactorX, resolutionControlFactorY,1));

        Bitmap[] fridgeObjectRes = new Bitmap[1];
        fridgeObjectRes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fridge,bitmapFactoryOptions);
        kitchenObjects.add(fridgeObject = new GameObject(fridgeObjectRes,
                 25,  90,  300, 345, resolutionControlFactorX, resolutionControlFactorY,1));

        Bitmap[] stoneScissorPaperObjectRes = new Bitmap[1];
        stoneScissorPaperObjectRes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.sspicon,bitmapFactoryOptions);
        playRoomObjects.add(stoneScissorPaperObject = new GameObject(stoneScissorPaperObjectRes,
                600, 220, 75, 100, resolutionControlFactorX, resolutionControlFactorY,1));

    }





    private void stonePaperStart() {
        state = GameState.STONEPAPER;
        stoneScissorPaperGame = new StoneScissorPaperGame(context, resolutionControlFactorX,resolutionControlFactorY);
        player.setX(400); player.setY(150);





    }

    private void showerGameStarted() {
        state = GameState.SHOWERGAME;
        showerGame = new ShowerGame(context, resolutionControlFactorX, resolutionControlFactorY, hygiene);
        player.hide();
    }

    private void createStatusBars() {
        hygeneBar = new StatusBar(resolutionControlFactorX,resolutionControlFactorY, hygiene, 1,50,50,(BitmapFactory.decodeResource(getResources(), R.drawable.hygienesymbol ,bitmapFactoryOptions)));
        statusBarArrayList.add(hygeneBar);
        funBar = new StatusBar(resolutionControlFactorX,resolutionControlFactorY, fun, 2,50,50,(BitmapFactory.decodeResource(getResources(), R.drawable.funsymbol ,bitmapFactoryOptions)));
        statusBarArrayList.add(funBar);
        saturationBar = new StatusBar(resolutionControlFactorX,resolutionControlFactorY, foodSaturation, 3,50,50,(BitmapFactory.decodeResource(getResources(), R.drawable.hungersymbol ,bitmapFactoryOptions)));
        statusBarArrayList.add(saturationBar);
    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {


        Rect click = new Rect((int) event.getX(), (int) event.getY(), (int) event.getX() + 2, (int) event.getY() + 2);


        if (roomScroll.up) {
            for (int i = 0; i < roomScroll.getRoomButtonRectangles().length; i++) {

                if (collision(click, roomScroll.getRoomButtonRectangles()[i])) {
                    if (i == 0) {

                        kitchenRoomStart();
                    }
                    if (i == 1) {
                        playRoomStart();
                    }
                    if (i == 2) {
                        outsideRoomStart();
                    }
                    if (i == 3) {
                        bathRoomStart();
                    }
                }
            }
        }
        if (collision(click, roomScroll.getRectangle())) {


            roomScroll.scroll();

        }
      if(state == GameState.PLAYROOM){
       if (collision(click, stoneScissorPaperObject.getRectangle())) {

            stonePaperStart();

        }}
        if(state == GameState.BATH){
       if (collision(click, showerGameObject.getRectangle())) {

            showerGameStarted();

        }}
        if(state == GameState.KITCHEN){
            if (collision(click, fridgeObject.getRectangle())) {

                statusBarArrayList.get(2).addValue(1);
                foodSaturation = statusBarArrayList.get(2).getValue();

            }}


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


        if(barChangerTimer <= 100){
            barChangerTimer++;
        }
        else{barChangerTimer =0;}
        if(barChangerTimer == 100 && state != GameState.SHOWERGAME&& state != GameState.STONEPAPER){
            for(int i =0; i < statusBarArrayList.size();i++){
            statusBarArrayList.get(i).addValue(-1);
            if(i == 0){hygiene = statusBarArrayList.get(0).getValue();}
                if(i == 1){hygiene = statusBarArrayList.get(1).getValue();}
                if(i == 2){foodSaturation = statusBarArrayList.get(2).getValue();}

        }}


        if (player.getPlaying()) {
            room.update(BitmapFactory.decodeResource(getResources(), currentRoom,bitmapFactoryOptions));
            player.update();
        }
        if(state == GameState.STONEPAPER && stoneScissorPaperGame!= null){

            if((stoneScissorPaperGame.gameWon || stoneScissorPaperGame.gameLost)&& !currentWaitingProcess){


                statusBarArrayList.get(1).addValue(5);
                fun = statusBarArrayList.get(1).getValue();
                currentWaitingProcess = true;
                thread.sleep(5000);
                currentWaitingProcess = false;
                playRoomStart();
                stoneScissorPaperGame = null;


            }

        }
        if(state == GameState.SHOWERGAME && showerGame!= null) {

            statusBarArrayList.get(0).setValue(hygiene);
            statusBarArrayList.get(0).update();
            hygiene = showerGame.update();





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
                showerGame = null;
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
                showerGame = null;
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

                showerGame = null;
                currentRoom = R.drawable.stonepaperroom;
                break;


            case SHOWERGAME:
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
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.bathroom;
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
                showerGame = null;
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.bathroom;
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
                showerGame = null;
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.backyard;
                break;

        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        setCurrentBackground();


        final float scaleFactorX = 1;
        final float scaleFactorY = 1;


        if (canvas != null) {
            final int savedState = canvas.save();

            //canvas.scale(scaleFactorX, scaleFactorY);


            room.draw(canvas);
            player.draw(canvas);



            drawStatusBars(canvas);


            switch (state) {
                case STONEPAPER:
                    stoneScissorPaperGame.draw(canvas);
                    break;

                case SHOWERGAME:
                    showerGame.draw(canvas);

                case KITCHEN:


                    for (GameObject object : kitchenObjects) {
                        object.draw(canvas);
                    }


                case PLAYROOM:
                    for (GameObject object : playRoomObjects) {
                        object.draw(canvas);
                    }


                    break;


                case BATH:

                    for (GameObject object : bathRoomObjects) {
                        object.draw(canvas);
                    }
                    break;


                case OUTSIDE:

                    for (GameObject object : outsideObjects) {
                        object.draw(canvas);

                    }
                    break;

            }
            roomScroll.draw(canvas);
            canvas.restoreToCount(savedState);
        }

    }

    private void drawStatusBars(Canvas canvas) {
        for (int i = 0; i < statusBarArrayList.size(); i++) {
            statusBarArrayList.get(i).draw(canvas);
        }


    }

}