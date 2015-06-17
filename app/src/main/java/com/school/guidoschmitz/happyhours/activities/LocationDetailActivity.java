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
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.Receiver;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.location.LocationRepository;

public class LocationDetailActivity extends ActionBarReceiverActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private Intent referredIntent;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        referredIntent = getIntent();

        super.receiver = new Receiver(this, new LocationRepository());
        registerReceiver(super.receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        location = LocationRepository.getByName(referredIntent.getStringExtra("locationTitle"));

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        this.setData();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng latLng = new LatLng(location.getLat(), location.getLon());

        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(location.getName()));


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void addFavorite(View v) {
        //Intent i = new Intent(this, FavoriteActivity.class);
        //startActivity(i);
        Log.i("repo", LocationRepository.repository + "");
    }

    public void setData() {
        TextView name = (TextView) findViewById(R.id.name_text);
        name.setText(location.getName());

        TextView address = (TextView) findViewById(R.id.address_text);
        address.setText(location.getAddress());

        TextView description = (TextView) findViewById(R.id.description_text);
        description.setText(location.getDescription());

        LinearLayout actionLayout = (LinearLayout) findViewById(R.id.actions_layout);
        TextView actionText = new TextView(this);
        actionText.setText("Test");
        actionLayout.addView(actionText);
    }
}
