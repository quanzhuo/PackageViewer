package com.github.quanzhuo.model;

/**
 * Created by Admin on 2017/7/6.
 */

public abstract class BaseItem {
    public static final int DIVIDER = 0;
    public static final int SECTION = 1;
    public static final int KEY_VALUE_PAIR = 2;
    public static final int HEADER = 3;

    protected boolean enabled = false;

    public abstract int getType();
    public abstract boolean isEnabled();
}
