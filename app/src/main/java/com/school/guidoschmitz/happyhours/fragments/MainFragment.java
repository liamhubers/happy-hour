package com.school.guidoschmitz.happyhours.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.Receiver;
import com.school.guidoschmitz.happyhours.activities.LocationDetailActivity;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.LocationRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class MainFragment extends MapViewFragment {

    private GoogleMap map;
    private HashMap<Marker, Location> markerLocations = new HashMap<>();
    private LatLngBounds bounds;
    private static final int ZOOM_LEVEL = 12;
    private static final int BOUNDS_PADDING = 125;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getActivity().getBaseContext(), LocationDetailActivity.class);
                intent.putExtra("location", markerLocations.get(marker));
                startActivity(intent);

                return true;
            }
        });

        new Receiver(this);
        this.setData();
        this.setCurrentPosition();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        MapView mapView = (MapView) getActivity().findViewById(R.id.map_view);
        Log.i("size", mapView.getMeasuredWidth() + " " + mapView.getMeasuredHeight());
        super.onActivityCreated(savedInstanceState);
    }

    public void setData() {
        ArrayList<Location> locations = LocationRepository.all();

        map.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Location location : locations) {
            LatLng latLng = new LatLng(location.getLat(), location.getLon());
            Marker marker = map.addMarker(
                new MarkerOptions()
                    .position(latLng)
                    .title(location.getName())
            );
            builder.include(latLng);
            markerLocations.put(marker, location);
        }
        bounds = builder.build();
    }

    public void setCurrentPosition() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        android.location.Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));
        if (location != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), ZOOM_LEVEL));
        } else {
            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 250));
                }
            });
        }

        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            private boolean set = false;

            @Override
            public void onMyLocationChange(android.location.Location location) {
                if (!set) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), ZOOM_LEVEL));
                    set = true;
                }
            }
        });
    }
}
