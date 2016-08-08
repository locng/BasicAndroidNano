package com.udacity.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private final String LOG = MainActivity.class.getSimpleName();
    ListView newsListView;
    NewsAdapter adapter;
    private TextView emptyText;
    private String URL_GUARDIAN_START = "http://content.guardianapis.com/search?q=";
    private String URL_GUARDIAN_END = "&api-key=test&show-fields=trailText,thumbnail";
    private String searchString;
    View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        emptyText = (TextView) findViewById(R.id.placeholder);
        //Get reference to the loaderManager in order to interact with loaders
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);

        newsListView = (ListView) findViewById(R.id.list);
        newsListView.setEmptyView(emptyText);
        adapter = new NewsAdapter(this);

        newsListView.setAdapter(adapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentNews = adapter.getItem(i);
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri webUrl = Uri.parse(currentNews.getWebUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webUrl);

                startActivity(intent);
            }
        });
        loadingIndicator = findViewById(R.id.loading_bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Place an action bar item for searching.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getApplicationContext().getResources().getString(R.string.search_hint));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchString = createFullQueryString(query);
                //prevent request twice due to KEY_UP/KEY_DOWN
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                //prevent request twice due to KEY_UP/KEY_DOWN
                if (isNetworkConnected()) {
                    getLoaderManager().restartLoader(0, null, MainActivity.this);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, searchString);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {
        if (newses == null) {
         return;
        }
        emptyText.setText(R.string.no_data);
        loadingIndicator.setVisibility(View.GONE);
        adapter.clear();
        adapter.addAll(newses);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.d(LOG, "onLoadFinished");
        adapter.clear();
    }

    private String createFullQueryString(String keyword) {
        String ret = "";
        if (!TextUtils.isEmpty(keyword)) {
            StringBuilder builder = new StringBuilder(URL_GUARDIAN_START);
            builder.append(keyword).append(URL_GUARDIAN_END);
            ret = builder.toString();
        }
        return ret;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
