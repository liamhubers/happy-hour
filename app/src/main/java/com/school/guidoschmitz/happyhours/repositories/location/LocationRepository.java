package com.school.guidoschmitz.happyhours.repositories.location;

import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.Repository;

import java.util.ArrayList;

public class LocationRepository extends Repository {

    public LocationRepository() {
        api = new LocationApiRepository();
        cache = new LocationCacheRepository();
    }

    public static Location get(int id) {
        return ((LocationRepositoryInterface)repository).get(id);
    }

    public static ArrayList<Location> all() {
        return ((LocationRepositoryInterface)repository).all();
    }

    public static Location getByName(String name) {
        return ((LocationRepositoryInterface)repository).getByName(name);
    }
}
