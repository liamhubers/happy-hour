package com.school.guidoschmitz.happyhours.repositories.location;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.school.guidoschmitz.happyhours.database.DBContract;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.CacheRepository;

import java.util.ArrayList;

public class LocationCacheRepository extends CacheRepository implements LocationRepositoryInterface {

    @Override
    public ArrayList<Location> all() {
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBContract.Location.TABLE, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                locations.add(LocationRepository.parseCursor(cursor));

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
                return LocationRepository.parseCursor(cursor);
            }
        }

        return null;
    }

    @Override
    public Location getByName(String name) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBContract.Location.TABLE + " WHERE " + DBContract.Location.NAME + " = '" + name + "'", null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                return LocationRepository.parseCursor(cursor);
            }
        }

        return null;
    }

    @Override
    public void addAsFavorite(Location location) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Favorite.LOCATION_ID, location.getId());

        long id = database.insert(DBContract.Favorite.TABLE, "", values);
    }

    public void removeFavorite(Location location) {
        Log.i("remove", location.getId()+"");
        database.delete(DBContract.Favorite.TABLE, DBContract.Favorite.LOCATION_ID + " = " + location.getId(), null);
    }

    @Override
    public ArrayList<Location> getFavorites() {
        ArrayList<Location> locations = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT " + DBContract.Favorite.LOCATION_ID + " FROM " + DBContract.Favorite.TABLE, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                locations.add(get(cursor.getInt(cursor.getColumnIndex(DBContract.Favorite.LOCATION_ID))));

                Log.i("cursor", cursor.getInt(cursor.getColumnIndex(DBContract.Favorite.LOCATION_ID)) + "");

                cursor.moveToNext();
            }
        }

        return locations;
    }

    @Override
    public boolean isFavorite(Location location) {
        Cursor cursor = database.rawQuery("SELECT "+DBContract.Favorite._ID+" FROM favorites WHERE " + DBContract.Favorite.LOCATION_ID + " = " + location.getId(), null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                return true;
            }
        }

        return false;
    }

    public static void setLocations(ArrayList<Location> locations) {
        database.execSQL("DELETE FROM " + DBContract.Location.TABLE);

        for (int i = 0; i < locations.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DBContract.Location._ID, locations.get(i).getId());
            values.put(DBContract.Location.NAME, locations.get(i).getName());
            values.put(DBContract.Location.DESCRIPTION, locations.get(i).getDescription());
            values.put(DBContract.Location.ADDRESS, locations.get(i).getAddress());
            values.put(DBContract.Location.THUMBNAIL, locations.get(i).getThumbnailString());
            values.put(DBContract.Location.LAT, locations.get(i).getLat());
            values.put(DBContract.Location.LON, locations.get(i).getLon());

            long id = database.insert(DBContract.Location.TABLE, "", values);
        }
    }
}
