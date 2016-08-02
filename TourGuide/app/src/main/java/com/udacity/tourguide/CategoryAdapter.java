package com.udacity.tourguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nloc on 8/2/2016.
 */
public class CategoryAdapter extends ArrayAdapter<AttractionPoints> {
    public CategoryAdapter(Context context, int resource, List<AttractionPoints> objects) {
        super(context, resource, objects);
    }

    public CategoryAdapter(Context context, List<AttractionPoints> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_category, parent, false);
        }

        AttractionPoints point = getItem(position);
        ImageView icon = (ImageView) listView.findViewById(R.id.icon_cat);
        icon.setImageResource(point.getLocationResId());
        icon.setAlpha(0.75f);
        TextView textView = (TextView) listView.findViewById(R.id.text_cat);
        textView.setText(point.getCategoryAsString(position + 1));

        return listView;
    }
}
