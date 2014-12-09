package com.tmiyamon.androidsampler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity
        implements MainFragment.OnFragmentInteractionListener {

    public static final String ASSET_FILE_NAME = "fragments";
//    public static final String KEY_FRAGMENT = "fragments";


    @InjectView(R.id.tool_bar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);

        ArrayList<String> fragments = getFragmentClassNames(ASSET_FILE_NAME);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance(fragments))
                    .commit();

            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(
                            getSupportFragmentManager().getBackStackEntryCount() > 0);
                }
            });

        }
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

    @Override
    public void onFragmentInteraction(Class<? extends Fragment> fragmentClass) {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.abc_fade_in, 0, 0, R.anim.abc_fade_out)
                    .replace(R.id.container, fragmentClass.newInstance())
                    .addToBackStack(null)
                    .commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        @InjectView(R.id.recyclerView)
//        RecyclerView recyclerView;
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            ButterKnife.inject(this, rootView);
//
//            initRecyclerView(getArguments().getStringArrayList(KEY_FRAGMENT));
//
//            return rootView;
//        }
//
//        public void initRecyclerView(List<String> items) {
//
//            List<ListItem> listItems = new ArrayList<>(items.size());
//            for (String fragmentClassName : items) {
//                try {
//                    listItems.add(new ListItem(fragmentClassName));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            recyclerView.setAdapter(new Adapter(listItems));
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerView.addOnItemTouchListener(new ItemClickDetector(getActivity(), new ItemClickDetector.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                }
//            }));
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            @InjectView(R.id.name)
//            TextView name;
//
//            @InjectView(R.id.description)
//            TextView description;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//                ButterKnife.inject(this, itemView);
//            }
//        }
//
//        public class Adapter extends RecyclerView.Adapter<ViewHolder> {
//            List<ListItem> items;
//
//            public Adapter(List<ListItem> items) {
//                this.items = items;
//            }
//
//            @Override
//            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
//                return new ViewHolder(itemView);
//            }
//
//            @Override
//            public void onBindViewHolder(ViewHolder holder, int position) {
//                ListItem item = items.get(position);
//                holder.name.setText(item.name);
//                holder.description.setText(item.description);
//            }
//
//            @Override
//            public int getItemCount() {
//                return items.size();
//            }
//        }
//
//        public static class ItemClickDetector implements RecyclerView.OnItemTouchListener {
//            private OnItemClickListener listener;
//            private GestureDetector gestureDetector;
//
//            public interface OnItemClickListener {
//                public void onItemClick(View view, int position);
//            }
//
//            public ItemClickDetector(Context context, OnItemClickListener listener) {
//                this.listener = listener;
//                this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                    @Override
//                    public boolean onSingleTapUp(MotionEvent e) {
//                        return true;
//                    }
//                });
//            }
//
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
//                View childView = view.findChildViewUnder(e.getX(), e.getY());
//                if (childView != null && listener != null && gestureDetector.onTouchEvent(e)) {
//                    listener.onItemClick(childView, view.getChildPosition(childView));
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }
//        }
//
//        public class ListItem {
//            public static final String FIELD_APP_NAME = "APP_NAME";
//            public static final String FIELD_APP_DESCRIPTION = "APP_DESCRIPTION";
//
//            Class<?> fragmentClass;
//            String fragmentClassName;
//            String name;
//            String description;
//
//            public ListItem(String fragmentClassName) throws ClassNotFoundException {
//                this.fragmentClassName = fragmentClassName;
//                this.fragmentClass = Class.forName(fragmentClassName);
//
//                this.name = buildValueFromFragment(FIELD_APP_NAME, this.fragmentClassName);
//                this.description = buildValueFromFragment(FIELD_APP_DESCRIPTION, "");
//            }
//
//
//            private String buildValueFromFragment(String fieldName, String defaultValue) {
//                try {
//                    return (String)this.fragmentClass.getField(fieldName).get(null);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                }
//                return defaultValue;
//            }
//        }
//    }
}
