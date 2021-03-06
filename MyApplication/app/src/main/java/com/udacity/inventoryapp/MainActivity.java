package com.udacity.inventoryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noData = (TextView)findViewById(R.id.empty_tv);

        mDbHelper = new ProductDBHelper(this);
        mDbHelper.open();

        listView = (ListView) findViewById(R.id.list_);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setEmptyView(noData);

        if (mDbHelper.getProductCount() > 0) {
            listView.setVisibility(View.VISIBLE);

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
            noData.setVisibility(View.GONE);

            //Add new product to database
            Fragment f = new AddProductFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.placeholder, f);
            transaction.addToBackStack("ADDProduct");
            transaction.commit();
        }
    }

    @Override
    public void onProductQuantityUpdated() {
        //Update layout, no need to get back to main screen
        adapter.clear();
        productList.addAll(mDbHelper.fetchAllProductEntry());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProductDeleted() {
        //Update layout, back to main screen immediately
        adapter.clear();
        productList.addAll(mDbHelper.fetchAllProductEntry());
        adapter.notifyDataSetChanged();
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            listView.setVisibility(View.VISIBLE);
            addProduct.setVisibility(View.VISIBLE);
            fm.popBackStack();
        }
    }

    @Override
    public void onProductAdded() {
        //Update layout, back to main screen immediately
        adapter.clear();
        productList.addAll(mDbHelper.fetchAllProductEntry());
        adapter.notifyDataSetChanged();
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            listView.setVisibility(View.VISIBLE);
            addProduct.setVisibility(View.VISIBLE);
            fm.popBackStack();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        addProduct.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        Product currentProduct = adapter.getItem(position);
        //Add new product to database
        Fragment f = ProductDetail.newInstance(currentProduct);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.placeholder, f);
        transaction.addToBackStack("DetailProduct");
        transaction.commit();
    }
}
