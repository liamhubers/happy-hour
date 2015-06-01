package com.school.guidoschmitz.happyhours.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.school.guidoschmitz.happyhours.repositories.LocationCacheRepository;
import com.school.guidoschmitz.happyhours.repositories.LocationRepository;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.Receiver;
import com.school.guidoschmitz.happyhours.models.Location;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(
                new Receiver(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION
                )
        );

        LocationRepository.cache = new LocationCacheRepository(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void all(View view) {
        ArrayList<Location> locations = LocationRepository.all();
        Log.i("all", locations + "");
    }

    public void single(View view) {
        Location location = LocationRepository.get(2);
        Log.i("single", location + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng latLng1 = new LatLng(52.080182, 4.316461);
        LatLng latLng2 = new LatLng(52.081038, 4.315759);

        map.addMarker(new MarkerOptions()
                .position(latLng1)
                .title("Haagse Kluis"));

        map.addMarker(new MarkerOptions()
                .position(latLng2)
                .title("Danzig"));

        map.setOnMarkerClickListener(this);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(this, LocationDetailActivity.class);
        startActivity(intent);
        return true;
    }
}
