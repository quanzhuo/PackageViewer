package com.github.quanzhuo.model;

/**
 * Created by Admin on 2017/7/6.
 */

public interface BaseItem {
    int DIVIDER = 0;
    int SECTION = 1;
    int KEY_VALUE_PAIR = 2;
    int ITEM_WITH_IMAGE = 3;
    int getType();
}
