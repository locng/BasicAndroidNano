package com.udacity.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {
    private static final int READ_REQUEST_CODE = 3;
    OnDataChangeListener listener;

    String productImageLocation;
    ProductDBHelper mDbHelper;
    ImageView productImage;
    Uri uri = null;


    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddProductFragment.
     */
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) return null;
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);
        final EditText productname = (EditText) v.findViewById(R.id.name);
        final EditText productPrice = (EditText) v.findViewById(R.id.price);
        final EditText productQuantity = (EditText) v.findViewById(R.id.quantity);
        final EditText productSupplier = (EditText) v.findViewById(R.id.supplier);
        productImage = (ImageView) v.findViewById(R.id.productImage);

        Button btAdd = (Button) v.findViewById(R.id.bt_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = productname.getText().toString();
                final String price = productPrice.getText().toString();
                final String quantity = productQuantity.getText().toString();
                final String supplier = productSupplier.getText().toString();
                if (invalidateAllField(name, price, quantity, supplier)) {
                    Product product = new Product(name, Double.parseDouble(price), Integer.parseInt(quantity), Integer.parseInt(quantity), supplier, productImageLocation);
                    mDbHelper = new ProductDBHelper(AddProductFragment.this.getContext());
                    mDbHelper.open();
                    if (mDbHelper.addNewProduct(product) > 0) {
                        Toast.makeText(getContext(), "Product added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Fail to add product", Toast.LENGTH_SHORT).show();
                    }
                    mDbHelper.close();
                    listener.onProductAdded();
                } else {
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btChangeImage = (Button) v.findViewById(R.id.bt_addImage);
        btChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFileSearch();
                AsyncTask<Uri, Void, Bitmap> imageLoadAsyncTask = new AsyncTask<Uri, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Uri... uris) {
                        return getBitmapFromUri(uris[0]);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        productImage.setImageBitmap(bitmap);
                    }
                };
                imageLoadAsyncTask.execute(uri);
            }
        });

        return v;
    }

    private boolean invalidateAllField(String name, String price, String quantity, String supplier) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(quantity) && !TextUtils.isEmpty(supplier)) {
            return true;
        }
        return false;
    }

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     * Code handling image display is copied from StorageClient sample
     */
    public void performFileSearch() {

        // BEGIN_INCLUDE (use_open_document_intent)
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a file (as opposed to a list
        // of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
        // END_INCLUDE (use_open_document_intent)
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        // BEGIN_INCLUDE (parse_open_document_response)
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code READ_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent,
        // and the below code shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.  Pull that uri using "resultData.getData()"

            if (resultData != null) {
                uri = resultData.getData();
                productImageLocation = uri.toString();
                //Return bitmap for product image
                displayImage(uri);
            }
            // END_INCLUDE (parse_open_document_response)
        }
    }

    private void displayImage(Uri uri) {

        AsyncTask<Uri, Void, Bitmap> imageLoadAsyncTask = new AsyncTask<Uri, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Uri... uris) {
                return getBitmapFromUri(uris[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                productImage.setImageBitmap(bitmap);
                productImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        };
        imageLoadAsyncTask.execute(uri);
    }

    /**
     * Create a Bitmap from the URI for that image and return it.
     *
     * @param uri the Uri for the image to return.
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor =
                    getActivity().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (Exception e) {
            Log.e("", "Failed to load image.", e);
            return null;
        } finally {
            try {
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("", "Error closing ParcelFile Descriptor");
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDataChangeListener) {
            listener = (OnDataChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDataChangeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
