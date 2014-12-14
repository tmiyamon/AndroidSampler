package com.tmiyamon.androidsampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    public static final String ASSET_FILE_NAME = "targets";
//    public static final String KEY_FRAGMENT = "fragments";


    @InjectView(R.id.tool_bar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);

        ArrayList<String> targets = getTargetClassNames(ASSET_FILE_NAME);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance(targets))
                    .commit();
        }
    }

    public ArrayList<String> getTargetClassNames(String filename) {
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
    public void onFragmentInteraction(String targetClassName) {
        if (targetClassName.endsWith("MainFragment")) {
            Intent intent = new Intent(this, AppActivity.class);
            intent.putExtra(AppActivity.KEY_FRAGMENT, targetClassName);

            startActivity(intent);
        } else if(targetClassName.endsWith("MainActivity")) {
            try {
                startActivity(new Intent(this, Class.forName(targetClassName).asSubclass(Activity.class)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
