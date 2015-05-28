package com.school.guidoschmitz.happyhours;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Liam Hubers on 27-5-2015.
 */
public class FavoritesAdapter extends ArrayAdapter<Bar> {

    public FavoritesAdapter(Context context, int resource, Bar[] items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_favorites_list_item, null);
        }

        Bar bar = getItem(position);

        TextView name = (TextView) v.findViewById(R.id.name);
        name.setText(bar.getName());

        TextView address = (TextView) v.findViewById(R.id.address);
        address.setText(bar.getAddress());

        ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        thumbnail.setImageResource(bar.getThumbnail());

        return v;
    }
}
