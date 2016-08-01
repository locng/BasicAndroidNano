package com.udacity.tourguide;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by nloc on 8/2/2016.
 */
public class AttractionPointsAdapter extends ArrayAdapter<AttractionPoints> {

    public AttractionPointsAdapter(Context context, int resource, List<AttractionPoints> objects) {
        super(context, resource, objects);
    }
    public AttractionPointsAdapter(Context context, List<AttractionPoints> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView != null){
        }

        return super.getView(position, convertView, parent);
    }
}
