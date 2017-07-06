package com.github.quanzhuo.pkgviewer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.github.quanzhuo.model.BaseItem;
import com.github.quanzhuo.model.Divider;
import com.github.quanzhuo.model.ImageItem;
import com.github.quanzhuo.model.KeyValuePair;
import com.github.quanzhuo.model.Section;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.pm.PackageInfo.INSTALL_LOCATION_AUTO;
import static android.content.pm.PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY;
import static android.content.pm.PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL;

public class PackageOverviewActivity extends AppCompatActivity {
    private ActivityInfo[] activityInfos;
    private ServiceInfo[] serviceInfos;
    private ActivityInfo[] receiverInfos;
    private ProviderInfo[] providerInfos;

    private ArrayList<BaseItem> mListViewData;
    private OverViewAdapter mOverViewAdapter;
    private ListView mListView;
    private PackageInfo mPackageInfo;
    private ApplicationInfo mApplicationInfo;
    private PackageManager mPackageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_overview);

        Intent intent = getIntent();
        mPackageInfo = intent.getExtras().getParcelable(PackageListActivity.PKG_INFO_KEY);
        mApplicationInfo = mPackageInfo.applicationInfo;
        mPackageManager = getPackageManager();

        mListViewData = new ArrayList<>();
        initDataSet();

        mListView = (ListView) findViewById(R.id.list_view);
        mOverViewAdapter = new OverViewAdapter(this, mListViewData);
        mListView.setAdapter(mOverViewAdapter);

        try {
            activityInfos = mPackageManager.getPackageInfo(mPackageInfo.packageName,
                    PackageManager.GET_ACTIVITIES).activities;
            serviceInfos = mPackageManager.getPackageInfo(mPackageInfo.packageName,
                    PackageManager.GET_SERVICES).services;
            receiverInfos = mPackageManager.getPackageInfo(mPackageInfo.packageName,
                    PackageManager.GET_RECEIVERS).receivers;
            providerInfos = mPackageManager.getPackageInfo(mPackageInfo.packageName,
                    PackageManager.GET_PROVIDERS).providers;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BaseItem item = mListViewData.get(i);
                Intent intent = null;
                if (item instanceof KeyValuePair) {
                    switch (((KeyValuePair) item).getDetails()) {
                        case KeyValuePair.ACTIVITY:
                            if (activityInfos != null) {
                                intent = new Intent(PackageOverviewActivity.this, AllActivities.class);
                                intent.putExtra("packageName", mPackageInfo.packageName);
                                startActivity(intent);
                            }
                            break;
                        case KeyValuePair.SERVICE:
                            if (serviceInfos != null) {
                                intent = new Intent(PackageOverviewActivity.this, AllServices.class);
                                intent.putExtra("packageName", mPackageInfo.packageName);
                                startActivity(intent);
                            }
                            break;
                        case KeyValuePair.RECIVER:
                            if (receiverInfos != null) {
                                intent = new Intent(PackageOverviewActivity.this, AllReceivers.class);
                                intent.putExtra("packageName", mPackageInfo.packageName);
                                startActivity(intent);
                            }
                            break;
                        case KeyValuePair.PROVIDER:
                            if (providerInfos != null) {
                                intent = new Intent(PackageOverviewActivity.this, AllProviders.class);
                                intent.putExtra("packageName", mPackageInfo.packageName);
                                startActivity(intent);
                            }
                    }
                }
            }
        });
    }

    private String getInstallLocation(int flag) {
        String location = null;
        switch (flag) {
            case INSTALL_LOCATION_AUTO:
                location = "auto";
                break;
            case INSTALL_LOCATION_INTERNAL_ONLY:
                location = "internalOnly";
                break;
            case INSTALL_LOCATION_PREFER_EXTERNAL:
                location = "preferExternal";
                break;
            default:
                location = "internalOnly";
        }
        return location;
    }

    private boolean isGame(ApplicationInfo appInfo) {
        boolean result;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            result = appInfo.category == appInfo.CATEGORY_GAME;
        } else {
            result = (appInfo.flags & appInfo.FLAG_IS_GAME) == 1;
        }

        return result;
    }

    private void initDataSet() {
        mListViewData.add(new ImageItem(mApplicationInfo.loadIcon(mPackageManager),
                mApplicationInfo.loadLabel(mPackageManager).toString(),
                mPackageInfo.packageName));
        mListViewData.add(new Divider());
        mListViewData.add(new Section("Manifest"));
        mListViewData.add(new KeyValuePair("android:versionCode") {
            @Override
            public String getValue() {
                return mPackageInfo.versionCode + "";
            }
        });
        mListViewData.add(new KeyValuePair("android:versionName") {
            @Override
            public String getValue() {
                return mPackageInfo.versionName;
            }
        });
        mListViewData.add(new KeyValuePair("android:installLocation") {
            @Override
            public String getValue() {
                return getInstallLocation(mPackageInfo.installLocation);
            }
        });
        mListViewData.add(new KeyValuePair("android:sharedUserId") {
            @Override
            public String getValue() {
                return mPackageInfo.sharedUserId;
            }
        });
        mListViewData.add(new KeyValuePair("android:sharedUserLabel") {
            @Override
            public String getValue() {
                return mPackageInfo.sharedUserLabel + "";
            }
        });
        mListViewData.add(new KeyValuePair("firstInstallTime") {
            @Override
            public String getValue() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault());
                return dateFormat.format(new Date(mPackageInfo.firstInstallTime));
            }
        });
        mListViewData.add(new KeyValuePair("lastUpdateTime") {
            @Override
            public String getValue() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault());
                return dateFormat.format(new Date(mPackageInfo.firstInstallTime));
            }
        });
        mListViewData.add(new Divider());
        mListViewData.add(new Section("Application"));
        mListViewData.add(new KeyValuePair("android:allowBackup") {
            @Override
            public String getValue() {
                return (mApplicationInfo.flags & mApplicationInfo.FLAG_ALLOW_BACKUP) == 1
                        ? "true" : "false";
            }
        });
        mListViewData.add(new KeyValuePair("android:debuggable") {
            @Override
            public String getValue() {
                return (mApplicationInfo.flags & mApplicationInfo.FLAG_DEBUGGABLE) == 1
                        ? "true" : "false";
            }
        });
        mListViewData.add(new KeyValuePair("android:enabled") {
            @Override
            public String getValue() {
                return (mApplicationInfo.flags & mApplicationInfo.FLAG_DEBUGGABLE) == 1
                        ? "true" : "false";
            }
        });
        mListViewData.add(new KeyValuePair("android:isGame") {
            @Override
            public String getValue() {
                return isGame(mApplicationInfo) ? "true" : "false";
            }
        });
        mListViewData.add(new KeyValuePair("android:process") {
            @Override
            public String getValue() {
                return mApplicationInfo.processName;
            }
        });
        mListViewData.add(new KeyValuePair("android:theme") {
            @Override
            public String getValue() {
                return mApplicationInfo.theme + "";
            }
        });
        mListViewData.add(new KeyValuePair("minSdkVersion") {
            @Override
            public String getValue() {
                return Build.VERSION.SDK_INT >= 24 ? mApplicationInfo.minSdkVersion + "" : "N/A";
            }
        });
        mListViewData.add(new KeyValuePair("dataDir") {
            @Override
            public String getValue() {
                return mApplicationInfo.dataDir;
            }
        });
        mListViewData.add(new KeyValuePair("nativeLibraryDir") {
            @Override
            public String getValue() {
                return mApplicationInfo.nativeLibraryDir;
            }
        });
        mListViewData.add(new KeyValuePair("publicSourceDir") {
            @Override
            public String getValue() {
                return mApplicationInfo.publicSourceDir;
            }
        });
        mListViewData.add(new KeyValuePair("sharedLibraryFiles") {
            @Override
            public String getValue() {
                return mApplicationInfo.sharedLibraryFiles != null ?
                        mApplicationInfo.sharedLibraryFiles.toString() : null;
            }
        });
        mListViewData.add(new KeyValuePair("sourceDir") {
            @Override
            public String getValue() {
                return mApplicationInfo.sourceDir;
            }
        });
        mListViewData.add(new Divider());
        mListViewData.add(new Section("activities"));
        mListViewData.add(new KeyValuePair("totally") {
            @Override
            public String getValue() {
                return activityInfos == null ? "0" : activityInfos.length + "";
            }

            @Override
            public int getDetails() {
                return ACTIVITY;
            }
        });
        mListViewData.add(new Divider());
        mListViewData.add(new Section("Services"));
        mListViewData.add(new KeyValuePair("totally") {

            @Override
            public String getValue() {
                return serviceInfos == null ? "0" : serviceInfos.length + "";
            }

            @Override
            public int getDetails() {
                return SERVICE;
            }
        });
        mListViewData.add(new Divider());
        mListViewData.add(new Section("receivers"));
        mListViewData.add(new KeyValuePair("totally") {
            @Override
            public String getValue() {
                return receiverInfos == null ? "0" : receiverInfos.length + "";
            }

            @Override
            public int getDetails() {
                return RECIVER;
            }
        });
        mListViewData.add(new Divider());
        mListViewData.add(new Section("providers"));
        mListViewData.add(new KeyValuePair("totally") {
            @Override
            public String getValue() {
                return providerInfos == null ? "0" : providerInfos.length + "";
            }

            @Override
            public int getDetails() {
                return PROVIDER;
            }
        });
    }
}
