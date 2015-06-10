package com.school.guidoschmitz.happyhours.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.Receiver;
import com.school.guidoschmitz.happyhours.activities.LocationDetailActivity;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.LocationCacheRepository;
import com.school.guidoschmitz.happyhours.repositories.LocationRepository;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private MapView mapView;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        getActivity().registerReceiver(new Receiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        LocationRepository.cache = new LocationCacheRepository(getActivity());
        LocationRepository.setConnectivity(true);

        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        ArrayList<Location> locations = LocationRepository.all();

        for (Location location : locations) {
            LatLng latLng = new LatLng(location.getLat(), location.getLon());
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(location.getName()));
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getActivity().getBaseContext(), LocationDetailActivity.class);
                startActivity(intent);
                return true;
            }
        });

        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));

        return view;
    }

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
