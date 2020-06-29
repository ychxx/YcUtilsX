package com.yc.ycutilsx;

import android.app.Application;
import android.content.Context;

import com.yc.yclibx.YcUtilsInit;
import com.yc.ycutilsx.input.InputMethodHolder;

/**
 *
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YcUtilsInit.init(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        InputMethodHolder.init(base);
        super.attachBaseContext(base);
    }
}
