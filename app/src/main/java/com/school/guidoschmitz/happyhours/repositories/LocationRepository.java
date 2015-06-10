package com.school.guidoschmitz.happyhours.repositories;

import com.school.guidoschmitz.happyhours.models.Location;

import java.util.ArrayList;

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

    public static Location getByName(String name) {
        return repository.getByName(name);
    }

    public static void setLocations(ArrayList<Location> locations) {
        cache.setLocations(locations);
    }
}
