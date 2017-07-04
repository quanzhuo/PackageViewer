package com.github.quanzhuo.pkgviewer;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PackageListActivity extends AppCompatActivity {
    private ListView mListView;
    private BaseAdapter mAdapter;
    private int mItemCount;
    private List<PackageInfo> mPkgList;

    public static final String PKG_INFO_KEY = "com.github.quanzhuo.pkgviewer.pkg_info_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);

        mListView = (ListView) findViewById(R.id.AppList);
        mPkgList = getNonSysPackages();
        mItemCount = mPkgList.size();

        mAdapter = new BaseAdapter() {
            private PackageManager manager = getPackageManager();

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
            public View getView(int i, View convertView, ViewGroup viewGroup) {
                PackageInfo packageInfo = mPkgList.get(i);
                ApplicationInfo appInfo = packageInfo.applicationInfo;
                ViewHolder holder;

                if (convertView == null) {
                    convertView = (LinearLayout) getLayoutInflater().inflate(R.layout.package_list_item, null);
                    holder = new ViewHolder();
                    holder.appIcon = convertView.findViewById(R.id.app_icon);
                    holder.appLabel = convertView.findViewById(R.id.app_label);
                    holder.appPackageName = convertView.findViewById(R.id.package_name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.appIcon.setImageDrawable(appInfo.loadIcon(manager));
                holder.appLabel.setText(appInfo.loadLabel(manager));
                holder.appPackageName.setText(appInfo.packageName);

                return convertView;
            }
        };

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PackageInfo info = mPkgList.get(i);
                Intent intent = new Intent(PackageListActivity.this, PackageOverviewActivity.class);
                intent.putExtra(PKG_INFO_KEY, info);
                startActivity(intent);
            }
        });
    }

    static class ViewHolder {
        ImageView appIcon;
        TextView appLabel;
        TextView appPackageName;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();
        switch (menu_id) {
            case R.id.show_sys_item:
                item.setChecked(!item.isChecked());
                if (item.isChecked())
                    mPkgList = getPackageManager().getInstalledPackages(0);
                else
                    mPkgList = getNonSysPackages();
                mItemCount = mPkgList.size();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(this, mItemCount + " Apps in Total", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<PackageInfo> getNonSysPackages() {
        List<PackageInfo> allApps = getPackageManager().getInstalledPackages(0);
        ArrayList<PackageInfo> nonSysApps = new ArrayList<>();
        for (PackageInfo info : allApps) {
            if (!isSystemApp(info))
                nonSysApps.add(info);
        }
        return nonSysApps;
    }

    private boolean isSystemApp(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & pkgInfo.applicationInfo.FLAG_SYSTEM) == 1;
    }
}
