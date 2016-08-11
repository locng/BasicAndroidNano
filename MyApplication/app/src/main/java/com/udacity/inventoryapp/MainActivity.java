package com.udacity.inventoryapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnDataChangeListener, AdapterView.OnItemClickListener {

    Button addProduct;
    ProductDBHelper mDbHelper;
    ListView listView;
    ProductAdapter adapter;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDbHelper = new ProductDBHelper(this);
        mDbHelper.open();

        listView = (ListView)findViewById(R.id.list_);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        if (mDbHelper.getProductCount() <= 0) {
            listView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);;
            productList.addAll(mDbHelper.fetchAllProductEntry());
            adapter.notifyDataSetChanged();
        }
        addProduct = (Button) findViewById(R.id.addProduct);
        addProduct.setVisibility(View.VISIBLE);
        addProduct.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    listView.setVisibility(View.VISIBLE);
                    addProduct.setVisibility(View.VISIBLE);
                    fm.popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View view) {
        int resId = view.getId();
        if (resId == R.id.addProduct) {
            addProduct.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);

            //Add new product to database
            Fragment f = new AddProductFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.placeholder, f);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onDataChanged() {
        adapter.clear();
        productList.addAll(mDbHelper.fetchAllProductEntry());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        addProduct.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        Product currentProduct = adapter.getItem(position);
        Log.d("onItemClick",currentProduct.toString());
        //Add new product to database
        Fragment f = ProductDetail.newInstance(currentProduct);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.placeholder, f);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
