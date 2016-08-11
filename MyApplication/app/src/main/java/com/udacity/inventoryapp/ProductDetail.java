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
 * {@link ProductDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetail extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Product product;
    ProductDBHelper mDbHelper;

    private OnFragmentInteractionListener mListener;

    public ProductDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetail newInstance(String param1, String param2, Product product) {
        ProductDetail fragment = new ProductDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putE(ARG_PARAM3, (Serializable) product);
        args.putBundle(ARG_PARAM3, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            product = (Product) getArguments().getSerializable(ARG_PARAM3);
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
        btUpdate.setOnClickListener(this);
        TextView productSupplier = (TextView)v.findViewById(R.id.productSupplier);

        productName.setText(product.getProductName());
        productPrice.setText(Double.toString(product.getProductPrice()));
        productQuantity.setText(Integer.toString(product.getProductQuantity()));
        productSupplier.setText(product.getProductSupplier());

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        int ResId = view.getId();
        if (ResId == R.id.btUpdate) {
            int quantity =Integer.valueOf(productQuantity.getText().toString());
            if (quantity > 0){
                product.setProductQuantity(quantity);
                mDbHelper.updateProductEntry(product);
            } else {
                Toast.makeText(getContext(),"Input must be positive", Toast.LENGTH_SHORT);
            }
        } else if (ResId == R.id.bt_delete){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.confirm_delete_msg)
                    .setPositiveButton(R.string.yes_msg, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDbHelper.deleteProductEntry(product);
                        }
                    })
                    .setNegativeButton(R.string.no_msg, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    }).create();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
