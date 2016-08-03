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
public class AttractionPointsAdapter extends ArrayAdapter<AttractionPoint> {

    public AttractionPointsAdapter(Context context, int resource, ArrayList<AttractionPoint> objects) {
        super(context, resource, objects);
    }

    public AttractionPointsAdapter(Context context, List<AttractionPoint> objects) {
        super(context, 0, objects);
    }

    static class ViewHolder {
        ImageView coverPhoto;
        ImageView iconName;
        TextView textName;
        TextView textPlace;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        ViewHolder viewHolder;
        AttractionPoint point = getItem(position);

        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false);
            //cache view fields into field holder
            viewHolder = new ViewHolder();
            viewHolder.coverPhoto = (ImageView) listView.findViewById(R.id.photo);
            viewHolder.iconName = (ImageView) listView.findViewById(R.id.icon_name);
            viewHolder.textName = (TextView) listView.findViewById(R.id.text_name);
            viewHolder.textPlace = (TextView) listView.findViewById(R.id.text_place);
            // associate the holder with the view for later lookup
            listView.setTag(viewHolder);
        } else {
            // view already exists, get the holder instance from the view
            viewHolder = (ViewHolder) listView.getTag();
        }

        if (point.hasCoverPhoto()) {
            viewHolder.coverPhoto.setImageResource(point.getImageResId());
        } else {
            viewHolder.coverPhoto.setImageResource(R.raw.la_villa_french);
        }
        viewHolder.iconName.setImageResource(point.getLocationResId());
        viewHolder.textName.setText(point.getName());
        viewHolder.textPlace.setText(point.getAddressName());

        return listView;
    }
}
