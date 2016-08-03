package com.udacity.tourguide;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


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
        View v = inflater.inflate(R.layout.list_contents, container, false);

        final AttractionPoint categorySelected = listener.onCategorySelected();
        int category = categorySelected.getCategory();

        ArrayList<AttractionPoint> points = newAttraction(category);

        AttractionPointsAdapter adapter = new AttractionPointsAdapter(getActivity(), points);

        ListView listView = (ListView) v.findViewById(R.id.list);

        listView.setAdapter(adapter);

        return v;
    }


    //RQ:App contains at least 4 lists of relevant attractions for a location
    private ArrayList<AttractionPoint> newAttraction(int category) {

        ArrayList<AttractionPoint> points = new ArrayList<>();
        Resources res = getContext().getResources();

        if (category == AttractionPoint.RESTAURANT) {
            points.add(new AttractionPoint(getContext(), category, res.getString(R.string.name_res1),
                    res.getString(R.string.addr_res1), Double.valueOf(R.string.default_long),
                    Double.valueOf(R.string.default_lat),
                    R.drawable.ic_restaurant, R.drawable.paradise_beach_club));
            points.add(new AttractionPoint(getContext(),category, res.getString(R.string.name_res2),
                    res.getString(R.string.addr_res2), Double.valueOf(R.string.default_long),
                    Double.valueOf(R.string.default_lat),
                    R.drawable.ic_restaurant, R.drawable.la_villa_french));

        } else if (category == AttractionPoint.HOTEL) {
            points.add(new AttractionPoint(getContext(),category, res.getString(R.string.name_hotel1),
                    res.getString(R.string.addr_hotel1), Double.valueOf(R.string.default_long),
                    Double.valueOf(R.string.default_lat),
                    R.drawable.ic_hotel, R.drawable.park_hyatt));
            points.add(new AttractionPoint(getContext(), category, res.getString(R.string.name_hotel2),
                    res.getString(R.string.addr_hotel2), Double.valueOf(R.string.default_long),
                    Double.valueOf(R.string.default_lat),
                    R.drawable.ic_hotel, R.drawable.reverie));

        } else if (category == AttractionPoint.SHOPPING) {
            points.add(new AttractionPoint(getContext(), category, res.getString(R.string.name_shop1),
                    res.getString(R.string.addr_shop2), Double.valueOf(R.string.default_long),
                    Double.valueOf(R.string.default_lat),
                    R.drawable.ic_shopping, R.drawable.saigononbike));
            points.add(new AttractionPoint(getContext(), category, res.getString(R.string.name_shop2),
                    res.getString(R.string.addr_shop2), Double.valueOf(R.string.default_long),
                    Double.valueOf(R.string.default_lat),
                    R.drawable.ic_shopping, R.drawable.phancustom));

        } else {
            points.add(new AttractionPoint(getContext(), category, res.getString(R.string.name_sin1),
                    res.getString(R.string.addr_sin1), Double.valueOf(R.string.default_long),
                    Double.valueOf(R.string.default_lat),
                    R.drawable.ic_history, R.drawable.opera_house));
            points.add(new AttractionPoint(getContext(), category, res.getString(R.string.name_sin2),
                    res.getString(R.string.addr_sin2), Double.valueOf(R.string.default_long),
                    Double.valueOf(R.string.default_lat),
                    R.drawable.ic_history, R.drawable.dame));
        }
        return points;
    }

    public interface OnCategorySelectedListener {
        AttractionPoint onCategorySelected();
    }
}
