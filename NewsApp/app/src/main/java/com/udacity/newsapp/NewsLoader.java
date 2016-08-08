package com.udacity.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by nloc on 8/8/2016.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private final String LOG = this.getClass().getSimpleName();

    /**Query URL */
    private String mUrl;

    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    public List<News> loadInBackground() {
        if(mUrl == null){
            return null;
        }
        //Perform the network request
        URL url = createUrl(mUrl);
        String jsonResponse = "";
        try {
            jsonResponse = Utils.makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG, "Failed to request data", e);
            return null;
        }

        List<News> newsCollection = Utils.extractRepsonseFromJson(jsonResponse);
        return newsCollection;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }
}
