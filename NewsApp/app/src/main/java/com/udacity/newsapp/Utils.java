package com.udacity.newsapp;

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
    private static String RESPONSE = "response";
    private static String WEB_TITLE = "webTitle";
    private static String SECTION_NAME = "sectionName";
    private static String PUBLICATION_DATE = "webPublicationDate";
    private static String WEB_URL = "webUrl";

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
     * Return an {@link News} object by parsing out information
     * about the first earthquake from the input string.
     */
    public static List<News> extractRepsonseFromJson(String baseJSON) {
        List<News> NewsCollection = new ArrayList<>();
        News news;
        try {
            JSONObject baseJsonResponse = new JSONObject(baseJSON);
            JSONObject response = baseJsonResponse.getJSONObject(RESPONSE);

            JSONArray resultArray = response.getJSONArray("results");

            if (resultArray.length() > 0){

            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject result = resultArray.getJSONObject(i);
                String sectionName = result.getString(SECTION_NAME);
                String webTitle = result.getString(WEB_TITLE);
                String publicationDate = result.getString(PUBLICATION_DATE);
                String webUrl = result.getString(WEB_URL);

                news = new News(webTitle,sectionName,webUrl,publicationDate);
                NewsCollection.add(news);
            }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }
        return NewsCollection;
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
