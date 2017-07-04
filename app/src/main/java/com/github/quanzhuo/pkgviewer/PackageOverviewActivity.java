package com.github.quanzhuo.pkgviewer;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static android.content.pm.PackageInfo.INSTALL_LOCATION_AUTO;
import static android.content.pm.PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY;
import static android.content.pm.PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL;

public class PackageOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_overview);

        Intent intent = getIntent();
        PackageInfo pkgInfo = intent.getExtras().getParcelable(PackageListActivity.PKG_INFO_KEY);
        ApplicationInfo appInfo = pkgInfo.applicationInfo;

        ImageView appIcon = (ImageView) findViewById(R.id.app_icon);
        appIcon.setImageDrawable(appInfo.loadIcon(getPackageManager()));
        TextView appLabel = (TextView) findViewById(R.id.app_label);
        appLabel.setText(appInfo.loadLabel(getPackageManager()));
        TextView appPackageName = (TextView) findViewById(R.id.package_name);
        appPackageName.setText(pkgInfo.packageName);

        // For manifest category
        TextView textView = (TextView) findViewById(R.id.overview_versioncode);
        textView.setText(pkgInfo.versionCode + "");
        textView = (TextView) findViewById(R.id.overview_versionname);
        textView.setText(pkgInfo.versionName);
        textView = (TextView) findViewById(R.id.overview_installlocation);
        textView.setText(getInstallLocation(pkgInfo.installLocation));
        textView = (TextView) findViewById(R.id.overview_shareduserid);
        textView.setText(pkgInfo.sharedUserId);
        textView = (TextView) findViewById(R.id.overview_shareduserlabel);
        textView.setText(pkgInfo.sharedUserLabel + "");
        textView = (TextView) findViewById(R.id.overview_firstInstallTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        textView.setText(dateFormat.format(new Date(pkgInfo.firstInstallTime)));
        textView = (TextView) findViewById(R.id.overview_lastUpdateTime);
        textView.setText(dateFormat.format(new Date(pkgInfo.lastUpdateTime)));

        // For application category
        textView = (TextView) findViewById(R.id.overview_allowBackup);
        textView.setText((appInfo.flags & appInfo.FLAG_ALLOW_BACKUP) == 1 ? "true" : "false");
        textView = (TextView) findViewById(R.id.overview_debuggable);
        textView.setText((appInfo.flags & appInfo.FLAG_DEBUGGABLE) == 1 ? "true" : "false");
        textView = (TextView) findViewById(R.id.overview_enabled);
        textView.setText(appInfo.enabled + "");
        textView = (TextView) findViewById(R.id.overview_isgame);
        textView.setText(isGame(appInfo) ? "true" : "false");
        textView = (TextView) findViewById(R.id.overview_process);
        textView.setText(appInfo.processName);
        textView = (TextView) findViewById(R.id.overview_theme);
        textView.setText(appInfo.theme + "");
        textView = (TextView) findViewById(R.id.overview_datadir);
        textView.setText(appInfo.dataDir);
        textView = (TextView) findViewById(R.id.overview_minSdkVersion);
        textView.setText(appInfo.minSdkVersion + "");
        textView = (TextView) findViewById(R.id.overview_nativeLibraryDir);
        textView.setText(appInfo.nativeLibraryDir);
        textView = (TextView) findViewById(R.id.overview_publicSourceDir);
        textView.setText(appInfo.publicSourceDir);
        textView = (TextView) findViewById(R.id.overview_sharedLibraryFiles);
        textView.setText(appInfo.sharedLibraryFiles != null ? appInfo.sharedLibraryFiles.toString() : null);
        textView = (TextView) findViewById(R.id.overview_sourceDir);
        textView.setText(appInfo.sourceDir);
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
}
