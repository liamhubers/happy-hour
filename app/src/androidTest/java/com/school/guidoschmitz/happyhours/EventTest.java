package com.school.guidoschmitz.happyhours;

import android.test.AndroidTestCase;

import com.school.guidoschmitz.happyhours.models.Event;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.EventRepository;
import com.school.guidoschmitz.happyhours.repositories.LocationRepository;
import com.school.guidoschmitz.happyhours.repositories.UserRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Liam Hubers on 18-6-2015.
 */
public class EventTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        LocationRepository.createDatabase(getContext());
        UserRepository.createDatabase(getContext());

        JSONArray array = new JSONArray("[{\"id\":\"1\",\"test\":\"1\",\"name\":\"Club Seven\",\"address\":\"Prinsegracht 14, 2512 GA Den Haag\",\"thumbnail\":\"http:\\/\\/liamhubers.nl\\/happy-hours\\/bar1.png\",\"description\":\"Gezellige club op het plein in Den Haag he\",\"lat\":\"52.070498\",\"lon\":\"4.300700\",\"created_at\":\"2015-05-31 19:43:55\",\"updated_at\":\"2015-05-31 19:43:55\",\"events\":[{\"id\":\"1\",\"location_id\":\"1\",\"day_of_week\":\"0\",\"description\":\"3 bier voor 2,-\",\"start_time\":\"21:00:00\",\"end_time\":\"00:00:00\",\"created_at\":\"2015-05-31 19:45:30\",\"updated_at\":\"2015-05-31 19:45:30\"}]},{\"id\":\"2\",\"test\":\"1\",\"name\":\"Club Vie\",\"address\":\"Maasboulevard 300, 3011 TX Rotterdam\",\"thumbnail\":\"http:\\/\\/liamhubers.nl\\/happy-hours\\/bar2.png\",\"description\":\"Mooie club in Rotterdam\",\"lat\":\"51.924420\",\"lon\":\"4.477733\",\"created_at\":\"2015-05-31 19:44:27\",\"updated_at\":\"2015-05-31 19:44:27\",\"events\":[{\"id\":\"4\",\"location_id\":\"2\",\"day_of_week\":\"4\",\"description\":\"2 bier voor de prijs van 1\",\"start_time\":\"15:00:00\",\"end_time\":\"16:00:00\",\"created_at\":\"-0001-11-30 00:00:00\",\"updated_at\":\"-0001-11-30 00:00:00\"}]},{\"id\":\"3\",\"test\":\"1\",\"name\":\"NEXT Leiden\",\"address\":\"Langebrug 6-A, 2311 TK Leiden\",\"thumbnail\":\"http:\\/\\/liamhubers.nl\\/happy-hours\\/bar3.png\",\"description\":\"Bij NEXT kun je iedere donderdag, vrijdag en zaterdag terecht voor een goed avondje stappen. Het maakt niet uit of je helemaal uit je dak wilt gaan of kiest voor een relaxed biertje met je vrienden. Bij NEXT kan het allemaal. Ook voor een sigaretje roken in de mooiste rookruimte van Leiden ben je in NEXT aan het juiste adres.\",\"lat\":\"52.159463\",\"lon\":\"4.486279\",\"created_at\":\"-0001-11-30 00:00:00\",\"updated_at\":\"-0001-11-30 00:00:00\",\"events\":[{\"id\":\"2\",\"location_id\":\"3\",\"day_of_week\":\"5\",\"description\":\"1 baco voor de helft van de prijs (3,-)\",\"start_time\":\"21:00:00\",\"end_time\":\"00:00:00\",\"created_at\":\"2015-05-31 19:45:30\",\"updated_at\":\"2015-05-31 19:45:30\"},{\"id\":\"3\",\"location_id\":\"3\",\"day_of_week\":\"1\",\"description\":\"10 bier voor 10,-\",\"start_time\":\"22:00:00\",\"end_time\":\"23:30:00\",\"created_at\":\"2015-05-31 19:45:30\",\"updated_at\":\"2015-05-31 19:45:30\"}]}]");

        LocationRepository.removeAll();
        EventRepository.removeAll();

        for (int i = 0; i < array.length(); i++) {
            LocationRepository.save(LocationRepository.toLocation(new JSONObject(array.get(i).toString())));
        }
    }

    public void testAll() {
        Location location = new Location();
        location.setId(3);

        ArrayList<Event> events = EventRepository.all(location);

        assertEquals(2, events.size());
    }
}
