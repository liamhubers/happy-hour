package com.school.guidoschmitz.happyhours.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.school.guidoschmitz.happyhours.database.DBContract;
import com.school.guidoschmitz.happyhours.database.DBHelper;
import com.school.guidoschmitz.happyhours.models.Location;

import java.util.ArrayList;

public class LocationCacheRepository implements LocationRepositoryInterface {
    private static SQLiteDatabase database;

    public LocationCacheRepository(Context context) {
        database = new DBHelper(context).getWritableDatabase();
    }

    @Override
    public ArrayList<Location> all() {
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBContract.Location.TABLE, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                locations.add(parseCursor(cursor));

                cursor.moveToNext();
            }
        }

        return locations;
    }

    @Override
    public Location get(int id) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBContract.Location.TABLE + " WHERE " + DBContract.Location._ID + " = " + id, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                return parseCursor(cursor);
            }
        }

        return null;
    }

    @Override
    public Location getByName(String name) {
        return null;
    }

    public void setLocations(ArrayList<Location> locations) {
        for (int i = 0; i < locations.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DBContract.Location._ID, locations.get(i).getId());
            values.put(DBContract.Location.NAME, locations.get(i).getName());
            values.put(DBContract.Location.DESCRIPTION, locations.get(i).getDescription());
            values.put(DBContract.Location.LAT, locations.get(i).getLat());
            values.put(DBContract.Location.LON, locations.get(i).getLon());

            long id = database.insert(DBContract.Location.TABLE, "", values);
        }
    }

    private Location parseCursor(Cursor cursor) {
        Location location = new Location();

        location.setId(cursor.getInt(cursor.getColumnIndex(DBContract.Location._ID)));
        location.setName(cursor.getString(cursor.getColumnIndex(DBContract.Location.NAME)));
        location.setDescription(cursor.getString(cursor.getColumnIndex(DBContract.Location.DESCRIPTION)));
        location.setLat(cursor.getDouble(cursor.getColumnIndex(DBContract.Location.LAT)));
        location.setLon(cursor.getDouble(cursor.getColumnIndex(DBContract.Location.LON)));

        return location;
    }
}
