package com.tmiyamon.fragmenttaghost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainFragment extends Fragment {
    public static final String APP_NAME = "Tab with FragmentTabHost";
    public static final String APP_DESCRIPTION = "Simple Tab sample with FragmentTabHost";

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("FragmentTabHost");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
