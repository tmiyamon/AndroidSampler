package com.kawaii.androidsampler.imageview;


import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public static final String APP_NAME = "ImageView";
    public static final String APP_DESCRIPTION = "A ImageView sample to show the difference of scale type";

    ImageView imageView;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_main, container, false);

        imageView = (ImageView)rootView.findViewById(R.id.image);
        imageView.setScaleType(ImageView.ScaleType.CENTER);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_imageview_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_scale_type_center) {
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            return true;
        } else if (item.getItemId() == R.id.action_scale_type_center_crop) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return true;
        } else if (item.getItemId() == R.id.action_scale_type_center_inside) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return true;
        } else if (item.getItemId() == R.id.action_scale_type_fit_center) {
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return true;
        } else if (item.getItemId() == R.id.action_scale_type_fit_end) {
            imageView.setScaleType(ImageView.ScaleType.FIT_END);
            return true;
        } else if (item.getItemId() == R.id.action_scale_type_fit_start) {
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
            return true;
        } else if (item.getItemId() == R.id.action_scale_type_fit_xy) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return true;
        } else if (item.getItemId() == R.id.action_scale_type_matrix) {
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            Matrix m = new Matrix();
            m.setScale(5, 5);
            imageView.setImageMatrix(m);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
