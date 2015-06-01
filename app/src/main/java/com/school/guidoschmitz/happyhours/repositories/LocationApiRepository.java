package com.school.guidoschmitz.happyhours.repositories;

import android.util.Log;

import com.school.guidoschmitz.happyhours.Api;
import com.school.guidoschmitz.happyhours.models.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Liam Hubers on 29-5-2015.
 */
public class LocationApiRepository implements LocationRepositoryInterface {
    @Override
    public ArrayList<Location> all() {
        ArrayList<Location> locations = new ArrayList<>();
        try{
            String JSON = new Api().execute("http://happy-hours.guidoschmitz.nl/locations").get();
            JSONArray array = new JSONArray(JSON);

            for(int i = 0; i < array.length(); i++) {
                locations.add(parseJSON(new JSONObject(array.get(i).toString())));
            }
        } catch(Exception e) {
            Log.i("API", "Couldn't fetch location data");
        }

        LocationRepository.setLocations(locations);

        return locations;
    }

    @Override
    public Location get(int id) {
        try {
            String JSON = new Api().execute("http://happy-hours.guidoschmitz.nl/locations/" + id).get();
            return parseJSON(new JSONObject(JSON));
        } catch(Exception e) {
            Log.i("API", "Couldn't fetch location with id "+id);
        }

        return null;
    }

    private Location parseJSON(JSONObject object) {
        Location location = new Location();

        try {
            location.setId(object.getInt("id"));
            location.setName(object.getString("name"));
            location.setDescription(object.getString("description"));
            location.setLat(object.getDouble("lat"));
            location.setLon(object.getDouble("lon"));
        } catch(Exception e) {
            Log.i("JSON", "Failed to parse object to location");
        }

        return location;
    }
}
