package de.akricorp.ovonat.repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

import de.akricorp.ovonat.R;

/**
 * Created by Hannes on 04.08.2015.
 */
public class DataRepository {



    private static final String DATABASE_NAME = "ovonatDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "databaseItems";

    public static final String KEY_ID = "_id";
    public static final String KEY_FIRST_START_TIME = "startTime";
    public static final String KEY_LAST_CLOSE_TIME = "closeTime";
    public static final String KEY_MINI_COUNT = "miniGameCount";


    public static final String KEY_CURRENT_BOOTS = "currentBoots";
    public static final String KEY_CURRENT_HAIR = "currentHair";
    public static final String KEY_CURRENT_BODY = "currentBody";

    public static final String KEY_FOOD_SATURATION = "foodSaturation";
    public static final String KEY_HYGIENE = "hygiene";
    public static final String KEY_FUN = "fun";



    public static final int FIRST_START_TIME_INDEX = 0;
    public static final int LAST_CLOSE_TIME_INDEX = 1;
    public static final int MINI_COUNT_INDEX = 2;
    public static final int CURRENT_BOOTS_INDEX = 3;
    public static final int CURRENT_HAIR_INDEX = 4;
    public static final int CURRENT_BODY_INDEX = 5;
    public static final int KEY_FOOD_SATURATION_INDEX = 6;
    public static final int HYGIENE_INDEX = 7;
    public static final int FUN_INDEX = 8;


