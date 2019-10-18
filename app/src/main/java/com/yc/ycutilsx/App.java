package com.yc.ycutilsx;

import android.app.Application;

import com.yc.yclibx.YcUtilsInit;

/**
 *
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YcUtilsInit.init(this);
    }
}
