package com.tmiyamon.androidsampler;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tmiyamon on 12/10/14.
 */
public class SimpleRecyclerView extends RecyclerView {
    private List<ListItem> listItems;
    private MainFragment.OnFragmentInteractionListener listener;

    public SimpleRecyclerView(Context context) {
        super(context);
        init();
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        setAdapter(new Adapter());
        setLayoutManager(new LinearLayoutManager(getContext()));
        setItemAnimator(new DefaultItemAnimator());
        setHasFixedSize(true);
        addOnItemTouchListener(new ItemClickDetector(getContext(), new ItemClickDetector.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (listener != null) {
                    listener.onFragmentInteraction(listItems.get(position).fragmentClass);
                }
            }
        }));
    }


    public void initAdapter(List<ListItem> listItems) {
        this.listItems = listItems;

        Adapter adapter = ((Adapter)this.getAdapter());
        adapter.setItems(this.listItems);
        adapter.notifyDataSetChanged();
    }

    public void setListener(MainFragment.OnFragmentInteractionListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.name)
        TextView name;

        @InjectView(R.id.description)
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }
    }

    public class Adapter extends RecyclerView.Adapter<ViewHolder> {
        List<ListItem> items = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ListItem item = items.get(position);
            holder.name.setText(item.name);
            holder.description.setText(item.description);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(List<ListItem> items) {
            this.items = items;
        }
    }


    public static class ItemClickDetector implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener listener;
        private GestureDetector gestureDetector;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        public ItemClickDetector(Context context, OnItemClickListener listener) {
            this.listener = listener;
            this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && listener != null && gestureDetector.onTouchEvent(e)) {
                listener.onItemClick(childView, view.getChildPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }
    }

    public static class ListItem {

        Class<? extends Fragment> fragmentClass;
        String fragmentClassName;
        String name;
        String description;

        public ListItem(String fragmentClassName) throws ClassNotFoundException {
            this.fragmentClassName = fragmentClassName;
            this.fragmentClass = Class.forName(fragmentClassName).asSubclass(Fragment.class);

            this.name = buildValueFromFragment(AppActivity.FIELD_APP_NAME, this.fragmentClassName);
            this.description = buildValueFromFragment(AppActivity.FIELD_APP_DESCRIPTION, "");
        }


        private String buildValueFromFragment(String fieldName, String defaultValue) {
            try {
                return (String)this.fragmentClass.getField(fieldName).get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }
}
