package com.github.quanzhuo.model;

/**
 * Created by Admin on 2017/7/6.
 */

public abstract class KeyValuePair implements BaseItem {
    private static final int NO_DETAILS = 0;
    public static final int ACTIVITY = 1;
    public static final int RECIVER = 2;
    public static final int SERVICE = 3;
    public static final int PROVIDER = 4;
    public String mLabel;

    public KeyValuePair(String label) {
        mLabel = label;
    }

    public String getLabel() {
        return mLabel;
    }

    public abstract String getValue();

    @Override
    public int getType() {
        return KEY_VALUE_PAIR;
    }

    public int getDetails() {
        return NO_DETAILS;
    }
}
