package de.akricorp.ovonat.repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.io.File;


public class DataRepository {


    Cursor cursor;
    private static final String DATABASE_NAME = "ovonatDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "databaseItems";
    private static final String KEY_LIFE_TIME_RECORD = "timeRecord";
    public static final String KEY_ID = "_id";
    public static final String KEY_FIRST_START_TIME = "firstStartTime";
    public static final String KEY_LAST_CLOSE_TIME = "fastCloseTime";
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
    public static final int LIFE_TIME_RECORD_INDEX = 9;


    private DataDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public DataRepository(Context context) {
        dbHelper = new DataDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);


    }

    public void setCursor() {
        this.cursor = db.query(DATABASE_TABLE, new String[]{KEY_FIRST_START_TIME,
                KEY_LAST_CLOSE_TIME, KEY_MINI_COUNT, KEY_CURRENT_BOOTS, KEY_CURRENT_HAIR,
                KEY_CURRENT_BODY, KEY_FOOD_SATURATION, KEY_HYGIENE, KEY_FUN, KEY_LIFE_TIME_RECORD}, null, null, null, null, null);

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


    public String getData(String requestedData) {

        String returnString = "";

        cursor.moveToFirst();

        switch (requestedData) {

            case "firstStartTime":
                returnString = cursor.getString(FIRST_START_TIME_INDEX);


                return returnString;
            case "lastCloseTime":
                returnString = cursor.getString(LAST_CLOSE_TIME_INDEX);
                break;
            case "miniCount":
                returnString = cursor.getString(MINI_COUNT_INDEX);
                break;
            case "boots":
                returnString = cursor.getString(CURRENT_BOOTS_INDEX);
                break;
            case "hair":
                returnString = cursor.getString(CURRENT_HAIR_INDEX);
                break;
            case "body":
                returnString = cursor.getString(CURRENT_BODY_INDEX);
                break;
            case "foodSaturation":
                returnString = cursor.getString(KEY_FOOD_SATURATION_INDEX);
                break;
            case "hygiene":
                returnString = cursor.getString(HYGIENE_INDEX);
                break;
            case "fun":
                returnString = cursor.getString(FUN_INDEX);
                break;
            case "timeRecord":
                returnString = cursor.getString(LIFE_TIME_RECORD_INDEX);
        }
        return returnString;
    }

    public int putIntoDb(String changedData, String newValue) {
        ContentValues cv = new ContentValues();

        switch (changedData) {

            case "firstStartTime":

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
            case "timeRecord":
                cv.put(KEY_LIFE_TIME_RECORD, newValue);
                return db.update(DATABASE_TABLE, cv, null, null);
        }
        return 0;
    }


    private class DataDBOpenHelper extends SQLiteOpenHelper {


        private static final String DATABASE_CREATE = "CREATE TABLE "
                + DATABASE_TABLE + "(" + KEY_ID
                + " INTEGER primary key autoincrement, " + KEY_FIRST_START_TIME + " text, "
                + KEY_LAST_CLOSE_TIME + " text, " + KEY_MINI_COUNT + " text ," + KEY_CURRENT_BOOTS + " text, "
                + KEY_CURRENT_HAIR + " text, " + KEY_CURRENT_BODY + " text, "
                + KEY_FOOD_SATURATION + " text, " + KEY_HYGIENE + " text , " + KEY_FUN + " text, " + KEY_LIFE_TIME_RECORD + " text);";


        public DataDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    public void firstSetup(String currentDate) {

        cursor = db.query(DATABASE_TABLE, new String[]{KEY_FIRST_START_TIME,
                KEY_LAST_CLOSE_TIME, KEY_MINI_COUNT, KEY_CURRENT_BOOTS, KEY_CURRENT_HAIR,
                KEY_CURRENT_BODY, KEY_FOOD_SATURATION, KEY_HYGIENE, KEY_FUN, KEY_LIFE_TIME_RECORD}, null, null, null, null, null);


        if (!cursor.moveToFirst()) {
            ContentValues firstOvoValues = new ContentValues();
            firstOvoValues.put(KEY_FIRST_START_TIME, currentDate);
            firstOvoValues.put(KEY_LAST_CLOSE_TIME, "0");
            firstOvoValues.put(KEY_MINI_COUNT, "3");
            firstOvoValues.put(KEY_CURRENT_BOOTS, "4");
            firstOvoValues.put(KEY_CURRENT_HAIR, "5");
            firstOvoValues.put(KEY_CURRENT_BODY, "2");
            firstOvoValues.put(KEY_FOOD_SATURATION, "3");
            firstOvoValues.put(KEY_HYGIENE, "4");
            firstOvoValues.put(KEY_FUN, "5");
            firstOvoValues.put(KEY_LIFE_TIME_RECORD, "0:0:0:0");
            db.insert(DATABASE_TABLE, null, firstOvoValues);
        }

    }

    public boolean repositoryIsEmpty() {
        if (cursor.moveToFirst()) {
            return false;

        } else {

            return true;
        }
    }

    public void deleteDb(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}