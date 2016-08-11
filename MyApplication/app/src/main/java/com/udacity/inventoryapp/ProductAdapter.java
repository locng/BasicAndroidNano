package com.udacity.inventoryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/**
 * Created by nloc on 8/11/2016.
 */
public class ProductAdapter extends ArrayAdapter<Product> {
    public ProductAdapter(Context context, List<Product> objects) {
        super(context, 0, objects);
    }

    ImageView imageView;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Product product = getItem(position);
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent, false);
        }
        imageView = (ImageView)v.findViewById(R.id.thumbnail);
        TextView name = (TextView)v.findViewById(R.id.productName);
        TextView price = (TextView)v.findViewById(R.id.productPrice);
        TextView quantity = (TextView)v.findViewById(R.id.productQuantity);
        TextView remaining = (TextView)v.findViewById(R.id.productRemaining);

        name.setText(product.getProductName());
        price.setText(Double.toString(product.getProductPrice()));
        quantity.setText(Integer.toString(product.getProductQuantity()));
        remaining.setText(Integer.toString(product.getProductRemaining()));

        if (!TextUtils.isEmpty(product.getProductImageLocation())){
            ImageDisplay imageDisplay = new ImageDisplay();
            imageDisplay.execute(product.getProductImageLocation());
        }
        return v;
    }
   class ImageDisplay extends AsyncTask<String, Void, Bitmap> {
       @Override
       protected Bitmap doInBackground(String... uris) {
           Bitmap bitmap = null;
           try {
               bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(uris[0]));
           } catch (IOException e) {
               e.printStackTrace();
           }
           return bitmap;
       }

       @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    };
}
