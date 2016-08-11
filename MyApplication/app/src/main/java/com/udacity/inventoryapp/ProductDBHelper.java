package com.udacity.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductDBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

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
                    ProductContract.ProductEntry.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_PRICE + REAL_TYPE + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_REMAINING + INTEGER_TYPE + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_IMAGE_LOCATION + TEXT_TYPE + COMMA_SEP +
                    ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL + TEXT_TYPE +
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

    /*
    * Open the habit database. If it can not be opened, try to create a new instance of the database,
    * If it can not be created, throws an exception to signal the failure.
    * */
    public void open() throws SQLException {
        //Get data repository in write mode
        db = this.getWritableDatabase();
    }
    public void close(){
        db.close();
    }

    /*
    * Create a new product entry using the name, price and quantity provided.
    * if created successfully, return the new rowId for that one, otherwise return -1
    * */
    public long addNewProduct( Product product) {

        // Create a new map of values, where column names are the keys
        ContentValues initialValues = new ContentValues();
        initialValues.put(ProductContract.ProductEntry.COLUMN_NAME, product.getProductName());
        initialValues.put(ProductContract.ProductEntry.COLUMN_PRICE, product.getProductPrice());
        initialValues.put(ProductContract.ProductEntry.COLUMN_QUANTITY, product.getProductQuantity());
        initialValues.put(ProductContract.ProductEntry.COLUMN_REMAINING, product.getProductQuantity());
        initialValues.put(ProductContract.ProductEntry.COLUMN_IMAGE_LOCATION, product.getProductImageLocation());
        initialValues.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL, product.getProductSupplier());

        return db.insert(ProductContract.ProductEntry.TABLE_NAME, null, initialValues);
    }

    /*
    * Delete all rows in the database
    * */
    public int deleteAllProduct() {
        //Passing null to whereClause will delete all rows.
        //To remove all rows and get a count pass "1" as the whereClause
        return db.delete(ProductContract.ProductEntry.TABLE_NAME, "1", null);
    }

    /*
    * Read out one product
    * */
    public Cursor fetchProductEntry(long rowId) throws SQLException {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ProductContract.ProductEntry.COLUMN_ID,
                ProductContract.ProductEntry.COLUMN_NAME,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_REMAINING,
                ProductContract.ProductEntry.COLUMN_IMAGE_LOCATION,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL
        };

        String selection = ProductContract.ProductEntry.COLUMN_ID + "=" + rowId;
        Cursor cursor =
                db.query(true, ProductContract.ProductEntry.TABLE_NAME, projection, selection, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int getProductCount() throws SQLException {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ProductContract.ProductEntry.COLUMN_ID,
        };

        Cursor cursor =
                db.query(true, ProductContract.ProductEntry.TABLE_NAME, projection, null, null, null, null, null, null);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }
    public List<Product> fetchAllProductEntry() throws SQLException {
        List<Product> allProducts = new ArrayList<>();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ProductContract.ProductEntry.COLUMN_ID,
                ProductContract.ProductEntry.COLUMN_NAME,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_REMAINING,
                ProductContract.ProductEntry.COLUMN_IMAGE_LOCATION,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL
        };

        Cursor cursor =
                db.query(true, ProductContract.ProductEntry.TABLE_NAME, projection, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            allProducts.add(cursorToProduct(cursor));
            cursor.moveToNext();
        }
        return allProducts;
    }
    /*
    * Database update
    * */
    public boolean updateProductEntry(Product product) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(ProductContract.ProductEntry.COLUMN_QUANTITY, product.getProductQuantity());
        updateValues.put(ProductContract.ProductEntry.COLUMN_REMAINING, product.getProductRemaining());
        String selection = ProductContract.ProductEntry.COLUMN_ID + "=" + product.getProductId();
        return db.update(ProductContract.ProductEntry.TABLE_NAME, updateValues, selection, null) > 0;
    }

    /*
    * Delete a single product
    * */
    public boolean deleteProductEntry(Product product) {
        String selection = ProductContract.ProductEntry.COLUMN_ID + "=" + product.getProductId();
        return db.delete(ProductContract.ProductEntry.TABLE_NAME, selection, null) > 0;
    }

    private Product cursorToProduct(Cursor c) {
        Product product = new Product();

        product.setProductId(c.getInt(c.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_ID)));
        product.setProductName(c.getString(c.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME)));
        product.setProductPrice(c.getFloat(c.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_PRICE)));
        product.setProductQuantity(c.getInt(c.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_QUANTITY)));
        product.setProductRemaining(c.getInt(c.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_REMAINING)));
        product.setProductImageLocation(c.getString(c.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_IMAGE_LOCATION)));
        product.setProductSupplier(c.getString(c.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL)));

        return product;
    }

    public void deleteDatable(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}