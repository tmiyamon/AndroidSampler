package com.tmiyamon.androidsampler;

import android.support.v4.app.Fragment;

/**
 * Created by tmiyamon on 12/10/14.
 */
public class FragmentManager {
    public static final String FIELD_APP_NAME = "APP_NAME";
    public static final String FIELD_APP_DESCRIPTION = "APP_DESCRIPTION";

    private String fragmentClassName;
    private Class<? extends Fragment> fragmentClass;
    private String title;
    private String description;

    public static final FragmentManager forClassName(String className) throws ClassNotFoundException {
        return new FragmentManager(className);
    }

    private FragmentManager(String className) throws ClassNotFoundException {
        this.fragmentClassName = className;
        this.fragmentClass = Class.forName(className).asSubclass(Fragment.class);
        this.title = buildValueFromFragment(FIELD_APP_NAME, this.fragmentClass.getName());
        this.description = buildValueFromFragment(FIELD_APP_DESCRIPTION, "");
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

    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
