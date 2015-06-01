package com.school.guidoschmitz.happyhours.repositories;

import com.school.guidoschmitz.happyhours.models.Location;

import java.util.ArrayList;

/**
 * Created by Liam Hubers on 29-5-2015.
 */
public class LocationRepository {
    private static LocationRepositoryInterface repository;
    private static LocationApiRepository api = new LocationApiRepository();
    public static LocationCacheRepository cache;

    public static void setConnectivity(boolean hasConnection) {
        repository = hasConnection ? api : cache;
    }

    public static Location get(int id) {
        return repository.get(id);
    }

    public static ArrayList<Location> all() {
        return repository.all();
    }

    public static void setLocations(ArrayList<Location> locations) {
        cache.setLocations(locations);
    }
}
