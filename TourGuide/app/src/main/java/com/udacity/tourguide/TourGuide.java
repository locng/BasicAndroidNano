package com.udacity.tourguide;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TourGuide extends AppCompatActivity
        implements AdapterView.OnItemClickListener, AttractionFragment.OnCategorySelectedListener {

    ArrayList<AttractionPoint> points;
    int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Create 4 category
        points = new ArrayList<>();

        points.add(createPoint(AttractionPoint.RESTAURANT));
        points.add(createPoint(AttractionPoint.HOTEL));
        points.add(createPoint(AttractionPoint.SHOPPING));
        points.add(createPoint(AttractionPoint.SIGHT_SEEING));

        CategoryAdapter pointsAdapter = new CategoryAdapter(getApplication(), points);

        ListView navigationView = (ListView) findViewById(R.id.nav_view);
        navigationView.setAdapter(pointsAdapter);
        navigationView.setOnItemClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private AttractionPoint createPoint(int category) {
        Resources res = getResources();
        if (category == AttractionPoint.RESTAURANT) {
            String str[] = res.getStringArray(R.array.restaurant);
            return new AttractionPoint(getApplicationContext(), category, str[1], str[2], Double.valueOf(str[3]), Double.valueOf(str[4]),
                    R.drawable.ic_restaurant, 0);
        } else if (category == AttractionPoint.HOTEL) {
            String str[] = res.getStringArray(R.array.hotel);
            return new AttractionPoint(getApplicationContext(), category, str[1], str[2], Double.valueOf(str[3]), Double.valueOf(str[4]),
                    R.drawable.ic_hotel, 0);
        } else if (category == AttractionPoint.SHOPPING) {
            String str[] = res.getStringArray(R.array.shopping);
            return new AttractionPoint(getApplicationContext(), category, str[1], str[2], Double.valueOf(str[3]), Double.valueOf(str[4]),
                    R.drawable.ic_shopping, 0);
        } else {
            String str[] = res.getStringArray(R.array.sightseeing);
            return new AttractionPoint(getApplicationContext(), category, str[1], str[2], Double.valueOf(str[3]), Double.valueOf(str[4]),
                    R.drawable.ic_history, R.raw.opera_house);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tour_guide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        currentPosition = position;
        Fragment f = new AttractionFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.single_screen, f);
        transaction.commit();

        int tittle = points.get(position).getCategory();

        setTitle(points.get(position).getCategoryAsString(tittle));
        //Control open/close drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public AttractionPoint onCategorySelected() {
        return points.get(currentPosition);
    }
}
