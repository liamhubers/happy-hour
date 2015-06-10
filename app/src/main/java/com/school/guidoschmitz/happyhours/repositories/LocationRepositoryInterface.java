package com.school.guidoschmitz.happyhours.repositories;

import com.school.guidoschmitz.happyhours.models.Location;

import java.util.ArrayList;

interface LocationRepositoryInterface {
    ArrayList<Location> all();
    Location get(int id);
    Location getByName(String name);
}
