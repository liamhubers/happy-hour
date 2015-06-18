package com.school.guidoschmitz.happyhours.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "database.db";
    static final Integer VERSION = 3;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBContract.Location.TABLE + " (" +
                DBContract.Location._ID + " INTEGER ," +
                DBContract.Location.NAME + " VARCHAR NOT NULL, " +
                DBContract.Location.DESCRIPTION + " TEXT NOT NULL, " +
                DBContract.Location.ADDRESS + " VARCHAR NOT NULL, " +
                DBContract.Location.THUMBNAIL + " VARCHAR, " +
                DBContract.Location.LAT + " DOUBLE(10,6) NOT NULL, " +
                DBContract.Location.LON + " DOUBLE(10,6) NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE " + DBContract.Event.TABLE + " (" +
                DBContract.Event._ID + " INTEGER ," +
                DBContract.Event.LOCATION_ID + " INTEGER NOT NULL ," +
                DBContract.Event.DESCRIPTION + " TEXT NOT NULL ," +
                DBContract.Event.DAY_OF_WEEK + " INTEGER NOT NULL ," +
                DBContract.Event.START_TIME + " TIME ," +
                DBContract.Event.END_TIME + " TIME" +
                ")");

        db.execSQL("CREATE TABLE " + DBContract.Favorite.TABLE + " (" +
                DBContract.Favorite._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.Favorite.LOCATION_ID + " INTEGER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Location.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Event.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Favorite.TABLE);
        onCreate(db);
    }
}
