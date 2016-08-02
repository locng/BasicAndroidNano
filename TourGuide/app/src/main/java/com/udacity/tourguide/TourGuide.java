package com.udacity.tourguide;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TourGuide extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        // Create 4 category
        ArrayList<AttractionPoints> points = new ArrayList<AttractionPoints>();

        points.add(createPoint(AttractionPoints.RESTAURANT));
        points.add(createPoint(AttractionPoints.HOTEL));
        points.add(createPoint(AttractionPoints.SHOPPING));
        points.add(createPoint(AttractionPoints.SIGHT_SEEING));

        AttractionPointsAdapter pointsAdapter = new AttractionPointsAdapter(getApplication(),points);

        ListView navigationView = (ListView) findViewById(R.id.nav_view);
        navigationView.setAdapter(pointsAdapter);
        navigationView.setOnItemClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
    }

    private AttractionPoints createPoint(int category){
        Resources res = getResources();
        if (category == AttractionPoints.RESTAURANT) {
        String str[] = res.getStringArray(R.array.restaurant);
            return new AttractionPoints(category, str[1],str[2], Double.valueOf(str[3]),Double.valueOf(str[4]));
        } else if (category == AttractionPoints.HOTEL) {
            String str[] = res.getStringArray(R.array.hotel);
            return new AttractionPoints(category, str[1],str[2], Double.valueOf(str[3]),Double.valueOf(str[4]));
        } else if (category == AttractionPoints.SHOPPING) {
            String str[] = res.getStringArray(R.array.shopping);
            return new AttractionPoints(category, str[1],str[2], Double.valueOf(str[3]),Double.valueOf(str[4]));
        } else {
            String str[] = res.getStringArray(R.array.sightseeing);
            return new AttractionPoints(category, str[1],str[2], Double.valueOf(str[3]),Double.valueOf(str[4]));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Fragment f = new Restaurants();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.single_screen, f).commit();

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
