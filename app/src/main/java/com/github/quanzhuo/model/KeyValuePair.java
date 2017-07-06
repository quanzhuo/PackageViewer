package com.github.quanzhuo.model;

/**
 * Created by Admin on 2017/7/6.
 */

public abstract class KeyValuePair implements BaseItem {
    private String mLabel;

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
}
