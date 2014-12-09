package com.tmiyamon.androidsampler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AppActivity extends ActionBarActivity {
    public static final String FIELD_APP_NAME = "APP_NAME";
    public static final String FIELD_APP_DESCRIPTION = "APP_DESCRIPTION";

    public static final String KEY_FRAGMENT = "fragment";

    @InjectView(R.id.tool_bar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        ButterKnife.inject(this);

        try {
            String fragmentName = getIntent().getStringExtra(KEY_FRAGMENT);
            Class<? extends Fragment> fragmentClass = Class.forName(fragmentName).asSubclass(Fragment.class);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, fragmentClass.newInstance())
                    .commit();

            toolbar.setTitle((String)fragmentClass.getField("APP_NAME").get(null));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
