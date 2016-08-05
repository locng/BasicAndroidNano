package com.udaicty.booklisting;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookList extends AppCompatActivity {
    String LOG = BookList.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //getSupportActionBar().hide();


        List<Book> books = Utils.extractFeatureFromJson(Utils.sample);
        //Log.d(LOG,book.getTittle() + ", "+ book.getAuthor());
        BookQueryTask bookQueryTask = new BookQueryTask();
        bookQueryTask.execute();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    private String SAMPLE_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=40";
    private class BookQueryTask extends AsyncTask<URL, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(URL... urls) {
            URL url = createUrl(SAMPLE_URL);
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
            for (int i=0;i<books.size();i++){
                Book book = books.get(i);
            Log.d(LOG,"onPostExecute: "+ i + book.getTittle() + ", "+ book.getAuthor());
            }
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
