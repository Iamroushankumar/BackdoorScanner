package com.example.lue.backdoorscanner.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lue.backdoorscanner.R;

import java.util.List;

/**
 * Created by lue on 3/2/18.
 */

public class AppList extends BaseAdapter {
    List<PackageInfo> packageList;
    Activity context;
    LayoutInflater inflater;
    PackageManager packageManager;

    public AppList(Activity context, List<PackageInfo> packageList,PackageManager packageManager) {
        super();
        this.context = context;
        this.packageList = packageList;
        inflater=LayoutInflater.from(context);
        this.packageManager = packageManager;
    }



    public int getCount() {
        return packageList.size();
    }

    public Object getItem(int position) {
        return packageList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        TextView titl,package_name;
        ImageView app_logo,status;
        boolean isSet;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi;
        boolean status_danger=false;
        boolean status_normal=true;
        boolean status_signature=false;
        boolean status_signature0rsystem=false;
       final ViewHolder holder;
        if(inflater==null){
           inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            vi=inflater.inflate(R.layout.single_row,null);
            holder=new ViewHolder();
            holder.titl =(TextView)vi.findViewById(R.id.app_title);
//            holder. package_name=(TextView)vi.findViewById(R.id.package_name);
            holder. app_logo=(ImageView)vi.findViewById(R.id.app_icon);
            holder. status=(ImageView)vi.findViewById(R.id.status);
            holder.status.setTag(position);
            vi.setTag(holder);
        }
        else{
            vi=convertView;
            holder=(ViewHolder) vi.getTag();
        }

        PackageInfo packageinfo=packageList.get(position);
      PermissionInfo [] permissionarray=  packageinfo.permissions;
      if(permissionarray!=null){
boolean status=true;
if(status){
    for(int i=0;i<packageinfo.permissions.length;i++){

    switch(packageinfo.permissions[i].protectionLevel){
        case PermissionInfo.PROTECTION_DANGEROUS:

            holder. status.setImageResource(R.drawable.danger);
            status=false;
            break;
        case PermissionInfo.PROTECTION_SIGNATURE :
            holder. status.setImageResource(R.drawable.warning);
            break;
        case PermissionInfo.PROTECTION_SIGNATURE_OR_SYSTEM:
            holder. status.setImageResource(R.drawable.warning);
            break;
        default:
            holder.status.setImageResource(R.drawable.good);


    }


}}
      }
else {holder.status.setImageResource(R.drawable.good);}


        Drawable appIcon=packageManager.getApplicationIcon(packageinfo.applicationInfo);
        String appname=packageManager.getApplicationLabel(packageinfo.applicationInfo).toString();
        String packagename=packageinfo.packageName;
       holder. titl.setText(appname);
       holder. app_logo.setImageDrawable(appIcon);
//       holder. package_name.setText(packagename);
        return vi;
    }


}
