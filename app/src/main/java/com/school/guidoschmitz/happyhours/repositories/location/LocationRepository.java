package com.school.guidoschmitz.happyhours.repositories.location;

import android.database.Cursor;
import android.util.Log;

import com.school.guidoschmitz.happyhours.database.DBContract;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.Repository;

import org.json.JSONObject;

import java.util.ArrayList;

public class LocationRepository extends Repository {

    public LocationRepository() {
        api = new LocationApiRepository();
        cache = new LocationCacheRepository();
    }

    public static ArrayList<Location> all() {
        return ((LocationRepositoryInterface)repository).all();
    }

    public static Location get(int id) {
        return ((LocationRepositoryInterface)repository).get(id);
    }

    public static Location getByName(String name) {
        return ((LocationRepositoryInterface)repository).getByName(name);
    }

    public static void addAsFavorite(Location location) {
        ((LocationRepositoryInterface)repository).addAsFavorite(location);
    }

    public static void removeFavorite(Location location) {
        ((LocationRepositoryInterface)repository).removeFavorite(location);
    }

    public static ArrayList<Location> getFavorites() {
        return ((LocationRepositoryInterface)repository).getFavorites();
    }

    public static boolean isFavorite(Location location) {
        return ((LocationRepositoryInterface)repository).isFavorite(location);
    }

    public static Location parseJSON(JSONObject object) {
        Location location = new Location();

        try {
            location.setId(object.getInt("id"));
            location.setName(object.getString("name"));
            location.setDescription(object.getString("description"));
            location.setAddress(object.getString("address"));
            location.setLat(object.getDouble("lat"));
            location.setLon(object.getDouble("lon"));
        } catch (Exception e) {
            Log.i("JSON", "Failed to parse object to location");
        }

        return location;
    }

    public static Location parseCursor(Cursor cursor) {
        Location location = new Location();

        location.setId(cursor.getInt(cursor.getColumnIndex(DBContract.Location._ID)));
        location.setName(cursor.getString(cursor.getColumnIndex(DBContract.Location.NAME)));
        location.setDescription(cursor.getString(cursor.getColumnIndex(DBContract.Location.DESCRIPTION)));
        location.setAddress(cursor.getString(cursor.getColumnIndex(DBContract.Location.ADDRESS)));
        location.setLat(cursor.getDouble(cursor.getColumnIndex(DBContract.Location.LAT)));
        location.setLon(cursor.getDouble(cursor.getColumnIndex(DBContract.Location.LON)));

        return location;
    }
}
