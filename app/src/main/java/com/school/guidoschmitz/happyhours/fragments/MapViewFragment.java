package com.school.guidoschmitz.happyhours.fragments;

import android.app.Fragment;

import com.google.android.gms.maps.MapView;

/**
 * Created by Liam Hubers on 16-6-2015.
 */
public class MapViewFragment extends Fragment {

    protected MapView mapView;

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
