package com.example.rnztx.popularmovies.movieDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rnztx.popularmovies.R;
import com.example.rnztx.popularmovies.modules.MovieInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        //retrieve object from intent
        String INTENT_EXTRA_KEY = getString(R.string.intentExtra_MovieInfo_Key);
        Bundle intentBundle = getActivity().getIntent().getExtras();
        MovieInfo movieInfo = (MovieInfo)intentBundle.getParcelable(INTENT_EXTRA_KEY);

        // set title
        TextView textView = (TextView)rootView.findViewById(R.id.txt_hello);
        textView.setText(movieInfo.getTitle());

        return rootView;
    }

}
