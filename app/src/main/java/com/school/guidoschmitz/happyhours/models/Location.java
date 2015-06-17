package com.school.guidoschmitz.happyhours.models;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.school.guidoschmitz.happyhours.repositories.location.LocationRepository;

import java.net.URL;

public class Location {
    private int id;
    private String name;
    private String description;
    private String address;
    private Bitmap thumbnail;
    private String thumbnailString;
    private double lat;
    private double lon;
    private ArrayList<Event> events;

    public Location() {}

    public Location(String name, String address, Bitmap thumbnail) {
        this.name = name;
        this.address = address;
        this.thumbnail = thumbnail;
    }

    public boolean isFavorite() {
        return LocationRepository.isFavorite(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnailString = thumbnail;
        try {
            Log.i("thumbnail", "download: "+thumbnail);
            URL url = new URL(thumbnail);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            this.thumbnail = bmp;
        } catch(Exception e) {

        }
    }

    public String getThumbnailString() {
        return this.thumbnailString;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
