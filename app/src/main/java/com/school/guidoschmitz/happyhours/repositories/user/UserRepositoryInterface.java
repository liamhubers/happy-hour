package com.school.guidoschmitz.happyhours.repositories.user;

import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.RepositoryInterface;

import java.util.ArrayList;

public interface UserRepositoryInterface extends RepositoryInterface {
    void addFavorite(Location location);
    void removeFavorite(Location location);
    ArrayList<Location> getFavorites(int userId);
}
