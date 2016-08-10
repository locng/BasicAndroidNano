package com.udacity.inventoryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Inventory.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " INTEGER";
    private static final String COMMA_SEP = ", ";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProductContract.ProductEntry.TABLE_NAME +
                    "(" +
                    ProductContract.ProductEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_ID + INTEGER_TYPE + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_PRICE + TEXT_TYPE + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_NAME_DURATION + INTEGER_TYPE +
                    ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductContract.ProductEntry.TABLE_NAME;

    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void deleteDatable(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}