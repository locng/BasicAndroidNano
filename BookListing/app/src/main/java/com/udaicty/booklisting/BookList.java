package com.udaicty.booklisting;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
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
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(LOG,"Search for " + query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(searchItem);

        // Assumes current activity is the searchable activity
        //if not, replace getComponentName with SearchActivity.class
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false);// Do not iconify the widget, expand it by default
        //searchView.setSubmitButtonEnabled(true);// Enable "submit button"

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform the final search
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Text has changed, apply filtering
                return false;
            }
        });

        return true;
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
