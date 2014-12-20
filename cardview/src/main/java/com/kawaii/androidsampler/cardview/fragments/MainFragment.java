package com.kawaii.androidsampler.cardview.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kawaii.androidsampler.cardview.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public static final String APP_NAME = "CardView";
    public static final String APP_DESCRIPTION = "A CardView sample with RecyclerView";

    private String[] items;

    public MainFragment() {
        items = new String[100];
        for (int i = 0; i < items.length; i++) {
            items[i] = "Item " + i;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cardview_main, container, false);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new Adapter(items));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    public static class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private String[] items;

        public Adapter(String[] items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_main, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(items[position]);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView)itemView.findViewById(R.id.text_view);
        }
    }

}
