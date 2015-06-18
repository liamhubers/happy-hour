package com.school.guidoschmitz.happyhours.activities;

import android.content.Intent;
import android.net.Uri;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.adapters.EventsAdapter;
import com.school.guidoschmitz.happyhours.adapters.FavoritesAdapter;
import com.school.guidoschmitz.happyhours.models.Event;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.LocationRepository;
import com.school.guidoschmitz.happyhours.repositories.UserRepository;

public class LocationDetailActivity extends LocationDetailExtendActivity {

    private GoogleMap map;
    private ImageView image;
    private static final int ZOOM_LEVEL = 14;
    private boolean favorite = false;

    private static String FACEBOOK_SHARE_TITLE = "Hello Facebook";
    private static String FACEBOOK_SHARE_DESCRIPTION = "test";
    private static final Uri FACEBOOK_SHARE_URI = Uri.parse("http://developers.facebook.com/android");

    private static final String[] days = new String[]{ "Zondag", "Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        referredIntent = getIntent();
        location = (Location) referredIntent.getSerializableExtra("location");
        location.setFavorite(LocationRepository.isFavorite(location));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(location.getName());
        }

        this.setData();

        MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);
        map.getMap().setMyLocationEnabled(true);
        map.getMap().getUiSettings().setAllGesturesEnabled(false);
        map.getMap().getUiSettings().setMyLocationButtonEnabled(false);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(location.isFavorite()) {
            menu.getItem(0).setIcon(R.drawable.unfavorite);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng latLng = new LatLng(location.getLat(), location.getLon());

        map.addMarker(
            new MarkerOptions()
                .position(latLng)
                .title(location.getName())
        );

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL));
    }

    public void shareLocation(View v) {
        if(shareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(FACEBOOK_SHARE_TITLE)
                .setContentDescription(FACEBOOK_SHARE_DESCRIPTION)
                .setContentUrl(FACEBOOK_SHARE_URI)
                .build();
            shareDialog.show(linkContent);
        }
    }

    public void favorite(MenuItem item) {
        if(location.isFavorite()) {
            item.setIcon(R.drawable.favorite);
            UserRepository.removeFavorite(location);
            location.setFavorite(false);
        } else {
            item.setIcon(R.drawable.unfavorite);
            UserRepository.addFavorite(location);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    public void setData() {
        EventsAdapter adapter = new EventsAdapter(this, R.layout.activity_favorites_list_item, location.getEvents());
        ListView list = (ListView) findViewById(R.id.events);
        list.setDivider(null);
        list.setAdapter(adapter);

        View header = getLayoutInflater().inflate(R.layout.activity_detail_header, null);
        View footer = getLayoutInflater().inflate(R.layout.activity_detail_footer, null);

        if(AccessToken.getCurrentAccessToken() != null) {
            callbackManager = CallbackManager.Factory.create();
            shareDialog = new ShareDialog(this);
            setShareDialogRegister();
        } else {
            Button button = (Button) footer.findViewById(R.id.share_button);
            button.setVisibility(View.GONE);
        }

        list.addHeaderView(header);
        list.addFooterView(footer);

        TextView address = (TextView) findViewById(R.id.address_text);
        address.setText(location.getAddress());

        TextView description = (TextView) findViewById(R.id.description_text);
        description.setText(location.getDescription());
    }
}
