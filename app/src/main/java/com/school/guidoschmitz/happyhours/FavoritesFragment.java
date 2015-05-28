package com.school.guidoschmitz.happyhours;

import android.app.ListFragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Liam Hubers on 26-5-2015.
 */
public class FavoritesFragment extends ListFragment {

    Bar[] favorites = new Bar[] {
            new Bar(Html.fromHtml("Club Seven"), Html.fromHtml("Prinsegracht 14, 2512 GA Den Haag"), R.drawable.bar1),
            new Bar(Html.fromHtml("Caf&eacute; Beurs"), Html.fromHtml("Kruiskade 55, 3012 EE Rotterdam"), R.drawable.bar2),
            new Bar(Html.fromHtml("De Drie Musketiers"), Html.fromHtml("Dorpsstraat 27, Nootdorp"), R.drawable.bar3)
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FavoritesAdapter adapter = new FavoritesAdapter(inflater.getContext(), R.layout.activity_favorites_list_item, favorites);
        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Log.i("", "test"+position);
    }
}
