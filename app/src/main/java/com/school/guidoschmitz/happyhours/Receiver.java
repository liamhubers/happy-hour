package com.school.guidoschmitz.happyhours;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.school.guidoschmitz.happyhours.fragments.MainFragment;
import com.school.guidoschmitz.happyhours.repositories.LocationRepository;

import java.util.ArrayList;


public class Receiver extends BroadcastReceiver {

    private MainFragment fragment;
    private Activity activity;

    public Receiver(MainFragment fragment) {
        this.fragment = fragment;
        this.activity = fragment.getActivity();

        LocationRepository.createDatabase(this.activity);
        if(isOnline(this.activity)) {
            this.handler();
            try {
                this.activity.unregisterReceiver(this);
            } catch(Exception e) { }
        } else {
            this.activity.registerReceiver(this, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(this.isOnline(context)) {
            handler();
            context.unregisterReceiver(this);
        }
    }

    private void handler() {
        new LocationRepository().onOnline(this.fragment);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = manager.getActiveNetworkInfo();
        return (network != null && network.isConnected());
    }
}
