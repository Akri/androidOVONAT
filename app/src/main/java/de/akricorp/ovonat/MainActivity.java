package de.akricorp.ovonat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import de.akricorp.ovonat.repository.DataRepository;


public class MainActivity extends Activity {
    DataRepository repository;
    TimeStatusChanger timeStatusChanger;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loadscreen_layout);
        this.repository = new DataRepository(this);  //repository is being created
        this.timeStatusChanger = new TimeStatusChanger();
        repository.open();
        repository.setCursor();
        Log.d("testreposi",repository.repositoryIsEmpty()+"" );
        if(repository.repositoryIsEmpty()){
            repository.firstSetup(timeStatusChanger.getCurrentDate());
        }

        final android.os.Handler handler = new android.os.Handler();  //waiting for the repository to be created to start the game
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                repository.close();
                setContentView(new GamePanel(context));

            }
        }, 3000);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}