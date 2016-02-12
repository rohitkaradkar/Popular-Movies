package com.example.rnztx.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.rnztx.popularmovies.modules.AdapterMovieInfo;
import com.example.rnztx.popularmovies.modules.JsonHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private AdapterMovieInfo mAdapterMovieInfo;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);



        mAdapterMovieInfo = new AdapterMovieInfo(getActivity(),new JsonHandler().parseData());

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_gridView);
        gridView.setAdapter(mAdapterMovieInfo);

        return rootView;
    }

}
