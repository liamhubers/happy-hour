package com.school.guidoschmitz.happyhours.activities;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.school.guidoschmitz.happyhours.R;


public class MapActivity extends ActionBarActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private final int INITIAL_ZOOM = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        map.setMyLocationEnabled(true);

        LatLng latLng1 = new LatLng(52.080182, 4.316461);
        LatLng latLng2 = new LatLng(52.081038, 4.315759);

        map.addMarker(new MarkerOptions()
                .position(latLng1)
                .title("Haagse Kluis"));

        map.addMarker(new MarkerOptions()
                .position(latLng2)
                .title("Danzig"));

        map.setOnMarkerClickListener(this);

        /* zoom to current location */
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null){
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()),
                    INITIAL_ZOOM
                )
            );
        }
        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            private boolean setLocation = false;
            @Override
            public void onMyLocationChange (Location location) {
                if(!setLocation) {
                    LatLng locaction = new LatLng(location.getLatitude(), location.getLongitude());
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(locaction, INITIAL_ZOOM));
                    setLocation = true;
                }
            }
        };
        map.setOnMyLocationChangeListener(myLocationChangeListener);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(this, LocationDetailActivity.class);
        startActivity(intent);
        return true;
    }
}
