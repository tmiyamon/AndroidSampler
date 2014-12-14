package com.tmiyamon.androidsampler;

/**
 * Created by tmiyamon on 12/10/14.
 */
public class Target {
    public static final String FIELD_APP_NAME = "APP_NAME";
    public static final String FIELD_APP_DESCRIPTION = "APP_DESCRIPTION";

    private String className;
    private String title;
    private String description;

    public static final Target forClassName(String className) throws ClassNotFoundException {
        return new Target(className);
    }

    private Target(String className) throws ClassNotFoundException {
        this.className = className;

        Class<?> targetClass = Class.forName(className);
        this.title = buildValueFromFragment(targetClass, FIELD_APP_NAME, targetClass.getName());
        this.description = buildValueFromFragment(targetClass, FIELD_APP_DESCRIPTION, "");
    }

    private String buildValueFromFragment(Class<?> cls, String fieldName, String defaultValue) {
        try {
            return (String)cls.getField(fieldName).get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public String getClassName() {
        return className;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
