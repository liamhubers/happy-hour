package com.school.guidoschmitz.happyhours;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.school.guidoschmitz.happyhours.repositories.Repository;

import java.util.ArrayList;


public class Receiver extends BroadcastReceiver {
    ArrayList<Repository> repositories;

    public Receiver(Context context, ArrayList<Repository> repositories) {
        this.repositories = repositories;

        for(Repository repository : repositories) {
            repository.cache.createDatabase(context);
            repository.setConnectivity(isOnline(context));
        }
    }

    public Receiver(Context context, Repository repository) {
        this.repositories = new ArrayList<>();
        this.repositories.add(repository);

        repository.cache.createDatabase(context);
        repository.setConnectivity(isOnline(context));
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        for(Repository repository : repositories) {
            repository.setConnectivity(isOnline(context));
        }
        context.unregisterReceiver(this);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
