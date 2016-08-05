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

    static String sample = "{\"totalItems\":537,\"items\":[{\"volumeInfo\": {\"title\":\"Beginning Android 4\",\"authors\":[\"Grant Allen\"]}}]}";


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
    static List<Book> extractFeatureFromJson(String baseJSON) {
        List<Book> books = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(baseJSON);
            JSONArray itemArray = baseJsonResponse.getJSONArray("items");

            for (int i=0;i<itemArray.length();i++) {
                // Extract out the first item
                JSONObject firstItem = itemArray.getJSONObject(i);
                JSONObject volumeInfo = firstItem.getJSONObject("volumeInfo");
                // Extract out the title, authors
                String title = volumeInfo.getString("title");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                // Just get one author for testing
                String firstAuthor = authors.getString(0);
                Book book = new Book(title,firstAuthor);
                books.add(book);
            }
            // If there are results in the item array
//            if (itemArray.length() > 0) {
//                // Extract out the first item
//                JSONObject firstItem = itemArray.getJSONObject(0);
//                JSONObject volumeInfo = firstItem.getJSONObject("volumeInfo");
//
//                // Extract out the title, authors
//                String title = volumeInfo.getString("title");
//                JSONArray authors = volumeInfo.getJSONArray("authors");
//                // Just get one author for testing
//                String firstAuthor = authors.getString(0);
//
//                // Create a new {@link Event} object
//                //return new Book(title, firstAuthor);
//            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    static String makeHttpRequest(URL url) throws IOException {
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
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }
}
