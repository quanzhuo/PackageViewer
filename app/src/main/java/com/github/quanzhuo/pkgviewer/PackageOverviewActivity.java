package com.github.quanzhuo.pkgviewer;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.content.pm.PackageInfo.INSTALL_LOCATION_AUTO;
import static android.content.pm.PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY;
import static android.content.pm.PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL;

public class PackageOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_overview);

        Intent intent = getIntent();
        PackageInfo info = intent.getExtras().getParcelable(PackageListActivity.PKG_INFO_KEY);

        ImageView appIcon = (ImageView) findViewById(R.id.app_icon_overview);
        appIcon.setImageDrawable(info.applicationInfo.loadIcon(getPackageManager()));
        TextView appLabel = (TextView) findViewById(R.id.app_label_overview);
        appLabel.setText(info.applicationInfo.loadLabel(getPackageManager()));

        TextView textView = (TextView) findViewById(R.id.overview_package);
        textView.setText(info.packageName);
        textView = (TextView) findViewById(R.id.overview_versioncode);
        textView.setText(info.versionCode + "");
        textView = (TextView) findViewById(R.id.overview_versionname);
        textView.setText(info.versionName);
        textView = (TextView) findViewById(R.id.overview_installlocation);
        textView.setText(getInstallLocation(info.installLocation));
        textView = (TextView) findViewById(R.id.overview_shareduserid);
        textView.setText(info.sharedUserId);
        textView = (TextView) findViewById(R.id.overview_shareduserlabel);
        textView.setText(info.sharedUserLabel + "");
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
        }
        return location;
    }
}
