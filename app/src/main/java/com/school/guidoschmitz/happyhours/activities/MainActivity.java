package com.school.guidoschmitz.happyhours.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.Receiver;
import com.school.guidoschmitz.happyhours.adapters.NavAdapter;
import com.school.guidoschmitz.happyhours.fragments.FavoritesFragment;
import com.school.guidoschmitz.happyhours.fragments.MainFragment;
import com.school.guidoschmitz.happyhours.models.NavItem;
import com.school.guidoschmitz.happyhours.models.RoundImage;
import com.school.guidoschmitz.happyhours.repositories.LocationCacheRepository;
import com.school.guidoschmitz.happyhours.repositories.LocationRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends ActionBarActivity
{
    private ArrayList<NavItem> navItems;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        setUserProfile();

        navItems = new ArrayList<>();
        navItems.add(new NavItem("Happy Hours", "", R.drawable.home));
        navItems.add(new NavItem("Favorites", "", R.drawable.heart));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.nav_list);

        // Set the adapter for the list view
        drawerList.setAdapter(new NavAdapter(this, navItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_menu, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(getTitle());
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getTitle());
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        placeStartFragment();
        setLogoutListener();
        //setClickSettings();
    }

//    private void setClickSettings() {
//        ImageView settings = (ImageView)findViewById(R.id.settings);
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    private void setUserProfile() {
        Intent intent = getIntent();

        TextView username = (TextView) findViewById(R.id.username);
        String extraUsername = intent.getStringExtra(LoginActivity.EXTRA_USERNAME);
        String extraId = intent.getStringExtra(LoginActivity.EXTRA_PROFILE_ID);

        Log.v("LoginActivity", extraId + " | " + extraUsername);

        new DownloadProfilePictureTask().execute(extraId);

        username.setText(extraUsername);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void placeStartFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame, new MainFragment()).commit();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Insert the fragment by replacing any existing fragment
        switch (position) {
            case 0:
                fragment = new MainFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.content_frame, fragment).commit();
                break;
            case 1:
                fragment = new FavoritesFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.content_frame, fragment).commit();
                break;
        }

        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        setTitle(navItems.get(position).getTitle());
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void setLogoutListener() {
        Button logout = (Button)findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private Bitmap getFacebookPicture(String userID) {
        Bitmap bitmap = null;
        try {
            URL imageUrl = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
            bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void setRoundImage(Bitmap image) {
        // Round the profile image
        ImageView profileView = (ImageView) findViewById(R.id.avatar);
        Bitmap bitmap = image;
        RoundImage roundImage = new RoundImage(bitmap);
        profileView.setImageDrawable(roundImage);
    }

    private class DownloadProfilePictureTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String[] params) {
            return getFacebookPicture(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            setRoundImage(result);
        }
    }
}
