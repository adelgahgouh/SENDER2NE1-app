package com.adel2ne1.sender2ne1;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {


GridView lv;
    Toolbar    toolbar;
    ArrayList<appinfo> listapps=new ArrayList<appinfo>() ;
    ArrayAdapter<appinfo> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);
        setTitle("SENDER 2NE1");

        lv=(GridView)findViewById(R.id.gv);
        listapps=getInstalledApps();

        arrayAdapter=new ArrayAdapter<appinfo>(getApplicationContext(),R.layout.itemlist,listapps){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                // View g=convertView;

                if (view == null) {
                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                    view = layoutInflater.inflate(R.layout.itemlist, null);
                }
                TextView tvappname = (TextView) view.findViewById(R.id.tvappname);
                TextView tvappsize = (TextView) view.findViewById(R.id.tvappsize);
                ImageView img = (ImageView) view.findViewById(R.id.imgapp);
                img.setImageDrawable(listapps.get(position).getAppimage());
                tvappname.setText(listapps.get(position).getAppname().toString());
                tvappsize.setText(listapps.get(position).getAppsize().toString());


                return view;
            }
        };

        lv.setAdapter(arrayAdapter);



lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
String apkurl=getapkurl(listapps.get(position).getApppackage());
                sendViaBleutooth(apkurl);
            }
        });
}

    public String getapkurl(String packagename) {
        String d = null;


        PackageManager pm = getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(packagename, 0);
            String sourceApk = ai.publicSourceDir;
            d = sourceApk;
            //d=new File(sourceApk);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d;
    }
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    public void sendViaBleutooth(String path) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("application/*");
       // sharingIntent.setPackage("com.android.bluetooth");

        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        startActivity(Intent.createChooser(sharingIntent, "Sending apk "));
    }
    private ArrayList<appinfo> getInstalledApps() {
        List<appinfo> res = new ArrayList<appinfo>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((isSystemPackage(p) == false)) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                String apppackage=p.packageName;
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());

                final PackageManager pm = getApplicationContext().getPackageManager();
                ApplicationInfo applicationInfo = null;
                try {
                    applicationInfo = pm.getApplicationInfo(apppackage, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                File file = new File(applicationInfo.publicSourceDir);
                double size = file.length();
                String appsize=calculate(size);
                res.add(new appinfo(appName,apppackage, icon,appsize,false));
            }
        }
        return (ArrayList<appinfo>) res;
    }
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }

    private  String filesize_in_megaBytes(File file) {
        return (double) file.length()/(1024*1024)+" mb";
    }

    private  String filesize_in_kiloBytes(File file) {
        return (double) file.length()/1024+"  kb";
    }

    private  String filesize_in_Bytes(File file) {
        return file.length()+" bytes";
    }
    String calculate(double filesize){
        double fileLength=filesize;
        String result=null;
        int mb=1024*1024,kb=1024,byt=1;
        double inmb=fileLength/(1024*1024);
        double inkb=fileLength/1024;
        double inb=fileLength;
        if((fileLength/mb)>=1){
            result=new DecimalFormat("#.##").format(inmb)+" Mb";
        }else{
            if((fileLength/kb)>=1){
                result=new DecimalFormat("#.##").format(inkb)+" Kb";
            }else{
                result=new DecimalFormat("#.##").format(inb)+" byte";
            }
        }
      /*  int kbyte=1024;
        int mb=kbyte*1024;
        int gb=mb*1024;
        if((fileLength/kbyte)>1){
            if((fileLength/mb)>1){
                if((fileLength/gb)>1){

                    result =new DecimalFormat("#.##").format(fileLength/gb)+" GB";

                }else{
                    result =new DecimalFormat("#.##").format(fileLength/mb)+" MB";
                }
            }else{
                result =new DecimalFormat("#.##").format(fileLength/mb)+" KB";
            }
        }

*/


        return  result;
    }
}
