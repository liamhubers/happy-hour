package com.school.guidoschmitz.happyhours.repositories;

import com.school.guidoschmitz.happyhours.models.Location;

import java.util.ArrayList;

/**
 * Created by Liam Hubers on 29-5-2015.
 */
interface LocationRepositoryInterface {
    ArrayList<Location> all();
    Location get(int id);
}
