package com.school.guidoschmitz.happyhours.repositories.location;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import com.school.guidoschmitz.happyhours.LocationApi;
import com.school.guidoschmitz.happyhours.database.DBContract;
import com.school.guidoschmitz.happyhours.fragments.MainFragment;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.CacheRepository;
import org.json.JSONObject;
import java.util.ArrayList;

public class LocationRepository extends CacheRepository {

    public void onOnline(MainFragment fragment) {
        new LocationApi(this, fragment).execute();
    }

    public static ArrayList<Location> all() {
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM " + DBContract.Location.TABLE, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                locations.add(LocationRepository.toLocation(cursor));

                cursor.moveToNext();
            }
        }

        return locations;
    }

    public static void save(Location location) {
        getDatabase().insert(DBContract.Location.TABLE, "", toContentValues(location));
    }

    public static Location toLocation(JSONObject object) {
        Location location = new Location();

        try {
            location.setId(object.getInt("id"));
            location.setName(object.getString("name"));
            location.setDescription(object.getString("description"));
            location.setAddress(object.getString("address"));
            location.setLat(object.getDouble("lat"));
            location.setLon(object.getDouble("lon"));
            location.setThumbnail(object.getString("thumbnail"));
        } catch (Exception e) {
            Log.i("JSON", "Failed to parse object to location");
        }

        return location;
    }
    public static Location toLocation(Cursor cursor) {
        Location location = new Location();

        location.setId(cursor.getInt(cursor.getColumnIndex(DBContract.Location._ID)));
        location.setName(cursor.getString(cursor.getColumnIndex(DBContract.Location.NAME)));
        location.setDescription(cursor.getString(cursor.getColumnIndex(DBContract.Location.DESCRIPTION)));
        location.setAddress(cursor.getString(cursor.getColumnIndex(DBContract.Location.ADDRESS)));
        location.setLat(cursor.getDouble(cursor.getColumnIndex(DBContract.Location.LAT)));
        location.setLon(cursor.getDouble(cursor.getColumnIndex(DBContract.Location.LON)));
        location.setThumbnail(cursor.getString(cursor.getColumnIndex(DBContract.Location.THUMBNAIL)));

        return location;
    }

    public static ContentValues toContentValues(Location location) {
        ContentValues values = new ContentValues();

        values.put(DBContract.Location._ID, location.getId());
        values.put(DBContract.Location.NAME, location.getName());
        values.put(DBContract.Location.DESCRIPTION, location.getDescription());
        values.put(DBContract.Location.ADDRESS, location.getAddress());
        values.put(DBContract.Location.THUMBNAIL, location.getThumbnail());
        values.put(DBContract.Location.LAT, location.getLat());
        values.put(DBContract.Location.LON, location.getLon());

        return values;
    }

    public static boolean isFavorite(Location location) {
        Cursor cursor = getDatabase().rawQuery("SELECT "+DBContract.Favorite._ID+" FROM favorites WHERE " + DBContract.Favorite.LOCATION_ID + " = " + location.getId(), null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                return true;
            }
        }

        return false;
    }
}
