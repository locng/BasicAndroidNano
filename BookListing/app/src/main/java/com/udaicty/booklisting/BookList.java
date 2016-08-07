package com.udaicty.booklisting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookList extends AppCompatActivity {
    private final String LOG = BookList.class.getSimpleName();
    private String QUERY_URL_1 = "https://www.googleapis.com/books/v1/volumes?q=";
    private String QUERY_URL_2 = "&maxResults=40";
    ListView bookListView;
    BookAdapter adapter;
    TextView tx;
    int tx_state;
    List<Book> savedBooks;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (savedBooks != null) {
            outState.putParcelableArrayList("bookList", (ArrayList) savedBooks);
        }
        outState.putInt("TEXT_STATE", tx_state);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("bookList")) {
            savedBooks = (List) savedInstanceState.getParcelableArrayList("bookList");
            tx_state = savedInstanceState.getInt("TEXT_STATE");
            tx.setVisibility(tx_state);
            adapter.addAll(savedBooks);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        tx = (TextView) findViewById(R.id.placeholder);

        bookListView = (ListView) findViewById(R.id.list);
        adapter = new BookAdapter(this);

        bookListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getApplicationContext().getResources().getString(R.string.search_hint));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                final String searchString = createQuery(query);
                URL url = createUrl(searchString);
                //prevent request twice due to KEY_UP/KEY_DOWN
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                //prevent request twice due to KEY_UP/KEY_DOWN
                new BookQueryTask().execute(url);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
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

    private String createQuery(String keyword) {
        String ret = "";
        if (!TextUtils.isEmpty(keyword)) {
            StringBuilder builder = new StringBuilder(QUERY_URL_1);
            builder.append(keyword).append(QUERY_URL_2);
            ret = builder.toString();
        }
        return ret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class BookQueryTask extends AsyncTask<URL, Void, List<Book>> {
        private String LOG = BookQueryTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            tx_state = View.VISIBLE;
            tx.setVisibility(tx_state);
            tx.setText(R.string.loading);
            super.onPreExecute();
        }

        @Override
        protected List<Book> doInBackground(URL... urls) {
            URL url = urls[0];
            String jsonResponse = "";
            try {
                jsonResponse = Utils.makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG, "Failed to request data", e);
                return null;
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
            if (books != null) {
                savedBooks = books;
                tx_state = View.GONE;
                tx.setVisibility(tx_state);

                adapter.clear();
                adapter.addAll(savedBooks);
            } else {
                tx_state = View.VISIBLE;
                adapter.clear();
                tx.setVisibility(tx_state);
                tx.setText(R.string.no_data);
            }
        }
    }
}
