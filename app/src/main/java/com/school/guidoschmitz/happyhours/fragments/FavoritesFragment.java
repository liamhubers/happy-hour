package com.school.guidoschmitz.happyhours.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.activities.LocationDetailActivity;
import com.school.guidoschmitz.happyhours.adapters.FavoritesAdapter;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.repositories.location.LocationRepository;
import com.school.guidoschmitz.happyhours.repositories.user.UserRepository;

import java.util.ArrayList;

public class FavoritesFragment extends ListFragment {

    ArrayList<Location> favorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favorites = UserRepository.getFavorites();

        FavoritesAdapter adapter = new FavoritesAdapter(inflater.getContext(), R.layout.activity_favorites_list_item, favorites);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity().getBaseContext(), LocationDetailActivity.class);
        intent.putExtra("location", favorites.get(position));
        startActivity(intent);
    }
}
