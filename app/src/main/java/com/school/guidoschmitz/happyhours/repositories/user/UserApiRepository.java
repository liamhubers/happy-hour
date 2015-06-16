package com.school.guidoschmitz.happyhours.repositories.user;

import android.text.Html;
import android.util.Log;

import com.school.guidoschmitz.happyhours.Api;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.location.LocationRepository;

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
    public void removeFavorite(Location location) {
        // todo
    }

    @Override
    public ArrayList<Location> getFavorites(int userId) {
        ArrayList<Location> locations = new ArrayList<>();

        try {
            String JSON = new Api().execute("http://happy-hours.guidoschmitz.nl/users/1/favorites").get();
            JSONArray array = new JSONArray(JSON);

            for (int i = 0; i < array.length(); i++) {
                locations.add(LocationRepository.parseJSON(new JSONObject(array.get(i).toString())));
            }
        } catch (Exception e) {
            Log.i("API", "Couldn't fetch favorites data");
        }

        UserCacheRepository.setFavorites(locations);
        /*locations.add(new Location(Html.fromHtml("Club Seven").toString(), Html.fromHtml("Prinsegracht 14, 2512 GA Den Haag").toString(), R.drawable.bar1));
        locations.add(new Location(Html.fromHtml("Caf&eacute; Beurs").toString(), Html.fromHtml("Kruiskade 55, 3012 EE Rotterdam").toString(), R.drawable.bar2));
        locations.add(new Location(Html.fromHtml("De Drie Musketiers").toString(), Html.fromHtml("Dorpsstraat 27, Nootdorp").toString(), R.drawable.bar3));*/

        return locations;
    }


}
