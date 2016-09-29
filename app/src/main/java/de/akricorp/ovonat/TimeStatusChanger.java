package de.akricorp.ovonat;

import android.util.Log;

import java.text.ParsePosition;
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





    }



    public void getDataFromRepository(String firstStartTime, String lastCloseTime){
        this.firstStartTime = firstStartTime;
        this.lastCloseTime = lastCloseTime;
    }

    public String getCurrentDate(){
        return  this.date = dateFormat.format(calendar.getTime());
    }

    public int getChangeValue() {

        int changeValue;
        calendar = Calendar.getInstance();
        if (lastCloseTime.compareTo("0") != 0) {   //wenn lastCloseTime = 0, wird die app das erste mal geöffnet
            long timeChange = calendar.getTime().getTime() - dateFormat.parse(lastCloseTime, new ParsePosition(0)).getTime();

            long hourChange = timeChange / (60 * 60 * 1000) % 60;
            changeValue = (int) (hourChange / 3);
        }
         else{changeValue = 0;}




        return changeValue;
    }
}
