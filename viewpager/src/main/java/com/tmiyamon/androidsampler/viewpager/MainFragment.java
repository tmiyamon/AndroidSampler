package com.tmiyamon.androidsampler.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    public static final String APP_NAME = "ViewPager";
    public static final String APP_DESCRIPTION = "A view pager sample";

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.viewpager_pager_main, container, false);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getFragmentManager()));

        return rootView;
    }

    public static class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private List<Class<? extends Fragment>> fragments = new ArrayList<Class<? extends Fragment>>(){{
            add(ContentFragmentA.class);
            add(ContentFragmentB.class);
            add(ContentFragmentC.class);
        }};


        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            try {
                return fragments.get(position).newInstance();
            } catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + (position+1);
        }
    }

    public static class ContentFragment extends Fragment {
        public String getText() {
            return getClass().getName();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.viewpager_fragment_main, container, false);

            TextView textView = (TextView)rootView.findViewById(R.id.text);
            textView.setText(getText());

            return rootView;
        }
    }

    public static class ContentFragmentA extends ContentFragment{}
    public static class ContentFragmentB extends ContentFragment{}
    public static class ContentFragmentC extends ContentFragment{}
}

