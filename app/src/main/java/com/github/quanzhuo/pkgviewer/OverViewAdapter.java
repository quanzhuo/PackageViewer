package com.github.quanzhuo.pkgviewer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.quanzhuo.model.BaseItem;
import com.github.quanzhuo.model.ImageItem;
import com.github.quanzhuo.model.KeyValuePair;
import com.github.quanzhuo.model.Section;

import java.util.ArrayList;

/**
 * Created by Admin on 2017/7/6.
 */

public class OverViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BaseItem> mBaseItems;

    public OverViewAdapter(Context context, ArrayList<BaseItem> baseItems) {
        super();
        mContext = context;
        mBaseItems = baseItems;
    }

    @Override
    public int getItemViewType(int position) {
        return mBaseItems.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getCount() {
        return mBaseItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mBaseItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageItemHolder holder1 = null;
        SectionHolder holder2 = null;
        KeyValueHolder holder3 = null;

        int viewType = getItemViewType(i);

        switch (viewType) {
            case BaseItem.DIVIDER:
                if (view == null) {
                    LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                    view = inflater.inflate(R.layout.divider, null);
                }
                break;
            case BaseItem.SECTION:
                if (view == null) {
                    LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                    view = inflater.inflate(R.layout.section, null);
                    holder2 = new SectionHolder();
                    holder2.mSectionTitle = view.findViewById(R.id.section_title);
                    view.setTag(holder2);
                } else {
                    holder2 = (SectionHolder) view.getTag();
                }
                Section label = (Section) mBaseItems.get(i);
                holder2.mSectionTitle.setText(label.getLabel());
                break;
            case BaseItem.KEY_VALUE_PAIR:
                if (view == null) {
                    LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                    view = inflater.inflate(R.layout.key_value, null);
                    holder3 = new KeyValueHolder();
                    holder3.mKey = view.findViewById(R.id.overview_key);
                    holder3.mValue = view.findViewById(R.id.overview_value);
                    view.setTag(holder3);
                } else {
                    holder3 = (KeyValueHolder) view.getTag();
                }
                KeyValuePair pair = (KeyValuePair) mBaseItems.get(i);
                holder3.mKey.setText(pair.getLabel());
                holder3.mValue.setText(pair.getValue());
                break;
            case BaseItem.ITEM_WITH_IMAGE:
                if (view == null) {
                    LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                    view = inflater.inflate(R.layout.package_list_item, null);
                    holder1 = new ImageItemHolder();
                    holder1.mImageView = view.findViewById(R.id.app_icon);
                    holder1.mLabel = view.findViewById(R.id.app_label);
                    holder1.mPackageName = view.findViewById(R.id.package_name);
                    view.setTag(holder1);
                } else {
                    holder1 = (ImageItemHolder) view.getTag();
                }
                ImageItem imageItem = (ImageItem) mBaseItems.get(i);
                holder1.mImageView.setImageDrawable(imageItem.getDrawable());
                holder1.mLabel.setText(imageItem.getLabel());
                holder1.mPackageName.setText(imageItem.getPackageName());
        }
        return view;
    }

    static class SectionHolder {
        TextView mSectionTitle;
    }

    static class KeyValueHolder {
        TextView mKey;
        TextView mValue;
    }

    static class ImageItemHolder {
        ImageView mImageView;
        TextView mLabel;
        TextView mPackageName;
    }
}
