package de.akricorp.ovonat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import android.util.DisplayMetrics;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.ArrayList;


import de.akricorp.ovonat.actionObjects.RoomScroll;

import de.akricorp.ovonat.repository.DataRepository;
import de.akricorp.ovonat.repository.Games.showergame.ShowerGame;
import de.akricorp.ovonat.repository.Games.StonePaperScissor.StoneScissorPaperGame;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {


    private BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
    private static GameSettings gameSettings;
    private MainThread thread;
    private float resolutionControlFactorX;
    private float resolutionControlFactorY;
    private int screenWidth;
    private int screenHeight;
    private DataRepository repository;
    private Context context;
    private TimeStatusChanger timeStatusChanger;

    private enum GameState {OUTSIDE, KITCHEN, PLAYROOM, BATH, STONEPAPER, SHOWERGAME}

    private GameState state = GameState.PLAYROOM;

    private GameObject player;
    private Room room;
    private RoomScroll roomScroll;
    private InfoBox infobox;
    private Bitmap deadOvo;

    private ArrayList<GameObject> playRoomObjects = new ArrayList<>();
    private ArrayList<GameObject> kitchenObjects = new ArrayList<>();
    private ArrayList<GameObject> bathRoomObjects = new ArrayList<>();
    private ArrayList<GameObject> outsideObjects = new ArrayList<>();
    private GameObject stoneScissorPaperObject;

    private GameObject fridgeObject;
    private GameObject bathTubeObject;
    private GameObject hangerObject;

    private long deadCounter = 0;
    private int frapsPerStatusLose = 2000;
    private int currentRoom;
    private int barChangerTimer = 0;


    private ArrayList<StatusBar> statusBarArrayList = new ArrayList<>();
    private StatusBar funBar;
    private StatusBar hygeneBar;
    private StatusBar saturationBar;


    private boolean currentWaitingProcess = false;
    private boolean isDead = false;


    private ShowerGame showerGame;
    private StoneScissorPaperGame stoneScissorPaperGame;


    private String firstStartTime;
    private String lastCloseTime;
    private int miniCount;
    private int currentBoots;
    private int currentHair;
    private int currentBody;
    private int foodSaturation;
    private int hygiene;
    private int fun;
    private String lifeTimeRecord;
    private String currentLifeTime;


    public GamePanel(Context context) {
        super(context);
        this.context = context;
        gameSettings = new GameSettings(context);

        getHolder().addCallback(this);
        timeStatusChanger = new TimeStatusChanger();
        initDb(context);


        thread = new MainThread(getHolder(), this);


        setFocusable(true);


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


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        timeStatusChanger.getDataFromRepository(firstStartTime);
        hygiene -= timeStatusChanger.getChangeValue(lastCloseTime);
        foodSaturation -= timeStatusChanger.getChangeValue(lastCloseTime);
        fun -= timeStatusChanger.getChangeValue(lastCloseTime);


        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.screenWidth = dm.widthPixels;
        this.screenHeight = dm.heightPixels;
        bitmapFactoryOptions.inScaled = false;


        resolutionControlFactorX = screenWidth / (float) gameSettings.GAME_WIDTH;
        resolutionControlFactorY = screenHeight / (float) gameSettings.GAME_HEIGHT;
        createStatusBars();
        initActionObjects();

        infobox = new InfoBox(context, resolutionControlFactorX, resolutionControlFactorY, lifeTimeRecord, currentLifeTime);
        room = new Room(BitmapFactory.decodeResource(getResources(), currentRoom, bitmapFactoryOptions), resolutionControlFactorX, resolutionControlFactorY);
        player = new GameObject(gameSettings.getPlayer(currentBody), 300, 150, 100, 100, resolutionControlFactorX, resolutionControlFactorY, 3);
        deadOvo = BitmapFactory.decodeResource(context.getResources(), R.drawable.gravestone, bitmapFactoryOptions);
        deadOvo = Bitmap.createScaledBitmap(deadOvo, screenWidth, screenHeight, false);

        roomScroll = new RoomScroll(screenWidth, screenHeight, resolutionControlFactorX, resolutionControlFactorY, BitmapFactory.decodeResource(getResources(), R.drawable.kitchenbutton, bitmapFactoryOptions), BitmapFactory.decodeResource(getResources(), R.drawable.playroombutton), BitmapFactory.decodeResource(getResources(), R.drawable.outsidebutton), BitmapFactory.decodeResource(getResources(), R.drawable.bathbutton));
        roomScroll.scrollUp();
        showerGameStarted();
        playRoomStart();


        thread.setRunning(true);
        thread.start();

    }


    private void initDb(Context context) {

        repository = new DataRepository(context);
        repository.open();
        repository.setCursor();
        this.firstStartTime = repository.getData("firstStartTime");
        this.lastCloseTime = repository.getData("lastCloseTime");
        this.miniCount = Integer.parseInt(repository.getData("miniCount"));
        this.currentBoots = Integer.parseInt(repository.getData("boots"));
        this.currentHair = Integer.parseInt(repository.getData("hair"));
        this.currentBody = Integer.parseInt(repository.getData("body"));
        this.foodSaturation = Integer.parseInt(repository.getData("foodSaturation"));
        this.hygiene = Integer.parseInt(repository.getData("hygiene"));
        this.fun = Integer.parseInt(repository.getData("fun"));
        this.lifeTimeRecord = repository.getData("timeRecord");

    }

    private void saveDataToRepository() {
        repository.putIntoDb("lastCloseTime", timeStatusChanger.getCurrentDate());
        repository.putIntoDb("miniCount", "" + miniCount);
        repository.putIntoDb("boots", "" + currentBoots);
        repository.putIntoDb("hair", "" + currentHair);
        repository.putIntoDb("body", "" + currentBody);
        repository.putIntoDb("foodSaturation", "" + foodSaturation);
        repository.putIntoDb("hygiene", "" + hygiene);
        repository.putIntoDb("fun", "" + fun);
        repository.putIntoDb("timeRecord", lifeTimeRecord);
        repository.putIntoDb("firstStartTime", firstStartTime);

    }

    private void playRoomStart() {


        state = GameState.PLAYROOM;
        player.setX(300);
        player.setY(150);
        player.show();


    }

    private void kitchenRoomStart() {

        state = GameState.KITCHEN;
        player.setX(500);
        player.setY(350);
        player.show();
    }

    private void bathRoomStart() {

        state = GameState.BATH;
        player.setX(50);
        player.setY(350);
        player.show();
    }

    private void outsideRoomStart() {

        state = GameState.OUTSIDE;
        player.setX(520);
        player.setY(280);
        player.show();
    }

    private void showerGameStarted() {
        state = GameState.SHOWERGAME;
        showerGame = new ShowerGame(context, resolutionControlFactorX, resolutionControlFactorY, hygiene);
        player.hide();
    }

    private void stonePaperStart() {
        state = GameState.STONEPAPER;
        stoneScissorPaperGame = new StoneScissorPaperGame(context, resolutionControlFactorX, resolutionControlFactorY);
        player.setX(400);
        player.setY(150);
    }


    public void initActionObjects() {
        Bitmap[] fridgeObjectRes = new Bitmap[1];
        fridgeObjectRes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fridgeglow, bitmapFactoryOptions);
        kitchenObjects.add(fridgeObject = new GameObject(fridgeObjectRes,
                25, 90, 300, 345, resolutionControlFactorX, resolutionControlFactorY, 4));

        Bitmap[] stoneScissorPaperObjectRes = new Bitmap[1];
        stoneScissorPaperObjectRes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.sspglowicon, bitmapFactoryOptions);
        playRoomObjects.add(stoneScissorPaperObject = new GameObject(stoneScissorPaperObjectRes,
                580, 220, 100, 100, resolutionControlFactorX, resolutionControlFactorY, 4));

        Bitmap[] hangerObjectRes = new Bitmap[1];
        hangerObjectRes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hangerglowicon, bitmapFactoryOptions);
        playRoomObjects.add(hangerObject = new GameObject(hangerObjectRes,
                100, 80, 100, 50, resolutionControlFactorX, resolutionControlFactorY, 4));

        Bitmap[] showerObjectRes = new Bitmap[1];
        showerObjectRes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bathglowbutton, bitmapFactoryOptions);
        bathRoomObjects.add(bathTubeObject = new GameObject(showerObjectRes,
                370, 242, 400, 199, resolutionControlFactorX, resolutionControlFactorY, 4));

    }


    private void createStatusBars() {
        hygeneBar = new StatusBar(resolutionControlFactorX, resolutionControlFactorY, hygiene, 1, 50, 50, (BitmapFactory.decodeResource(getResources(), R.drawable.hygienesymbol, bitmapFactoryOptions)));
        statusBarArrayList.add(hygeneBar);
        funBar = new StatusBar(resolutionControlFactorX, resolutionControlFactorY, fun, 2, 50, 50, (BitmapFactory.decodeResource(getResources(), R.drawable.funsymbol, bitmapFactoryOptions)));
        statusBarArrayList.add(funBar);
        saturationBar = new StatusBar(resolutionControlFactorX, resolutionControlFactorY, foodSaturation, 3, 50, 50, (BitmapFactory.decodeResource(getResources(), R.drawable.hungersymbol, bitmapFactoryOptions)));
        statusBarArrayList.add(saturationBar);
    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {


        Rect click = new Rect((int) event.getX(), (int) event.getY(), (int) event.getX() + 2, (int) event.getY() + 2);


        if (roomScroll.up) {
            for (int i = 0; i < roomScroll.getRoomButtonRectangles().length; i++) {

                if (collision(click, roomScroll.getRoomButtonRectangles()[i])) {
                    if (i == 0) {

                        bathRoomStart();
                    }
                    if (i == 1) {
                        playRoomStart();
                    }
                    if (i == 2) {
                        outsideRoomStart();
                    }
                    if (i == 3) {
                        kitchenRoomStart();
                    }
                }
            }
        }
        if (collision(click, roomScroll.getRectangle())) {


            roomScroll.scroll();

        }

        if (infobox.boxShowed) {

            if (collision(click, infobox.getRectangle())) {

                if (!infobox.screenShowed) {
                    infobox.showScreen();
                    roomScroll.hide();


                } else {
                    infobox.hideScreen();
                    roomScroll.show();
                }


            }
        }


        if (state == GameState.PLAYROOM) {
            if (collision(click, stoneScissorPaperObject.getRectangle())) {

                stonePaperStart();

            }

            if (collision(click, hangerObject.getRectangle())) {
                player.hide();
                if (currentBody < gameSettings.bodyStyleCount) {
                    currentBody += 1;
                } else {
                    currentBody = 1;
                }

                player.setupBitmapAnimation(gameSettings.getPlayer(currentBody));
                player.show();


            }

        }
        if (state == GameState.BATH) {
            if (collision(click, bathTubeObject.getRectangle())) {

                showerGameStarted();

            }
        }
        if (state == GameState.KITCHEN) {
            if (collision(click, fridgeObject.getRectangle())) {

                statusBarArrayList.get(2).addValue(1);
                foodSaturation = statusBarArrayList.get(2).getValue();

            }
        }


        if (stoneScissorPaperGame != null) {
            for (int i = 0; i < stoneScissorPaperGame.stoneScissorPaperObjects.size(); i++) {

                if (collision(click, stoneScissorPaperGame.stoneScissorPaperObjects.get(i).getRectangle()) && !stoneScissorPaperGame.gameWon && !stoneScissorPaperGame.gameLost) {

                    switch (i) {
                        case 0:
                            stoneScissorPaperGame.paperUsed();
                            break;
                        case 1:
                            stoneScissorPaperGame.stoneUsed();
                            break;
                        case 2:
                            stoneScissorPaperGame.scissorUsed();
                            break;


                    }

                }
            }
        }


        return super.onTouchEvent(event);
    }

    private boolean collision(Rect click, Rect rectangle) {
        if (Rect.intersects(click, rectangle)) {

            return true;
        } else return false;
    }


    private void setCurrentBackground() {
        switch (state) {

            case KITCHEN:
                infobox.showBox();

                for (GameObject object : playRoomObjects) {
                    object.hide();
                }
                for (GameObject object : kitchenObjects) {
                    object.update();
                    object.show();
                }
                for (GameObject object : bathRoomObjects) {
                    object.hide();
                }
                for (GameObject object : outsideObjects) {
                    object.hide();
                }
                showerGame = null;
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.kitchen;
                break;
            case PLAYROOM:
                infobox.showBox();
                for (GameObject object : playRoomObjects) {
                    object.update();
                    object.show();
                }
                for (GameObject object : kitchenObjects) {
                    object.hide();
                }
                for (GameObject object : bathRoomObjects) {
                    object.hide();
                }
                for (GameObject object : outsideObjects) {
                    object.hide();
                }
                showerGame = null;
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.playroom;
                break;
            case STONEPAPER:
                infobox.showBox();
                for (GameObject object : playRoomObjects) {
                    object.hide();
                }
                for (GameObject object : kitchenObjects) {
                    object.hide();
                }
                for (GameObject object : bathRoomObjects) {
                    object.hide();
                }
                for (GameObject object : outsideObjects) {
                    object.hide();
                }

                showerGame = null;
                currentRoom = R.drawable.sspbackground;
                break;


            case SHOWERGAME:
                infobox.hideBox();
                for (GameObject object : playRoomObjects) {
                    object.hide();
                }
                for (GameObject object : kitchenObjects) {
                    object.hide();
                }
                for (GameObject object : bathRoomObjects) {
                    object.hide();
                }
                for (GameObject object : outsideObjects) {
                    object.hide();
                }
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.bathroom;
                break;
            case BATH:
                infobox.showBox();
                for (GameObject object : playRoomObjects) {
                    object.hide();
                }
                for (GameObject object : kitchenObjects) {
                    object.hide();
                }
                for (GameObject object : bathRoomObjects) {
                    object.update();
                    object.show();
                }
                for (GameObject object : outsideObjects) {
                    object.hide();
                }
                showerGame = null;
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.bathroom;
                break;
            case OUTSIDE:
                infobox.showBox();
                for (GameObject object : playRoomObjects) {
                    object.hide();
                }
                for (GameObject object : kitchenObjects) {
                    object.hide();
                }
                for (GameObject object : bathRoomObjects) {
                    object.hide();
                }
                for (GameObject object : outsideObjects) {
                    object.update();
                    object.show();

                }
                showerGame = null;
                stoneScissorPaperGame = null;
                currentRoom = R.drawable.backyard;
                break;

        }
    }


    private void died() {
        if (!isDead) {
            deadCounter = System.currentTimeMillis();
        }

        isDead = true;
        if ((System.currentTimeMillis() - deadCounter) / 1000 > 5) {

            currentLifeTime = "0:0:0:0";
            firstStartTime = timeStatusChanger.getCurrentDate();
            statusBarArrayList.get(1).setValue(5);
            fun = statusBarArrayList.get(1).getValue();
            statusBarArrayList.get(1).update();
            statusBarArrayList.get(0).setValue(4);
            hygiene = statusBarArrayList.get(0).getValue();
            statusBarArrayList.get(0).update();
            statusBarArrayList.get(2).setValue(3);
            foodSaturation = statusBarArrayList.get(2).getValue();
            statusBarArrayList.get(2).update();
            playRoomStart();
            isDead = false;

        }


    }


    public void update() throws InterruptedException {


        if (barChangerTimer <= frapsPerStatusLose) {
            barChangerTimer++;
        } else {
            barChangerTimer = 0;
        }
        if (barChangerTimer == frapsPerStatusLose && state != GameState.SHOWERGAME && state != GameState.STONEPAPER) {
            for (int i = 0; i < statusBarArrayList.size(); i++) {
                statusBarArrayList.get(i).addValue(-1);
                if (i == 0) {
                    hygiene = statusBarArrayList.get(0).getValue();
                }
                if (i == 1) {
                    fun = statusBarArrayList.get(1).getValue();
                }
                if (i == 2) {
                    foodSaturation = statusBarArrayList.get(2).getValue();
                }

            }
        }

        int[] currentLifeTimeArray = timeStatusChanger.getChangeTime(firstStartTime);
        currentLifeTime = currentLifeTimeArray[0] + ":" + currentLifeTimeArray[1] + ":" + currentLifeTimeArray[2] + ":" + currentLifeTimeArray[3];


        String[] currentLifeTimeSplit = currentLifeTime.split(":");
        String[] currentRecordSplit = lifeTimeRecord.split(":");

        for (int i = 0; i < currentLifeTimeArray.length; i++) {
            if (Integer.parseInt(currentLifeTimeSplit[i]) > Integer.parseInt(currentRecordSplit[i])) {
                lifeTimeRecord = currentLifeTime;
                infobox.setLifeTimeRecord(lifeTimeRecord);


            } else {
                if (Integer.parseInt(currentLifeTimeSplit[i]) < Integer.parseInt(currentRecordSplit[i])) {
                    break;
                }
            }
        }
        infobox.setCurrentLifeTime(currentLifeTime);

        if ((statusBarArrayList.get(0).getValue() == 0 && (statusBarArrayList.get(1).getValue() == 0 || statusBarArrayList.get(2).getValue() == 0)) || (statusBarArrayList.get(1).getValue() == 0 && statusBarArrayList.get(2).getValue() == 0)) {


            died();
        }


        if (player.getPlaying()) {
            room.update(BitmapFactory.decodeResource(getResources(), currentRoom, bitmapFactoryOptions));
            player.update();
        }


        if (state == GameState.STONEPAPER && stoneScissorPaperGame != null) {

            if ((stoneScissorPaperGame.gameWon || stoneScissorPaperGame.gameLost) && !currentWaitingProcess) {


                statusBarArrayList.get(1).addValue(2);
                fun = statusBarArrayList.get(1).getValue();
                currentWaitingProcess = true;
                thread.sleep(5000);
                currentWaitingProcess = false;
                playRoomStart();
                stoneScissorPaperGame = null;


            }

        }
        if (state == GameState.SHOWERGAME && showerGame != null) {

            statusBarArrayList.get(0).setValue(hygiene);
            statusBarArrayList.get(0).update();
            hygiene = showerGame.update();


        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        setCurrentBackground();


        if (canvas != null) {
            final int savedState = canvas.save();


            room.draw(canvas);
            drawStatusBars(canvas);

            switch (state) {
                case STONEPAPER:

                    player.draw(canvas);
                    stoneScissorPaperGame.draw(canvas);
                    break;

                case SHOWERGAME:

                    showerGame.draw(canvas);

                case KITCHEN:


                    for (GameObject object : kitchenObjects) {
                        object.draw(canvas);
                    }


                case PLAYROOM:
                    player.draw(canvas);
                    for (GameObject object : playRoomObjects) {
                        object.draw(canvas);
                    }


                    break;


                case BATH:
                    player.draw(canvas);
                    for (GameObject object : bathRoomObjects) {
                        object.draw(canvas);
                    }
                    break;


                case OUTSIDE:
                    player.draw(canvas);
                    for (GameObject object : outsideObjects) {
                        object.draw(canvas);

                    }
                    break;

            }
            infobox.draw(canvas);
            roomScroll.draw(canvas);
            if (isDead) {
                canvas.drawBitmap(deadOvo, 0, 0, null);
            }

            canvas.restoreToCount(savedState);
        }

    }

    private void drawStatusBars(Canvas canvas) {
        for (int i = 0; i < statusBarArrayList.size(); i++) {
            statusBarArrayList.get(i).draw(canvas);
        }


    }


}