package com.adel2ne1.sender2ne1;

import android.graphics.drawable.Drawable;

/**
 * Created by adel on 20-01-2018.
 */

public class appinfo {
    boolean isseleted=false;
String  appname,apppackage,appsize;
    Drawable appimage;
    public appinfo(String appname,String apppackage,Drawable appimage,String appsize,boolean isseleted){
        this.apppackage=apppackage;
        this.appname=appname;
        this.isseleted=isseleted;
        this.appimage=appimage;
        this.appsize=appsize;
    }

    public void setIsseleted(boolean isseleted) {
        this.isseleted = isseleted;
    }
    public String getAppsize() {
        return appsize;
    }

    public void setAppsize(String appsize) {
        this.appsize = appsize;
    }

    public boolean getisselcted() {
        return isseleted;
    }
    public Drawable getAppimage() {
        return appimage;
    }

    public String getAppname() {
        return appname;
    }

    public String getApppackage() {
        return apppackage;
    }

    public void setAppimage(Drawable appimage) {
        this.appimage = appimage;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public void setApppackage(String apppackage) {
        this.apppackage = apppackage;
    }
}
