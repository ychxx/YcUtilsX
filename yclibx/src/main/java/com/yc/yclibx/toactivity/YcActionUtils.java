package com.yc.yclibx.toactivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.comment.YcUtilVersion;

import java.io.File;
import java.util.List;

/**
 * 跳转到其他应用
 */

public class YcActionUtils {
    /**
     * 打开文件管理器
     *
     * @param activity 页面
     * @param fileType 限制打开的文件类型
     * @param request  请求码
     */
    public static void openFileManager(Activity activity, String fileType, int request) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(fileType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(Intent.createChooser(intent, "打开文件的方式"), request);
    }

    /**
     * 拨打电话
     *
     * @param activity 页面
     * @param telNum   电话号码
     */
    public static void openTelephone(Activity activity, String telNum, int request) {
        Uri uri = Uri.parse("tel:" + telNum.trim());//去掉空格
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        activity.startActivityForResult(intent, request);
    }

    /**
     * 打开网页
     *
     * @param activity 页面
     * @param webUrl   网页地址
     */
    public static void openWeb(Activity activity, String webUrl, int request) {
        Uri uri = Uri.parse(webUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivityForResult(Intent.createChooser(intent, "打开网页的方式"), request);
    }

    /**
     * 跳转到系统设置页面
     *
     * @param activity 页面
     */
    public static void openSetting(Activity activity, int request) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivityForResult(intent, request);
    }

    /**
     * 跳转到App信息页面
     *
     * @param activity 页面
     */
    public static void openAppInfo(Activity activity, int request) {
        Uri packageURI = Uri.parse("package:" + activity.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        activity.startActivityForResult(intent, request);
    }

    public static void openSms(Activity activity, String savePath, int request) {

    }

    /**
     * 启动系统照相机
     *
     * @param activity
     * @param saveImgFile 照片保存
     * @param request     请求码
     */
    public static void openCamera(Activity activity, File saveImgFile, int request) {
        Uri outPutUri;
        if (Build.VERSION.SDK_INT >= 23) {
            outPutUri = FileProvider.getUriForFile(activity, YcUtilVersion.getPackageName(activity) + ".fileprovider", saveImgFile);
        } else {
            outPutUri = Uri.fromFile(saveImgFile);
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);//将拍取的照片保存到指定URI

        List result = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
        if (result.isEmpty()) {
            Toast.makeText(activity, "没有照相机", Toast.LENGTH_SHORT).show();
            YcLog.e("没有照相机");
        } else {
            activity.startActivityForResult(intent, request);

        }
    }
}
