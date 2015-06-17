package com.school.guidoschmitz.happyhours.repositories.location;

import android.util.Log;

import com.school.guidoschmitz.happyhours.Api;
import com.school.guidoschmitz.happyhours.models.Event;
import com.school.guidoschmitz.happyhours.models.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class LocationApiRepository implements LocationRepositoryInterface {
    @Override
    public ArrayList<Location> all() {
        ArrayList<Location> locations = new ArrayList<>();
        try {
            String JSON = new Api().execute("http://happy-hours.guidoschmitz.nl/locations").get();
            JSONArray array = new JSONArray(JSON);

            for (int i = 0; i < array.length(); i++) {
                locations.add(LocationRepository.parseJSON(new JSONObject(array.get(i).toString())));
            }
        } catch (Exception e) {
            Log.i("API", "Couldn't fetch location data");
        }

        LocationCacheRepository.setLocations(locations);

        return locations;
    }

    @Override
    public Location get(int id) {
        try {
            String JSON = new Api().execute("http://happy-hours.guidoschmitz.nl/locations/" + id).get();
            return LocationRepository.parseJSON(new JSONObject(JSON));
        } catch (Exception e) {
            Log.i("API", "Couldn't fetch location with id " + id);
        }

        return null;
    }

    public Location getByName(String name) {
        try {
            String JSON = new Api().execute("http://happy-hours.guidoschmitz.nl/locations?name=" + URLEncoder.encode(name, "UTF-8")).get();
            return LocationRepository.parseJSON(new JSONObject(JSON));
        } catch (Exception e) {
            Log.i("API", "Couldn't fetch location with name " + name);
        }

        return null;
    }

    @Override
    public void addAsFavorite(Location location) {
        // todo
        ((LocationRepositoryInterface)LocationRepository.cache).addAsFavorite(location);
    }


    @Override
    public void removeFavorite(Location location) {
        ((LocationRepositoryInterface)LocationRepository.cache).removeFavorite(location);
    }

    @Override
    public boolean isFavorite(Location location) {
        return ((LocationRepositoryInterface)LocationRepository.cache).isFavorite(location);
    }

    @Override
    public ArrayList<Location> getFavorites() {
        return ((LocationRepositoryInterface)LocationRepository.cache).getFavorites();
    }
}
