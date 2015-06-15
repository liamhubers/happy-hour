package com.school.guidoschmitz.happyhours.repositories.user;

import android.content.ContentValues;
import android.database.Cursor;

import com.school.guidoschmitz.happyhours.database.DBContract;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.CacheRepository;
import com.school.guidoschmitz.happyhours.repositories.location.LocationRepository;

import java.util.ArrayList;

public class UserCacheRepository extends CacheRepository implements UserRepositoryInterface {

    @Override
    public void addFavorite(Location location) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Favorite.LOCATION_ID, location.getId());

        long id = database.insert(DBContract.Favorite.TABLE, "", values);
    }

    @Override
    public void removeFavorite(Location location) {
        // todo
    }

    @Override
    public ArrayList<Location> getFavorites(int userId) {
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBContract.Location.TABLE + " WHERE " + DBContract.Location._ID + " IN ((SELECT " + DBContract.Favorite.LOCATION_ID + " FROM " + DBContract.Favorite.TABLE + " ))", null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                locations.add(LocationRepository.parseCursor(cursor));

                cursor.moveToNext();
            }
        }

        return locations;
    }

    public static void setFavorites(ArrayList<Location> locations) {
        /*database.execSQL("DELETE FROM " + DBContract.Location.TABLE);

        for (int i = 0; i < locations.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DBContract.Location._ID, locations.get(i).getId());
            values.put(DBContract.Location.NAME, locations.get(i).getName());
            values.put(DBContract.Location.DESCRIPTION, locations.get(i).getDescription());
            values.put(DBContract.Location.ADDRESS, locations.get(i).getAddress());
            values.put(DBContract.Location.LAT, locations.get(i).getLat());
            values.put(DBContract.Location.LON, locations.get(i).getLon());

            long id = database.insert(DBContract.Location.TABLE, "", values);
        }*/
    }
}
