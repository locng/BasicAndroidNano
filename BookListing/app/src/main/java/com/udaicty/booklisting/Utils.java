package com.udaicty.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by win8 on 8/5/2016.
 */
public class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link Book} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    public static List<Book> extractFeatureFromJson(String baseJSON) {
        List<Book> books = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(baseJSON);
            int itemCount = baseJsonResponse.getInt("totalItems");
            if (itemCount == 0) {
                return null;
            }
            JSONArray itemArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < itemArray.length(); i++) {
                // Extract out the first item
                JSONObject firstItem = itemArray.getJSONObject(i);
                JSONObject volumeInfo = firstItem.getJSONObject("volumeInfo");
                // Extract out the title, authors
                String title = volumeInfo.getString("title");
                ArrayList<String> authors = new ArrayList<>();
                if (volumeInfo.has("authors")) {
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    if (authorsArray.length() > 0) { // Make sure author JSON have value
                        for (int j = 0; j < authorsArray.length(); j++) {
                            authors.add(authorsArray.getString(j));
                        }
                    }
                }
                Book book = new Book(title, authors);
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }
        return books;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                jsonResponse = "";
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
}
