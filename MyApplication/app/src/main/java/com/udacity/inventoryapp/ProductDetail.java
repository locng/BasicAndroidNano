package com.udacity.inventoryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProductDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetail extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PRODUCT_KEY = "PRODUCT_KEY";

    // TODO: Rename and change types of parameters
    private Product product;
    ProductDBHelper mDbHelper;
    private OnDataChangeListener listener;

    public ProductDetail() {
        // Required empty public constructor
    }

    public static ProductDetail newInstance(Product product) {
        ProductDetail fragment = new ProductDetail();
        Bundle args = new Bundle();
        args.putParcelable(PRODUCT_KEY, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = getArguments().getParcelable(PRODUCT_KEY);
        }
        mDbHelper = new ProductDBHelper(getContext());
        mDbHelper.open();
    }

    EditText productQuantity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_detail, container, false);
        TextView productName = (TextView)v.findViewById(R.id.productName);
        TextView productPrice = (TextView)v.findViewById(R.id.productPrice);
        productQuantity = (EditText) v.findViewById(R.id.productQuantity);
        Button btUpdate = (Button)v.findViewById(R.id.btUpdate);
        Button btDelete = (Button)v.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        TextView productSupplier = (TextView)v.findViewById(R.id.productSupplier);

        productName.setText(product.getProductName());
        productPrice.setText(Double.toString(product.getProductPrice()));
        productQuantity.setText(Integer.toString(product.getProductQuantity()));
        productSupplier.setText(product.getProductSupplier());

        return v;
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

    @Override
    public void onClick(View view) {
        int ResId = view.getId();
        if (ResId == R.id.btUpdate) {
            int quantity =Integer.valueOf(productQuantity.getText().toString());
            if (quantity > 0){
                product.setProductQuantity(quantity);
                mDbHelper.updateProductEntry(product);
                listener.onDataChanged();
                Toast.makeText(getContext(), "Product updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),"Input must be positive", Toast.LENGTH_SHORT).show();
            }
        } else if (ResId == R.id.bt_delete){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setMessage(R.string.confirm_delete_msg)
                    .setPositiveButton(R.string.yes_msg, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDbHelper.deleteProductEntry(product);
                            listener.onDataChanged();
                        }
                    })
                    .setNegativeButton(R.string.no_msg, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
