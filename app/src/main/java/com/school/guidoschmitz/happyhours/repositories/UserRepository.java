package com.school.guidoschmitz.happyhours.repositories;

import android.content.ContentValues;
import android.database.Cursor;

import com.school.guidoschmitz.happyhours.database.DBContract;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.CacheRepository;
import com.school.guidoschmitz.happyhours.repositories.LocationRepository;

import java.util.ArrayList;

public class UserRepository extends CacheRepository {

    public static void addFavorite(Location location) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Favorite.LOCATION_ID, location.getId());

        getDatabase().insert(DBContract.Favorite.TABLE, "", values);
    }

    public static void removeFavorite(Location location) {
        getDatabase().delete(DBContract.Favorite.TABLE, DBContract.Favorite.LOCATION_ID + " = " + location.getId(), null);
    }

    public static ArrayList<Location> getFavorites() {
        ArrayList<Location> locations = new ArrayList<>();

        Cursor cursor = getDatabase().rawQuery("SELECT * FROM " + DBContract.Location.TABLE + " WHERE " + DBContract.Location._ID + " IN (SELECT " + DBContract.Favorite.LOCATION_ID + " FROM " + DBContract.Favorite.TABLE + ")", null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                locations.add(LocationRepository.toLocation(cursor));

                cursor.moveToNext();
            }
        }

        return locations;
    }
}
