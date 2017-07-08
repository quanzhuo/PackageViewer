package com.github.quanzhuo.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 2017/7/6.
 */

public class Header extends BaseItem {
    private Drawable mDrawable;
    private String mLabel;
    private String mPackageName;

    public Header(Drawable drawable, String label, String packageName) {
        mDrawable = drawable;
        mLabel = label;
        mPackageName = packageName;
    }

    @Override
    public int getType() {
        return HEADER;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getLabel() {
        return mLabel;
    }
}
