package com.school.guidoschmitz.happyhours.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.models.RoundImage;
import com.school.guidoschmitz.happyhours.thumbnailDownloader;

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
        thumbnail.setImageDrawable(new RoundImage(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.location_thumbnail_default)));
        new thumbnailDownloader(thumbnail, location).execute();

        return v;
    }
}
