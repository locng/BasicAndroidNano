package com.udaicty.booklisting;

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
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookList extends AppCompatActivity implements BookQueryTask.OnDataReadyListener{
    String LOG = BookList.class.getSimpleName();
    private String QUERY_URL_1 = "https://www.googleapis.com/books/v1/volumes?q=";
    private String QUERY_URL_2 = "&maxResults=5";
    BookQueryTask bookQueryTask;
    ListView bookListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        bookListView = (ListView)findViewById(R.id.list);

        bookQueryTask = new BookQueryTask();
        bookQueryTask.registerListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getApplicationContext().getResources().getString(R.string.search_hint));

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

    @Override
    public void onDataReady(List<Book> books) {
        //ArrayList<Book> books = (ArrayList<Book>) objects;
          for (int i=0;i<books.size();i++){
         Book book = books.get(i);
        Log.d(LOG,"onDataReady: "+ i + book.getTittle() + ", "+ book.getAuthor());
        }
        BookAdapter bookAdapter = new BookAdapter(this, books);
        bookListView.setAdapter(bookAdapter);
    }
}
