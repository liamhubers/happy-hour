package com.school.guidoschmitz.happyhours.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.guidoschmitz.happyhours.R;
import com.school.guidoschmitz.happyhours.models.NavItem;

import java.util.ArrayList;

/**
 * Created by thjvr on 08/06/15.
 */
public class NavAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavItem> navItems;

    public NavAdapter(Context context, ArrayList<NavItem> navItems) {
        this.context = context;
        this.navItems = navItems;
    }

    @Override
    public int getCount() {
        return navItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.nav_item, null);
        } else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
//        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText(navItems.get(position).getTitle());
//        subtitleView.setText(navItems.get(position).getSubtitle());
        iconView.setImageResource(navItems.get(position).getIcon());

        return view;
    }
}
