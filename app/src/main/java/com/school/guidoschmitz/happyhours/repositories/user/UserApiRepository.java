package com.school.guidoschmitz.happyhours.repositories.user;

import android.text.Html;
import android.util.Log;

import com.school.guidoschmitz.happyhours.Api;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.models.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class UserApiRepository implements UserRepositoryInterface {

    @Override
    public void addFavorite(Location location) {
        // todo
    }

    @Override
    public ArrayList<Location> getFavorites(int userId) {
        ArrayList<Location> locations = new ArrayList<>();
        /*
        @guido -> api url /users/1/favorites maken
        try {
            String JSON = new Api().execute("http://happy-hours.guidoschmitz.nl/locations").get();
            JSONArray array = new JSONArray(JSON);

            for (int i = 0; i < array.length(); i++) {
                locations.add(parseJSON(new JSONObject(array.get(i).toString())));
            }
        } catch (Exception e) {
            Log.i("API", "Couldn't fetch location data");
        }

        UserCacheRepository.setLocations(locations);*/
        locations.add(new Location(Html.fromHtml("Club Seven").toString(), Html.fromHtml("Prinsegracht 14, 2512 GA Den Haag").toString(), R.drawable.bar1));
        locations.add(new Location(Html.fromHtml("Caf&eacute; Beurs").toString(), Html.fromHtml("Kruiskade 55, 3012 EE Rotterdam").toString(), R.drawable.bar2));
        locations.add(new Location(Html.fromHtml("De Drie Musketiers").toString(), Html.fromHtml("Dorpsstraat 27, Nootdorp").toString(), R.drawable.bar3));

        return locations;
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
        } catch (Exception e) {
            Log.i("JSON", "Failed to parse object to location");
        }

        return location;
    }
}
