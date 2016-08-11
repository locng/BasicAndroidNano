package com.udacity.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nloc on 8/9/2016.
 */

public class ProductContract {

    private ProductDBHelper mDbHelper;
    Context context;
    //Data repository
    SQLiteDatabase db;

    public ProductContract(Context ctx) {
        context = ctx;
    }

    /* Inner class define table contents */
    public static abstract class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_ID = "productId";
        public static final String COLUMN_NAME = "productName";
        public static final String COLUMN_PRICE = "productPrice";
        public static final String COLUMN_QUANTITY = "productQuantity";
        public static final String COLUMN_REMAINING = "productRemaining";
        public static final String COLUMN_IMAGE_LOCATION = "productImageLocation";
        public static final String COLUMN_SUPPLIER_EMAIL = "productSupplierEmail";
    }
}