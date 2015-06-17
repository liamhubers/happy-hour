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
                locations.add(parseJSON(new JSONObject(array.get(i).toString())));
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
            return parseJSON(new JSONObject(JSON));
        } catch (Exception e) {
            Log.i("API", "Couldn't fetch location with id " + id);
        }

        return null;
    }

    public Location getByName(String name) {
        try {
            String JSON = new Api().execute("http://happy-hours.guidoschmitz.nl/locations?name=" + URLEncoder.encode(name, "UTF-8")).get();
            return parseJSON(new JSONObject(JSON));
        } catch (Exception e) {
            Log.i("API", "Couldn't fetch location with name " + name);
        }

        return null;
    }

    private Location parseJSON(JSONObject object) {
        Location location = new Location();

        try {
            location.setId(object.getInt("id"));
            location.setName(object.getString("name"));
            location.setDescription(object.getString("description"));
            location.setAddress(object.getString("address"));
            location.setLat(object.getDouble("lat"));
            location.setLon(object.getDouble("lon"));

            ArrayList<Event> eventModels = new ArrayList<>();
            JSONArray events = object.getJSONArray("events");
            for (int i = 0; i < events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                Event model = new Event();
                model.setDayOfWeek(event.getInt("day_of_week"));
                model.setDescription(event.getString("description"));
                model.setStartTime(event.getString("start_time"));
                model.setEndTime(event.getString("end_time"));
                eventModels.add(model);
            }

            location.setEvents(eventModels);
        } catch (Exception e) {
            Log.i("JSON", "Failed to parse object to location");
        }

        return location;
    }
}
