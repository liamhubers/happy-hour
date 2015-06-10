package com.school.guidoschmitz.happyhours.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Liam Hubers on 1-6-2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "database.db";
    static final Integer VERSION = 1;
    DBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ DBContract.Location.TABLE + " ("+
                DBContract.Location._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DBContract.Location.NAME +" VARCHAR NOT NULL, "+
                DBContract.Location.DESCRIPTION +" TEXT NOT NULL, "+
                DBContract.Location.ADDRESS +" VARCHAR NOT NULL, "+
                DBContract.Location.THUMBNAIL +" VARCHAR, "+
                DBContract.Location.LAT +" DOUBLE(10,6) NOT NULL, "+
                DBContract.Location.LON +" DOUBLE(10,6) NOT NULL"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Location.TABLE);
        onCreate(db);
    }
}
