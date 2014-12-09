package com.tmiyamon.androidsampler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {
    public static final String ASSET_FILE_NAME = "fragment";
    public static final String KEY_FRAGEMENT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, buildFragment())
                    .commit();
        }
    }

    public Fragment buildFragment() {
        ArrayList<String> fragmentClassNames = getFragmentClassNames(ASSET_FILE_NAME);

        Fragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY_FRAGEMENT, fragmentClassNames);
        fragment.setArguments(bundle);

        return fragment;
    }

    public ArrayList<String> getFragmentClassNames(String filename) {
        ArrayList<String> fragments = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().getAssets().open(filename)));
            String line;
            while((line = reader.readLine()) != null) {
                fragments.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fragments;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        @InjectView(R.id.recyclerView)
        RecyclerView recyclerView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.inject(this, rootView);

            initRecyclerView(getArguments().getStringArrayList(KEY_FRAGEMENT));

            return rootView;
        }

        public void initRecyclerView(List<String> items) {

            List<ListItem> listItems = new ArrayList<>(items.size());
            for (String fragmentClassName : items) {
                try {
                    listItems.add(new ListItem(fragmentClassName));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            recyclerView.setAdapter(new Adapter(listItems));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @InjectView(R.id.textView)
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.inject(this, itemView);
            }
        }

        public class Adapter extends RecyclerView.Adapter<ViewHolder> {
            List<ListItem> items;

            public Adapter(List<ListItem> items) {
                this.items = items;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
                return new ViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                holder.textView.setText(items.get(position).text);
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        }

        public class ListItem {
            String text;
            Class<?> fragmentClass;

            public ListItem(String fragmentClassName) throws ClassNotFoundException {
                this.text = fragmentClassName;
                this.fragmentClass = Class.forName(fragmentClassName);
            }
        }
    }
}
