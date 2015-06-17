package com.school.guidoschmitz.happyhours.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.school.guidoschmitz.happyhours.database.DBHelper;

/**
 * Created by Liam on 11/06/2015.
 */
public class CacheRepository {
    private static SQLiteDatabase database;

    public static void createDatabase(Context context) {
        database = new DBHelper(context).getWritableDatabase();
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    public static void closeDatabase() {
        try {
            database.close();
        } catch(Exception e) {}
    }
}
