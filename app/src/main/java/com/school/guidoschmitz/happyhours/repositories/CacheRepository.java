package com.school.guidoschmitz.happyhours.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.school.guidoschmitz.happyhours.database.DBHelper;

/**
 * Created by Liam on 11/06/2015.
 */
public class CacheRepository extends RepositoryInterface {
    protected static SQLiteDatabase database;

    public void createDatabase(Context context) {
        database = new DBHelper(context).getWritableDatabase();
    }
}
