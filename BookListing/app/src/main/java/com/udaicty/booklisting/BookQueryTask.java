package com.udaicty.booklisting;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.udaicty.booklisting.Book;
import com.udaicty.booklisting.Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

class BookQueryTask extends AsyncTask<URL, Void, List<Book>> {
    private String LOG = BookQueryTask.class.getSimpleName();
    //String queryUrl = "";
    OnDataReadyListener listener;
    Context ctx;

    interface OnDataReadyListener {
        public void onDataReady(List<Book> objects);
    }

    public void registerListener(OnDataReadyListener ls){
        listener = ls;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Book> doInBackground(URL... urls) {
        URL url = urls[0];//createUrl(queryUrl);
        String jsonResponse="";
        try{
            jsonResponse = Utils.makeHttpRequest(url);
        }catch(IOException e){
        }

        List<Book> books = Utils.extractFeatureFromJson(jsonResponse);
        return books;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        listener.onDataReady(books);
    }
}