package com.example.lue.backdoorscanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.lue.backdoorscanner.utils.Help;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppDetail extends AppCompatActivity {

    TextView appLabel, packagename, version, features,privacylevel;
    TextView permissions, andVersion, installed, lastModify, path;
    PackageInfo packageInfo;
    PackageManager packagemanager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        findViewsById();
String appname= (String) getPackageManager().getApplicationLabel(getApplicationInfo());
        Log.d("Appname==",appname);
String packname=getPackageName();
Log.d("PackName<><>",packname);
String path=getPackageResourcePath();
Log.d("apk path<><><><>",path);
String path1=getApplicationInfo().sourceDir;
Log.d("apk path<><><><>",path1);
        try {
            PackageInfo packageInfo1=getPackageManager().getPackageInfo(getPackageName(),0);
            String version=packageInfo1.versionName;
            Log.d("version name<><><>",version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        packageInfo= Help.info;


        setValues();

    }

    private void findViewsById() {
        appLabel = (TextView) findViewById(R.id.applabel);
        packagename = (TextView) findViewById(R.id.package_name);
        version = (TextView) findViewById(R.id.version_name);
        features = (TextView) findViewById(R.id.req_feature);
        permissions = (TextView) findViewById(R.id.req_permission);
        andVersion = (TextView) findViewById(R.id.andversion);
        path = (TextView) findViewById(R.id.path);
        installed = (TextView) findViewById(R.id.insdate);
        lastModify = (TextView) findViewById(R.id.last_modify);
        privacylevel=(TextView)findViewById(R.id.tvprotectionlevel);
    }
private void setValues(){
        //Privacy level
   PermissionInfo[] permissionarray= packageInfo.permissions;
    if(permissionarray!=null){ for(int i=0;i<packageInfo.permissions.length;i++){

        if(packageInfo.permissions[i].protectionLevel== PermissionInfo.PROTECTION_DANGEROUS){
            privacylevel.setText("Risk");
            privacylevel.setTextColor(Color.RED);

        }
        else if(packageInfo.permissions[i].protectionLevel==PermissionInfo.PROTECTION_SIGNATURE ||packageInfo.permissions[i].protectionLevel==PermissionInfo.PROTECTION_SIGNATURE_OR_SYSTEM){
            privacylevel.setTextColor(getResources().getColor(R.color.warning));
            privacylevel.setText("Warning");
        }
        else{
            privacylevel.setText("Normal");
            privacylevel.setTextColor(Color.GREEN);
        }
    }}
    else{
        privacylevel.setText("Normal");
        privacylevel.setTextColor(Color.GREEN);
    }

        //App name
    appLabel.setText(getPackageManager().getApplicationLabel(packageInfo.applicationInfo));
    // Package Name
    packagename.setText(packageInfo.packageName);
    //version Name
    version.setText(packageInfo.versionName);

//     features
    if(packageInfo.reqFeatures!=null){
        features.setText(getFeatures(packageInfo.reqFeatures));

    }else{
        features.setText("-");
    }
//    permissions
    if(packageInfo.requestedPermissions!=null){
        permissions.setText(getPermission(packageInfo.requestedPermissions));
    }else{
        permissions.setText("-");
    }
//     target version name
    andVersion.setText(String.valueOf(packageInfo.applicationInfo.targetSdkVersion));
    //installation path
    path.setText(packageInfo.applicationInfo.sourceDir);
    //first installation
    installed.setText(setDateFormat(packageInfo.firstInstallTime));
    lastModify.setText(setDateFormat(packageInfo.lastUpdateTime));
}


    @SuppressLint("SimpleDateFormat")
    private String setDateFormat(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = formatter.format(date);
        return strDate;
    }
private String getPermission (String[] data){
    StringBuffer buffer=new StringBuffer();

    for(int i=0;i<data.length;i++){
        buffer.append(data[i]+','+'\n');
    }
    return buffer.toString();
}
private String getFeatures(FeatureInfo[] data){
    StringBuffer buffer=new StringBuffer();
    for(int i=0;i<data.length;i++){
        buffer.append(data[i]);
    }
    return buffer.toString();
}

}
