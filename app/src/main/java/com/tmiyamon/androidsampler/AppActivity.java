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
            Target target = Target.forClassName(fragmentName);

            Class<? extends Fragment> fragmentClass =
                    Class.forName(target.getClassName()).asSubclass(Fragment.class);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, fragmentClass.newInstance())
                    .commit();

            toolbar.setTitle(target.getTitle());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
