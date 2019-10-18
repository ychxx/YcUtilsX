package com.yc.yclibx.comment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.yc.yclibx.YcUtilsInit;
import com.yc.yclibx.permissions.YcUtilPermission;
import java.io.File;
/**
 * app版本相关信息
 */

public class YcUtilVersion {
    /**
     * 获取版本号
     */
    public static int getVersionCode() {
        if (YcUtilsInit.getApplication() == null) {
            YcLog.e("请先初始化，YcUtilsInit.init(Application)");
            return -1;
        }
        return getVersionCode(YcUtilsInit.getApplication());
    }

    /**
     * 获取版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取版本名
     */
    public static String getVersionName() {
        if (YcUtilsInit.getApplication() == null) {
            YcLog.e("请先初始化，YcUtilsInit.init(Application)");
            return "";
        }
        return getVersionName(YcUtilsInit.getApplication());
    }

    /**
     * 获取版本名
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取包名
     */
    public static String getPackageName() {
        if (YcUtilsInit.getApplication() == null) {
            YcLog.e("请先初始化，YcUtilsInit.init(Application)");
            return "";
        }
        return getPackageName(YcUtilsInit.getApplication());
    }

    /**
     * 获取包名
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取手机IMEI(设备id)
     * 需要Manifest.permission.READ_PHONE_STATE权限
     *
     * @param activity
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(AppCompatActivity activity) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (YcUtilPermission.newInstance(activity).isHasPermission(Manifest.permission.READ_PHONE_STATE)) {
                return YcEmpty.getNoEmpty(telephonyManager.getDeviceId());
            } else {
                YcLog.e("没有" + Manifest.permission.READ_PHONE_STATE + "权限，无法获取imei值");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取手机Sn（设备序列号）
     */
    public static String getSn(Context context) {
        return android.os.Build.SERIAL;
    }

    /**
     * 获取手机IMSI（sim卡序列号）
     * 需要Manifest.permission.READ_PHONE_STATE权限
     */
    @SuppressLint("MissingPermission")
    public static String getIMSI(AppCompatActivity activity) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            if (YcUtilPermission.newInstance(activity).isHasPermission(Manifest.permission.READ_PHONE_STATE)) {
                //获取IMSI号
                return YcEmpty.getNoEmpty(telephonyManager.getSubscriberId());
            } else {
                YcLog.e("没有" + Manifest.permission.READ_PHONE_STATE + "权限，无法获取IMSI值");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 安装apk
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !YcUtilPermission.newInstance((AppCompatActivity) context).isHasPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)) {
            YcLog.e("缺少 REQUEST_INSTALL_PACKAGES 权限无法安装Apk");
        }
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(context, getPackageName(context) + ".fileprovider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
