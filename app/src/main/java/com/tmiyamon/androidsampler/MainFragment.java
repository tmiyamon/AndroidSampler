package com.tmiyamon.androidsampler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    public static final String ARG_KEY_TARGETS = "fragments";

    @InjectView(R.id.recyclerView)
    SimpleRecyclerView simpleRecyclerView;

    private List<String> targetClassNames;
    private OnFragmentInteractionListener listener;

    public static MainFragment newInstance(ArrayList<String> targets) {
        MainFragment fragment = new MainFragment();

        Bundle args = new Bundle();
        args.putStringArrayList(ARG_KEY_TARGETS, targets);
        fragment.setArguments(args);

        return fragment;
    }

    public MainFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.targetClassNames = getArguments().getStringArrayList(ARG_KEY_TARGETS);
        } else {
            this.targetClassNames = Collections.emptyList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);

        initRecyclerView(this.targetClassNames);

        return rootView;
    }

    void initRecyclerView(List<String> items) {
        final List<SimpleRecyclerView.ListItem> listItems = new ArrayList<>(items.size());
        for (String targetClassName : items) {
            try {
                listItems.add(new SimpleRecyclerView.ListItem(targetClassName));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        simpleRecyclerView.initAdapter(listItems);
        simpleRecyclerView.setListener(listener);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String targetClassName);
    }
}
