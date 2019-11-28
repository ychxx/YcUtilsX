package com.yc.yclibx.comment;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;

import com.yc.yclibx.YcUtilsInit;

/**
 *
 */

public class YcUI {
    /**
     * * 显示或隐藏StatusBar
     *
     * @param enable false 显示，true 隐藏
     */
    public static void hideStatusBar(Window window, boolean enable) {
        WindowManager.LayoutParams p = window.getAttributes();
        if (enable)
        //|=：或等于，取其一
        {
            p.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else
        //&=：与等于，取其二同时满足，     ~ ： 取反
        {
            p.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        window.setAttributes(p);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 将dp值转换为px值
     */
    public static int dpToPx(float dp) {
        return (int) (YcUtilsInit.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    /**
     * 将px值转换为dp值
     */
    public static int pxToDp(float px) {
        return (int) (px / YcUtilsInit.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * sp转px
     */
    public static int spToPx(float sp) {
        return (int) (sp * YcUtilsInit.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    /**
     * px转sp
     */
    public static int pxToSp(float px) {
        return (int) (YcUtilsInit.getResources().getDisplayMetrics().scaledDensity / px + 0.5f);
    }

    /**
     * 获取屏幕宽度 像素值
     */
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度 像素值
     */
    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getDisplayMetrics(YcUtilsInit.getApplication());
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
    public static View createView(Context context, @LayoutRes int layoutRes) {
        return LayoutInflater.from(context).inflate(layoutRes, null, false);
    }
}
