package com.udacity.tourguide;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class AttractionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    OnCategorySelectedListener listener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AttractionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttractionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttractionFragment newInstance(String param1, String param2) {
        AttractionFragment fragment = new AttractionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            listener = (OnCategorySelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCategorySelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) return null;
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.content_tour_guide, container, false);
        final AttractionPoints point = listener.onCategorySelected();

        ImageView coverPhoto = (ImageView) v.findViewById(R.id.photo);
        if (point.hasCoverPhoto()) {
            coverPhoto.setImageResource(point.getImageResId());
        }

        //Update icon based on type of location
        ImageView iconName = (ImageView) v.findViewById(R.id.icon_name);
        iconName.setImageResource(point.getLocationResId());
        //Update location name
        TextView textName = (TextView) v.findViewById(R.id.text_name);
        textName.setText(point.getName());

        ImageView iconPlace = (ImageView) v.findViewById(R.id.icon_place);
        //Update location address
        TextView textPlace = (TextView) v.findViewById(R.id.text_place);
        textPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open the map if address get clicked
                double latitude = point.getLatitude();
                double longitude = point.getLongitude();

                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        textPlace.setText(point.getAddressName());

        return v;
    }

    public interface OnCategorySelectedListener {
        public AttractionPoints onCategorySelected();
    }
}
