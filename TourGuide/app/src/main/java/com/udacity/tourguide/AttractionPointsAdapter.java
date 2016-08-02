package com.udacity.tourguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_category, parent, false);
        }

        AttractionPoints point = getItem(position);
        TextView textView = (TextView)listView.findViewById(R.id.text_cat);
        textView.setText(point.getCategoryAsString(position+1));

        return listView;
    }
}
