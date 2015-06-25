package com.school.guidoschmitz.happyhours.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.models.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Liam Hubers on 18-6-2015.
 */
public class EventsAdapter extends ArrayAdapter<Event> {

    public static final String[] DAYS = new String[] { "Zondag", "Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag" };

    public EventsAdapter(Context context, int resource, ArrayList<Event> events) {
        super(context, resource, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int dayOfWeek = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);

        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_detail_list_item, null);
        }

        Event event = getItem(position);

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView description = (TextView) v.findViewById(R.id.description);
        TextView subdescription = (TextView) v.findViewById(R.id.subdescription);
        Button share = (Button) v.findViewById(R.id.share_button);

        int padding = v.getResources().getDimensionPixelSize(R.dimen.detail_list_item_today_padding);
        int paddingSmall = v.getResources().getDimensionPixelSize(R.dimen.detail_list_item_today_padding_small);

        if(event.getDayOfWeek() == dayOfWeek - 1) {
            v.setBackgroundColor(getColor(R.attr.colorPrimaryDark, v));
            v.setPadding(padding, padding, padding, (int) (padding * 1.35));

            title.setText("Actie van vandaag");
            title.setTextColor(getColor(R.attr.colorPrimary, v));

            description.setText(event.getDescription());
            description.setTextColor(Color.WHITE);

            subdescription.setText(event.getStartTime().substring(0, 5) + " - " + event.getEndTime().substring(0, 5));
            subdescription.setTextColor(Color.WHITE);
            subdescription.setVisibility(View.VISIBLE);

            if (AccessToken.getCurrentAccessToken() != null) {
                share.setVisibility(View.VISIBLE);
            }
        } else {
            v.setBackgroundColor(Color.WHITE);
            v.setPadding(padding, paddingSmall, padding, (int) (paddingSmall * 1.35));

            title.setText(event.getDescription());
            title.setTextColor(Color.parseColor("#111111"));

            description.setText(DAYS[event.getDayOfWeek()] + " " + event.getStartTime().substring(0, 5) + " - " + event.getEndTime().substring(0, 5));
            description.setTextColor(Color.parseColor("#111111"));

            subdescription.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
        }

        return v;
    }

    private int getColor(int color, View v) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = v.getContext().getTheme();
        theme.resolveAttribute(color, typedValue, true);
        return typedValue.data;
    }
}
