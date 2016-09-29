package de.akricorp.ovonat;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.akricorp.ovonat.repository.DataRepository;

/**
 * Created by Hännes on 29.09.2016.
 */

public class TimeStatusChanger {

    String date;
    String firstStartTime;
    String lastCloseTime;
    SimpleDateFormat dateFormat;
    Calendar calendar;

    public TimeStatusChanger( ){
        this.calendar = Calendar.getInstance();
        this. dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.date = dateFormat.format(calendar.getTime());

        Log.d("dateTime","realDate: "+date);



    }

    public void logcheck(){
        this.calendar = Calendar.getInstance();
        this.date = dateFormat.format(calendar.getTime());
        Log.d("dateTime","Date from repository: "+firstStartTime);
        Log.d("dateTime","realDate: "+date);

    }

    public void getDataFromRepository(String firstStartTime, String lastCloseTime){
        this.firstStartTime = firstStartTime;
        this.lastCloseTime = lastCloseTime;
    }

    public String getCurrentDate(){
        return  this.date = dateFormat.format(calendar.getTime());
    }
}
