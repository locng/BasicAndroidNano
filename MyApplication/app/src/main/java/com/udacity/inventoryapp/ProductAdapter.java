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
import android.widget.Button;
import android.widget.ImageView;
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

    ViewHolder viewHolder;

    static class ViewHolder {
        TextView name;
        TextView price;
        TextView quantity;
        TextView remaining;
        ImageView imageView;
        Button order;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final Product product = getItem(position);
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) v.findViewById(R.id.productName);
            viewHolder.price = (TextView) v.findViewById(R.id.productPrice);
            viewHolder.quantity = (TextView) v.findViewById(R.id.productQuantity);
            viewHolder.remaining = (TextView) v.findViewById(R.id.productRemaining);
            viewHolder.order = (Button) v.findViewById(R.id.bt_order);
            viewHolder.imageView = (ImageView) v.findViewById(R.id.thumbnail);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDBHelper mDbHelper = new ProductDBHelper(getContext());
                mDbHelper.open();
                int remained = Integer.valueOf(product.getProductRemaining());
                Log.d("", "remained: " + remained);
                remained = --remained;
                if (remained >= 0) {
                    product.setProductRemaining(remained);
                    mDbHelper.updateProductEntry(product);
                    mDbHelper.close();
                    notifyDataSetChanged();
                } else {
                    return;
                }
            }
        });

        viewHolder.name.setText(product.getProductName());
        viewHolder.price.setText(Double.toString(product.getProductPrice()));
        viewHolder.quantity.setText(Integer.toString(product.getProductQuantity()));
        viewHolder.remaining.setText(Integer.toString(product.getProductRemaining()));

        if (!TextUtils.isEmpty(product.getProductImageLocation())) {
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
            viewHolder.imageView.setImageBitmap(bitmap);
        }
    }

    ;
}
