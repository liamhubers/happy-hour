package com.school.guidoschmitz.happyhours.repositories.user;

import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.Repository;

import java.util.ArrayList;

public class UserRepository extends Repository {

    public UserRepository() {
        super.api = new UserApiRepository();
        super.cache = new UserCacheRepository();
    }

    public static ArrayList<Location> getFavorites() {
        int userId = 1;
        return ((UserRepositoryInterface)repository).getFavorites(userId);
    }

    public static void addFavorite(Location location) {
        ((UserRepositoryInterface)repository).addFavorite(location);
    }
}
