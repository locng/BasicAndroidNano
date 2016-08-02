package com.udacity.tourguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nloc on 8/2/2016.
 */
public class AttractionPointsAdapter extends ArrayAdapter<AttractionPoints> {

    public AttractionPointsAdapter(Context context, int resource, ArrayList<AttractionPoints> objects) {
        super(context, resource, objects);
    }

    public AttractionPointsAdapter(Context context, List<AttractionPoints> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.content_tour_guide, parent, false);
        }

        AttractionPoints point = getItem(position);

        ImageView iconName = (ImageView) listView.findViewById(R.id.icon_name);
        iconName.setImageResource(point.getLocationResId());

        TextView textView = (TextView) listView.findViewById(R.id.text_name);
        textView.setText(point.getName());

        ImageView iconPlace = (ImageView) listView.findViewById(R.id.icon_place);
        TextView textPlace = (TextView) listView.findViewById(R.id.text_place);
        textPlace.setText(point.getAddressName());

        return listView;
    }
}
