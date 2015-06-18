package com.school.guidoschmitz.happyhours;

import android.app.Application;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.LocationRepository;
import com.school.guidoschmitz.happyhours.repositories.UserRepository;

import junit.framework.TestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class LocationFavoriteTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        LocationRepository.createDatabase(getContext());
        UserRepository.createDatabase(getContext());
    }

    public void testFavoriteLocation() {
        Location location = new Location();
        location.setId(10);

        UserRepository.addFavorite(location);

        assertEquals(true, LocationRepository.isFavorite(location));
    }

    public void testUnfavoriteLocation() {
        Location location = new Location();
        location.setId(10);

        UserRepository.addFavorite(location);
        UserRepository.removeFavorite(location);

        assertEquals(false, LocationRepository.isFavorite(location));
    }
}