package com.udaicty.booklisting;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookList extends AppCompatActivity {
    String LOG = BookList.class.getSimpleName();
    private String QUERY_URL_1 = "https://www.googleapis.com/books/v1/volumes?q=";
    private String QUERY_URL_2 = "&maxResults=20";
    BookQueryTask bookQueryTask;
    ListView bookListView;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        bookListView = (ListView)findViewById(R.id.list);
        adapter = new BookAdapter(this, R.layout.list_item);

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
                Log.d(LOG,"===="  + query);
                final String searchString = createQuery(query);
                URL url = createUrl(searchString);
                //prevent request twice due to KEY_UP/KEY_DOWN
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                //prevent request twice due to KEY_UP/KEY_DOWN
                bookQueryTask = new BookQueryTask();
                bookQueryTask.execute(url);
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
    private String createQuery(String keyword){
        String ret = "";
        if(!TextUtils.isEmpty(keyword)){
        StringBuilder builder = new StringBuilder(QUERY_URL_1);
        builder.append(keyword).append(QUERY_URL_2);
        ret = builder.toString();
        }
        Log.d(LOG,"createQuery() " + ret);
        return ret;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    class BookQueryTask extends AsyncTask<URL, Void, List<Book>> {
        private String LOG = BookQueryTask.class.getSimpleName();

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
            for (int i=0;i<books.size();i++){
                Book book = books.get(i);
                Log.d("onPostExecute", "pos:" + i + ", " + book.getTittle() + ", " + book.getAuthor());
            }
            adapter.clear();
            adapter.addAll(books);
            bookListView.setAdapter(adapter);
        }
    }
}
