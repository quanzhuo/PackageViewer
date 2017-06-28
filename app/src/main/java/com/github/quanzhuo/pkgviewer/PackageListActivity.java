package com.github.quanzhuo.pkgviewer;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class PackageListActivity extends AppCompatActivity {
    private ListView mListView;
    private BaseAdapter mAdapter;
    private int mItemCount;
    private List<PackageInfo> packageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);

        mListView = (ListView) findViewById(R.id.AppList);
        packageList = getPackageManager().getInstalledPackages(0);
        mItemCount = packageList.size();

        mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mItemCount;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                PackageInfo packInfo = packageList.get(i);
                ApplicationInfo appInfo = packInfo.applicationInfo;
                PackageManager manager = getPackageManager();
                View layout = getLayoutInflater().inflate(R.layout.package_list_item, null);

                ImageView appIcon = (ImageView) layout.findViewById(R.id.app_icon);
                appIcon.setImageDrawable(appInfo.loadIcon(manager));
                TextView appLabel = (TextView) layout.findViewById(R.id.app_label);
                appLabel.setText(appInfo.loadLabel(manager));
                TextView appPackageName = (TextView) layout.findViewById(R.id.package_name);
                appPackageName.setText(appInfo.packageName);

                return layout;
            }
        };

        mListView.setAdapter(mAdapter);
    }
}
