package com.github.quanzhuo.pkgviewer;

import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AllProviders extends AppCompatActivity {

    private ProviderInfo[] mProviderInfos;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_activities);
        String packageName = getIntent().getStringExtra("packageName");
        try {
            mProviderInfos = getPackageManager().getPackageInfo(packageName, PackageManager.GET_PROVIDERS).providers;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mListView = (ListView) findViewById(R.id.all_activities);
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mProviderInfos.length;
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
                    holder = new ViewHolder();
                    view = getLayoutInflater().inflate(R.layout.activity_item, null);
                    holder.mTextView = view.findViewById(R.id.class_name);
                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }

                holder.mTextView.setText(mProviderInfos[i].name);

                return view;
            }
        });
    }

    static class ViewHolder {
        TextView mTextView;
    }
}
