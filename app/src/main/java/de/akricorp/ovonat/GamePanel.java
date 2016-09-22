package de.akricorp.ovonat;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.ArrayList;


//import de.akricorp.ovonat.actionObjects.RoomScroll;
import de.akricorp.ovonat.actionObjects.Playroom.StoneScissorPaperObject;
import de.akricorp.ovonat.actionObjects.StonePaperScissor.Paper;
import de.akricorp.ovonat.actionObjects.StonePaperScissor.Scissor;
import de.akricorp.ovonat.actionObjects.StonePaperScissor.Stone;
import de.akricorp.ovonat.repository.DataRepository;

/**
 * Created by Hannes on 23.07.2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private static GameSettings gameSettings =  new GameSettings();
    private MainThread thread;
    public int height = gameSettings.GAME_HEIGHT;
    public  int width = gameSettings.GAME_WIDTH;
    private int currentRoom = R.drawable.room2;
    private Room room;
   // private RoomScroll roomScroll;
    private Player player;

    private Stone stone;
    private Scissor scissor;
    private Paper paper;
    private StoneScissorPaperObject stoneScissorPaperObject;
    private float resolutionControlFactor;
    StatusBar healthBar;
    StatusBar hygeneBar;
    StatusBar saturationBar;
    DataRepository repository;
    Context context;
    ArrayList<StatusBar> statusBarArrayList = new ArrayList<>();
    public enum GameState{OUTSIDE,KITCHEN,PLAYROOM,BATH,STONEPAPER}
    GameState state =  GameState.PLAYROOM;






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
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

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
    public void surfaceCreated(SurfaceHolder holder)
    {


        room = new Room(BitmapFactory.decodeResource(getResources(), currentRoom));
        Log.d("roomW",""+room.getBitmap().getWidth());
        resolutionControlFactor = (float)room.getBitmap().getWidth()/(float)gameSettings.GAME_WIDTH;

        createStatusBars();

        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.ovo3),BitmapFactory.decodeResource(getResources(),R.drawable.eyes3),(int)(100*resolutionControlFactor),(int)(100*resolutionControlFactor),3,1000,300,resolutionControlFactor);
        stoneScissorPaperObject = new StoneScissorPaperObject(BitmapFactory.decodeResource(getResources(), R.drawable.stonescissorpaper),(int)(100*resolutionControlFactor), (int)(100*resolutionControlFactor),1,,150,resolutionControlFactor);
        //roomScroll = new RoomScroll(getWidth(),getHeight(),resolutionControlFactor,BitmapFactory.decodeResource(getResources(),R.drawable.kitchenbutton),BitmapFactory.decodeResource(getResources(),R.drawable.playroombutton),BitmapFactory.decodeResource(getResources(),R.drawable.outsidebutton),BitmapFactory.decodeResource(getResources(),R.drawable.bathbutton));
        //roomScroll.scroll();

        playRoomStart();

        //start gameloop


        Log.d("maße", "" + getWidth() + "   " + getHeight());

        thread.setRunning(true);
        thread.start();

    }

    private void playRoomStart(){
        state = GameState.PLAYROOM;

        setCurrentBackground();


    }

    private void stonePaperStarted(){
        state = GameState.STONEPAPER;
        setCurrentBackground();
        paper = new Paper(BitmapFactory.decodeResource(getResources(), R.drawable.paper),(int)(100*resolutionControlFactor), (int)(100*resolutionControlFactor),1,400,200,resolutionControlFactor);

        stone = new Stone(BitmapFactory.decodeResource(getResources(), R.drawable.stone),(int)(100*resolutionControlFactor), (int)(100*resolutionControlFactor),1,200,200,resolutionControlFactor);

        scissor = new Scissor(BitmapFactory.decodeResource(getResources(), R.drawable.scissor),(int)(100*resolutionControlFactor), (int)(100*resolutionControlFactor),1,600,200,resolutionControlFactor);



        paper.show();
        stone.show();

    }

    private void createStatusBars() {
        hygeneBar = new StatusBar(resolutionControlFactor,Integer.parseInt(repository.getData("hygene")),1);
        statusBarArrayList.add(hygeneBar);
        healthBar = new StatusBar(resolutionControlFactor,Integer.parseInt(repository.getData("health")),2);
        statusBarArrayList.add(healthBar);
        saturationBar = new StatusBar(resolutionControlFactor,Integer.parseInt(repository.getData("foodSaturation")),3);
        statusBarArrayList.add(saturationBar);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("getData", repository.getData("miniCount"));

        Rect click = new Rect((int)event.getX(), (int)event.getY(),(int)event.getX()+2, (int)event.getY()+2);
        Log.d("click","x: "+(int)event.getX()+", y: "+ (int)event.getY());


        /*if (collision(click,roomScroll.getRectangle())){
            Log.d("collision", "collision: yes");
            if(roomScroll.scrolledOut){roomScroll.scrollDown();
                Log.d("scrollCheck", "scrol Down!!!");}
            else {roomScroll.scrollUp(); Log.d("scrollCheck","ScrollUp!!");}
        }*/

        if (collision(click, stoneScissorPaperObject.getRectangle() )){
            Log.d("games", "SSP started");
            stonePaperStarted();
        }

        return super.onTouchEvent(event);
    }


    private boolean collision(Rect click, Rect rectangle) {
        if(Rect.intersects(click, rectangle)){

            return true;
        }
        else return false;
    }

    public void scaleObject(GameObject object){
        final float scaleFactorX = (float)getWidth()/(float)(gameSettings.GAME_WIDTH)/resolutionControlFactor;
        final float scaleFactorY = (float)getHeight()/(float)(gameSettings.GAME_HEIGHT)/resolutionControlFactor;
        object.scale(scaleFactorX);
    }

    public void update(){


        if(player.getPlaying()){
            room.update(BitmapFactory.decodeResource(getResources(), currentRoom));
            player.update();}

      /*  roomScroll.scale((int) (getWidth() / scaleFactorX), (int) (getHeight() / scaleFactorY));
         player.scale((int) (getWidth() / scaleFactorX), (int) (getHeight() / scaleFactorY));
         scissor.scale((int) (getWidth()/ scaleFactorX), (int) (getHeight() / scaleFactorY));
*/
    }

    private void setCurrentBackground() {
        switch(state){

            case KITCHEN: currentRoom = R.drawable.kitchen;break;
            case PLAYROOM: currentRoom = R.drawable.playroom;break;
            case STONEPAPER: currentRoom = R.drawable.stonepaperroom;break;
            case BATH: currentRoom = R.drawable.kitchen;break;
            case OUTSIDE: currentRoom = R.drawable.kitchen;break;

        }
    }

    @Override
    public void draw(Canvas canvas)
    {super.draw(canvas);
        Log.d("Positions","Canvas:  x:"+canvas.getWidth()+"  y:" +canvas.getHeight());
        final float scaleFactorX = (float)getWidth()/(float)(gameSettings.GAME_WIDTH)/resolutionControlFactor;
        final float scaleFactorY = (float)getHeight()/(float)(gameSettings.GAME_HEIGHT)/resolutionControlFactor;




        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            //Log.d("is shown :" ,""+stoneScissorPaperObject.isShown);
            room.draw(canvas);
            player.draw(canvas);
            stoneScissorPaperObject.draw(canvas);

            //roomScroll.draw(canvas);
            drawStatusBars(canvas);
            scissor.draw(canvas);
            stone.draw(canvas);
            paper.draw(canvas);

            canvas.restoreToCount(savedState);







        }
    }

    private void drawStatusBars(Canvas canvas){
        for(int i = 0; i<statusBarArrayList.size(); i++){
            statusBarArrayList.get(i).draw(canvas);
        }


    }

}