    private DataDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public DataRepository(Context context) {
        dbHelper = new DataDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);

    }


    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }


    /*
    public long addFoodieItem(FoodieItem item) {
        ContentValues newFoodieValues = new ContentValues();
        newFoodieValues.put(KEY_IMAGE_PATH, item.getImagePath());
        newFoodieValues.put(KEY_RATING, item.getRating());
        newFoodieValues.put(KEY_TITLE, item.getTitle());
        return db.insert(DATABASE_TABLE, null, newFoodieValues);
    }
    */


    /*
    public void deleteFoodieItem(String foodieItemID) {             // alte datenbank ueberschreibt nur
        db.delete(DATABASE_TABLE, KEY_ID + "=" + foodieItemID + "+ 1", null);
    }
    */

    /*
    public ArrayList<FoodieItem> getAllFoodieItems() {                 //daten aus datenbank holen
        ArrayList<FoodieItem> items = new ArrayList<FoodieItem>();
        items.clear();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                KEY_IMAGE_PATH, KEY_RATING, KEY_TITLE}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(COLUMN_TITLE_INDEX);
                String imagePath = cursor.getString(COLUMN_IMAGE_PATH_INDEX);
                float rating = cursor.getFloat(COLUMN_RATING_INDEX);
                items.add(new FoodieItem(imagePath, rating, title));
            } while (cursor.moveToNext());
        }
        return items;
    }
    */

    /*
    public FoodieItem getFoodieItem(String foodieItemID) {                  // wichtig, holt einzelne daten
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_IMAGE_PATH, KEY_RATING, KEY_TITLE}, null, null, null, null, null);
        cursor.moveToPosition(Integer.parseInt(foodieItemID));
        String title = cursor.getString(COLUMN_TITLE_INDEX);
        String imagePath = cursor.getString(COLUMN_IMAGE_PATH_INDEX);
        float rating = cursor.getFloat(COLUMN_RATING_INDEX);
        return new FoodieItem(imagePath, rating, title);
    }
    */

    public String getData(String requestedData){

        String returnString="";
        Cursor cursorSettings = db.query(DATABASE_TABLE, new String[]{KEY_FIRST_START_TIME,
                KEY_LAST_CLOSE_TIME,KEY_MINI_COUNT,KEY_CURRENT_BOOTS, KEY_CURRENT_HAIR,
                KEY_CURRENT_BODY, KEY_FOOD_SATURATION, KEY_HYGIENE, KEY_FUN}, null, null, null, null, null);
        cursorSettings.moveToFirst();

        switch(requestedData) {

            case "firstStartTime":
                returnString = cursorSettings.getString(FIRST_START_TIME_INDEX);


                return returnString;
            case "lastCloseTime":
                returnString = cursorSettings.getString(LAST_CLOSE_TIME_INDEX);
                break;
            case "miniCount":
                returnString = cursorSettings.getString(MINI_COUNT_INDEX);
                break;
            case "boots":
                returnString = cursorSettings.getString(CURRENT_BOOTS_INDEX);
                break;
            case "hair":
                returnString = cursorSettings.getString(CURRENT_HAIR_INDEX);
                break;
            case "body":
                returnString = cursorSettings.getString(CURRENT_BODY_INDEX);
                break;
            case "foodSaturation":
                returnString = cursorSettings.getString(KEY_FOOD_SATURATION_INDEX);
                break;
            case "hygiene":
                returnString = cursorSettings.getString(HYGIENE_INDEX);
                break;
            case "fun":
                returnString = cursorSettings.getString(FUN_INDEX);
                break;
        }
        return returnString;
    }

    public int putIntoDb(String changedData, String newValue){
        ContentValues cv = new ContentValues();
        Log.d("putintodb","done");
        switch(changedData){                                    // KOENNTE FEHLER HABEN

            case "firstStartTime":
                 /*  db.execSQL("UPDATE " + DATABASE_TABLE + " SET " + KEY_FIRST_START_TIME + " = " + newValue + " WHERE _id = 1;");    Zweitlösung
                break;*/
                cv.put(KEY_FIRST_START_TIME, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);

            case "lastCloseTime":

                cv.put(KEY_LAST_CLOSE_TIME, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);
            case "miniCount":
                cv.put(KEY_MINI_COUNT, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);
            case "boots":
                cv.put(KEY_CURRENT_BOOTS, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);
            case "hair":
                cv.put(KEY_CURRENT_HAIR, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);
            case "body":
                cv.put(KEY_CURRENT_BODY, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);
            case "foodSaturation":
                cv.put(KEY_FOOD_SATURATION, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);
            case "hygiene":
                cv.put(KEY_HYGIENE, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);
            case "fun":
                cv.put(KEY_FUN, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);
        }
        return 0;
    }


    /*
    public long updateRating(String foodieItemID, float rating) {                   //ueberschreibt alte daten
        ContentValues cv = new ContentValues();
        cv.put(KEY_RATING, rating);
        db.update(DATABASE_TABLE, cv, KEY_ID + " = " + foodieItemID + " + 1", null);
        return 1;
    }
    */

    private class DataDBOpenHelper extends SQLiteOpenHelper {


        private static final String DATABASE_CREATE = "CREATE TABLE "
                + DATABASE_TABLE + "(" + KEY_ID
                + " INTEGER primary key autoincrement, " + KEY_FIRST_START_TIME + " text, "
                + KEY_LAST_CLOSE_TIME + " text, " + KEY_MINI_COUNT +  " text ," + KEY_CURRENT_BOOTS + " text, "
                + KEY_CURRENT_HAIR + " text, " + KEY_CURRENT_BODY + " text, "
                + KEY_FOOD_SATURATION +" text, "+ KEY_HYGIENE +" text , "+ KEY_FUN +" text);";



        public DataDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            Log.d("db created ", "success");



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private static boolean doesDatabaseExist(ContextWrapper context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
    public void firstSetup(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_FIRST_START_TIME,
                KEY_LAST_CLOSE_TIME,KEY_MINI_COUNT,KEY_CURRENT_BOOTS, KEY_CURRENT_HAIR,
                KEY_CURRENT_BODY, KEY_FOOD_SATURATION, KEY_HYGIENE, KEY_FUN}, null, null, null, null, null);



        if(!cursor.moveToFirst()) {
            ContentValues firstOvoValues = new ContentValues();
            firstOvoValues.put(KEY_FIRST_START_TIME, "" + System.nanoTime());
            firstOvoValues.put(KEY_LAST_CLOSE_TIME, "2");
            firstOvoValues.put(KEY_MINI_COUNT, "3");
            firstOvoValues.put(KEY_CURRENT_BOOTS, "4");
            firstOvoValues.put(KEY_CURRENT_HAIR, "5");
            firstOvoValues.put(KEY_CURRENT_BODY, "6");
            firstOvoValues.put(KEY_FOOD_SATURATION, "7");
            firstOvoValues.put(KEY_HYGIENE, "8");
            firstOvoValues.put(KEY_FUN, "9");
            db.insert(DATABASE_TABLE, null, firstOvoValues);
        }

    }

    public void deleteDb(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
}