package com.school.guidoschmitz.happyhours.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.models.Location;

import java.util.ArrayList;

public class FavoritesAdapter extends ArrayAdapter<Location> {

    public FavoritesAdapter(Context context, int resource, ArrayList<Location> items) {
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

        Location location = getItem(position);

        TextView name = (TextView) v.findViewById(R.id.name);
        name.setText(location.getName());

        TextView address = (TextView) v.findViewById(R.id.address);
        address.setText(location.getAddress());

        ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        Log.i("thumbnail", location.getThumbnail()+"");
        thumbnail.setImageBitmap(location.getThumbnail());

        return v;
    }
}
