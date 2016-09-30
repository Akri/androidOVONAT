package de.akricorp.ovonat;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TimeStatusChanger {

    String date;
    String firstStartTime;

    SimpleDateFormat dateFormat;
    Calendar calendar;

    public TimeStatusChanger() {
        this.calendar = Calendar.getInstance();
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.date = dateFormat.format(calendar.getTime());


    }


    public void getDataFromRepository(String firstStartTime) {
        this.firstStartTime = firstStartTime;

    }

    public String getCurrentDate() {
        return this.date = dateFormat.format(calendar.getTime());
    }

    public int getChangeValue(String fromDate) {

        int changeValue;
        calendar = Calendar.getInstance();
        if (fromDate.compareTo("0") != 0) {   //wenn lastCloseTime = 0, wird die app das erste mal geöffnet
            long timeChange = calendar.getTime().getTime() - dateFormat.parse(fromDate, new ParsePosition(0)).getTime();

            long hourChange = timeChange / (60 * 60 * 1000) % 60;
            changeValue = (int) (hourChange / 3);
        } else {
            changeValue = 0;
        }

        return changeValue;
    }


    public int[] getChangeTime(String fromDate) {

        int[] changeValue = {0, 0, 0, 0};
        calendar = Calendar.getInstance();

        long timeChange = calendar.getTime().getTime() - dateFormat.parse(fromDate, new ParsePosition(0)).getTime();

        long dayChange = timeChange / (24 * 60 * 60 * 1000) % 60;
        long hourChange = timeChange / (60 * 60 * 1000) % 60;
        long minuteChange = timeChange / (60 * 1000) % 60;
        long secondChange = timeChange / (1000) % 60;

        changeValue[0] = (int) dayChange;
        changeValue[1] = (int) hourChange;
        changeValue[2] = (int) minuteChange;
        changeValue[3] = (int) secondChange;


        return changeValue;
    }

}