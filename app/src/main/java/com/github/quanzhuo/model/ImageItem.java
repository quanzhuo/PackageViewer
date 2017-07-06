package com.github.quanzhuo.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 2017/7/6.
 */

public class ImageItem implements BaseItem {
    private Drawable mDrawable;
    private String mLabel;
    private String mPackageName;

    public ImageItem(Drawable drawable, String label, String packageName) {
        mDrawable = drawable;
        mLabel = label;
        mPackageName = packageName;
    }

    @Override
    public int getType() {
        return ITEM_WITH_IMAGE;
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
