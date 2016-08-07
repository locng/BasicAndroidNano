package com.udaicty.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by win8 on 8/6/2016.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context) {
        super(context, 0, 0);
    }

    static class ViewHolder {
        TextView count;
        TextView title;
        TextView authors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list = convertView;
        ViewHolder vh;
        Book book = getItem(position);

        if (list == null) {
            list = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            vh = new ViewHolder();
            vh.count = (TextView) list.findViewById(R.id.count);
            vh.title = (TextView) list.findViewById(R.id.title);
            vh.authors = (TextView) list.findViewById(R.id.authors);
            list.setTag(vh);
        } else {
            vh = (ViewHolder) list.getTag();
        }

        vh.count.setText(Integer.toString(position));
        vh.title.setText(book.getTittle());

        String authors = "";
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < book.getAuthor().size(); i++) {
            build.append(book.getAuthor().get(i)).append(", ");
        }
        //Delete the last ","
        build.deleteCharAt(build.length() - 2);
        authors = build.toString();
        vh.authors.setText(authors);

        return list;
    }
}