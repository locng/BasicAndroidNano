package com.udacity.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.Calendar;

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
    }

    /*
    * Open the habit database. If it can not be opened, try to create a new instance of the database,
    * If it can not be created, throws an exception to signal the failure.
    * */
    public ProductContract open() throws SQLException {
        mDbHelper = new ProductDBHelper(context);
        //Get data repository in write mode
        db = mDbHelper.getWritableDatabase();
        return this;
    }

    /*
    * Create a new habit entry using the type and duration provided.
    * if created successfully, return the new rowId for that one, otherwise return -1
    * */
    public long addNewProduct(String productName, double productPrice, int productQuantity) {
        Calendar currentDay = Calendar.getInstance();

        // Create a new map of values, where column names are the keys
        ContentValues initialValues = new ContentValues();
        initialValues.put(ProductEntry.COLUMN_NAME, currentDay.toString());
        initialValues.put(ProductEntry.COLUMN_PRICE, type);
        initialValues.put(ProductEntry.COLUMN_REMAINING, duration);
        return db.insert(ProductEntry.TABLE_NAME, null, initialValues);
    }

    /*
    * Delete all rows in the database
    * */
    public int deleteAllHabit() {
        //Passing null to whereClause will delete all rows.
        //To remove all rows and get a count pass "1" as the whereClause
        return db.delete(ProductEntry.TABLE_NAME, "1", null);
    }

    /*
    * Database reading
    * */
    public Cursor fetchHabitEntry(long rowId) throws SQLException {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_NAME,
                ProductEntry.COLUMN_PRICE,
                ProductEntry.COLUMN_REMAINING,

        };
        String selection = ProductEntry.COLUMN_ID + "=" + rowId;
        Cursor cursor =
                db.query(true, ProductEntry.TABLE_NAME, projection, selection, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /*
    * Database update
    * */
    public boolean updateHabitEntry(long rowId, int duration) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(ProductEntry.COLUMN_REMAINING, duration);
        String selection = ProductEntry.COLUMN_ID + "=" + rowId;
        return db.update(ProductEntry.TABLE_NAME, updateValues, selection, null) > 0;
    }
}