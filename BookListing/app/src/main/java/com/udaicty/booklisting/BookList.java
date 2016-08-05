package com.udaicty.booklisting;

import android.os.AsyncTask;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BookList extends AppCompatActivity {
    String LOG = BookList.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Book book = Utils.extractFeatureFromJson(Utils.sample);
        //Log.d(LOG,book.getTittle() + ", "+ book.getAuthor());
        BookQueryTask bookQueryTask = new BookQueryTask();
        bookQueryTask.execute();

    }

    private String SAMPLE_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";
    private class BookQueryTask extends AsyncTask<URL, Void, Book> {

        @Override
        protected Book doInBackground(URL... urls) {
            URL url = createUrl(SAMPLE_URL);
            String jsonResponse="";
            try{
                jsonResponse = Utils.makeHttpRequest(url);
            }catch(IOException e){
            }

            Book book = Utils.extractFeatureFromJson(jsonResponse);
            return book;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Book book) {
            Log.d(LOG,"onPostExecute"+ book.getTittle() + ", "+ book.getAuthor());
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
}
