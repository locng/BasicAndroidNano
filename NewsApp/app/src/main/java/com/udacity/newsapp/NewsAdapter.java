package com.udacity.newsapp;

import android.content.Context;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.newsapp.News;
import com.udacity.newsapp.R;

/**
 * Created by win8 on 8/6/2016.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context) {
        super(context, 0, 0);
    }

    static class ViewHolder {
        TextView count;
        TextView title;
        TextView section;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list = convertView;
        ViewHolder vh;
        News news = getItem(position);

        if (list == null) {
            list = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            vh = new ViewHolder();
            vh.count = (TextView) list.findViewById(R.id.count);
            vh.title = (TextView) list.findViewById(R.id.title);
            vh.section = (TextView) list.findViewById(R.id.section);
            list.setTag(vh);
        } else {
            vh = (ViewHolder) list.getTag();
        }

        vh.count.setText(Integer.toString(position));
        vh.title.setText(news.getTitle());

        String section = news.getSectionName();
        String date = news.getPublicationDate();
        date = date.substring(0,10);

        vh.section.setText(section + ", " + date);

        return list;
    }
}