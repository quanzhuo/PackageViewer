package com.github.quanzhuo.model;

/**
 * Created by Admin on 2017/7/6.
 */

public class Section extends BaseItem {
    private String mLabel;

    public Section(String label) {
        mLabel = label;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int getType() {
        return SECTION;
    }

    public String getLabel() {
        return mLabel;
    }
}
