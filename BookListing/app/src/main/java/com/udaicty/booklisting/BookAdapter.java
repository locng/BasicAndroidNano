package com.udaicty.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by win8 on 8/6/2016.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list = convertView;
        if (list == null){
            list = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            Book book = getItem(position);

            TextView count = (TextView)list.findViewById(R.id.count);
            count.setText(Integer.toString(position));

            TextView tittle = (TextView)list.findViewById(R.id.title);
            tittle.setText(book.getTittle());

            TextView author = (TextView)list.findViewById(R.id.authors);
            author.setText(book.getAuthor());
        }
        return list;
    }
}