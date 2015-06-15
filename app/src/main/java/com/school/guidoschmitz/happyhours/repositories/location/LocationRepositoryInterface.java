package com.school.guidoschmitz.happyhours.repositories.location;

import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.RepositoryInterface;

import java.util.ArrayList;

public interface LocationRepositoryInterface extends RepositoryInterface {
    ArrayList<Location> all();
    ArrayList<Location> getFavorites();
    Location get(int id);
    Location getByName(String name);
    void addAsFavorite(Location location);
    void removeFavorite(Location location);
    boolean isFavorite(Location location);
}
