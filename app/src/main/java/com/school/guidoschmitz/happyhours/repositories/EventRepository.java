package com.school.guidoschmitz.happyhours.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.school.guidoschmitz.happyhours.database.DBContract;
import com.school.guidoschmitz.happyhours.models.Event;
import com.school.guidoschmitz.happyhours.models.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Liam Hubers on 17-6-2015.
 */
public class EventRepository extends CacheRepository {

    public static ArrayList<Event> all(Location location) {
        ArrayList<Event> events = new ArrayList<>();
        int dayOfWeek = new GregorianCalendar().get(Calendar.DAY_OF_WEEK) - 1;
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM " + DBContract.Event.TABLE + " WHERE " + DBContract.Event.LOCATION_ID + " = " + location.getId() +" ORDER BY (" + DBContract.Event.DAY_OF_WEEK + " = " + dayOfWeek + ") DESC, " + DBContract.Event.DAY_OF_WEEK, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                events.add(EventRepository.toEvent(cursor));

                cursor.moveToNext();
            }
        }

        return events;
    }

    public static void save(Event event) {
        getDatabase().insert(DBContract.Event.TABLE, "", toContentValues(event));
    }

    public static ArrayList<Event> toEvents(JSONArray JSON) {
        ArrayList<Event> events = new ArrayList<>();

        try {
            for (int i = 0; i < JSON.length(); i++) {
                JSONObject event = JSON.getJSONObject(i);
                Event model = new Event();
                model.setLocationId(event.getInt("location_id"));
                model.setDayOfWeek(event.getInt("day_of_week"));
                model.setDescription(event.getString("description"));
                model.setStartTime(event.getString("start_time"));
                model.setEndTime(event.getString("end_time"));
                events.add(model);
            }
        } catch(Exception e) { }

        return events;
    }

    public static Event toEvent(Cursor cursor) {
        Event event = new Event();

        event.setLocationId(cursor.getInt(cursor.getColumnIndex(DBContract.Event.LOCATION_ID)));
        event.setDayOfWeek(cursor.getInt(cursor.getColumnIndex(DBContract.Event.DAY_OF_WEEK)));
        event.setDescription(cursor.getString(cursor.getColumnIndex(DBContract.Location.DESCRIPTION)));
        event.setStartTime(cursor.getString(cursor.getColumnIndex(DBContract.Event.START_TIME)));
        event.setEndTime(cursor.getString(cursor.getColumnIndex(DBContract.Event.END_TIME)));

        return event;
    }

    public static ContentValues toContentValues(Event event) {
        ContentValues values = new ContentValues();

        values.put(DBContract.Event.LOCATION_ID, event.getLocationId());
        values.put(DBContract.Event.DAY_OF_WEEK, event.getDayOfWeek());
        values.put(DBContract.Event.DESCRIPTION, event.getDescription());
        values.put(DBContract.Event.START_TIME, event.getStartTime());
        values.put(DBContract.Event.END_TIME, event.getEndTime());

        return values;
    }

    public static void removeAll() {
        getDatabase().execSQL("DELETE FROM " + DBContract.Event.TABLE);
    }
}
