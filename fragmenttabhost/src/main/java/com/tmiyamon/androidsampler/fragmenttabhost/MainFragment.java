package com.tmiyamon.androidsampler.fragmenttabhost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {
    public static final String APP_NAME = "FragmentTabHost";
    public static final String APP_DESCRIPTION = "A tab sample using FragmentTabHost";

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTabHost tabHost = (FragmentTabHost)inflater.inflate(R.layout.fragmenttabhost_fragment_tab, container, false);
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.container);

        tabHost.addTab(tabHost.newTabSpec("a").setIndicator("A"), ContentFragmentA.class, null);
        tabHost.addTab(tabHost.newTabSpec("b").setIndicator("B"), ContentFragmentB.class, null);
        tabHost.addTab(tabHost.newTabSpec("c").setIndicator("C"), ContentFragmentC.class, null);

        return tabHost;
    }

    public static class ContentFragment extends Fragment {
        public ContentFragment() {
        }

        protected String getText() {
            return getClass().getName();
        }
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragmenttabhost_fragment_main, container, false);
            TextView textView = ((TextView)rootView.findViewById(R.id.textView));
            textView.setText(getText());

            return rootView;
        }
    }

    public static class ContentFragmentA extends ContentFragment { }
    public static class ContentFragmentB extends ContentFragment { }
    public static class ContentFragmentC extends ContentFragment { }
}
