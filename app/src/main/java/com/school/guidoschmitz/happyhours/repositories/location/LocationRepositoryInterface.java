package com.school.guidoschmitz.happyhours.repositories.location;

import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.RepositoryInterface;

import java.util.ArrayList;

interface LocationRepositoryInterface extends RepositoryInterface {
    ArrayList<Location> all();
    Location get(int id);
    Location getByName(String name);
}
