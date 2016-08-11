package com.udacity.inventoryapp;

/**
 * Created by nloc on 8/11/2016.
 */
interface OnDataChangeListener {
    void onDataChanged();
    Product onProductSelected(int position);
}
