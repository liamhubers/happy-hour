package com.school.guidoschmitz.happyhours.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.activities.LocationDetailActivity;
import com.school.guidoschmitz.happyhours.adapters.FavoritesAdapter;
import com.school.guidoschmitz.happyhours.models.Location;

public class FavoritesFragment extends ListFragment {

    Location[] favorites = new Location[]{
            new Location(Html.fromHtml("Club Seven").toString(), Html.fromHtml("Prinsegracht 14, 2512 GA Den Haag").toString(), R.drawable.bar1),
            new Location(Html.fromHtml("Caf&eacute; Beurs").toString(), Html.fromHtml("Kruiskade 55, 3012 EE Rotterdam").toString(), R.drawable.bar2),
            new Location(Html.fromHtml("De Drie Musketiers").toString(), Html.fromHtml("Dorpsstraat 27, Nootdorp").toString(), R.drawable.bar3)
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FavoritesAdapter adapter = new FavoritesAdapter(inflater.getContext(), R.layout.activity_favorites_list_item, favorites);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity().getBaseContext(), LocationDetailActivity.class);
        startActivity(i);
    }
}
