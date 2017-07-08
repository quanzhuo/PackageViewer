package com.github.quanzhuo.model;

/**
 * Created by Admin on 2017/7/6.
 */

public class Divider extends BaseItem {
    @Override
    public int getType() {
        return DIVIDER;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
