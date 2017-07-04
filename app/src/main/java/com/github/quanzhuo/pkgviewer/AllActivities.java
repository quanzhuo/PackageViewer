package com.github.quanzhuo.pkgviewer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AllActivities extends AppCompatActivity {

    private ActivityInfo[] mActivityInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_activities);

        String packageName = getIntent().getStringExtra("packageName");
        try {
            mActivityInfos = getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) findViewById(R.id.all_activities);
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mActivityInfos.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                ViewHolder holder;
                if (view == null) {
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.activity_item, null);
                    holder = new ViewHolder();
                    holder.mTextView = (TextView) view.findViewById(R.id.class_name);
                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }
                holder.mTextView.setText(mActivityInfos[i].name);
                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    static class ViewHolder {
        TextView mTextView;
    }
}
