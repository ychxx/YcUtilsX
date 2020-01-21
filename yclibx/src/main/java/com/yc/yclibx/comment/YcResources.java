package com.yc.yclibx.comment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CpuUsageInfo;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.yclibx.YcUtilsInit;
import com.yc.yclibx.file.YcFileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * 用于获取资源(需初始化YcUtilsInit.init(x))
 */

public class YcResources {
    public static Resources getResources() {
        return YcUtilsInit.getResources();
    }

    /**
     * 获取颜色值
     *
     * @param resId 颜色资源id
     * @return 颜色值
     */
    @ColorInt
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色值
     *
     * @param color 十六进制
     * @return
     */
    @ColorInt
    public static int getColor(String color) {
        return Color.parseColor(color);
    }

    /**
     * 获取Drawable
     *
     * @param resId Drawable资源id
     * @return Drawable
     */
    @Deprecated
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取字符串
     *
     * @param resId 字符串资源id
     * @return 字符串
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取字符串数组
     *
     * @param resId 数组资源id
     * @return 字符串数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取字符串数组
     *
     * @param resId 数组资源id
     * @return 字符串数组
     */
    public static List<String> getStringList(int resId) {
        return Arrays.asList(getResources().getStringArray(resId));
    }

    /**
     * 获取assets里的图片
     *
     * @param imgName 图片的文件名
     */
    public static Bitmap getAssetsBitmap(String imgName) {
        Bitmap image = null;
        try {
            InputStream is = getResources().getAssets().open(imgName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 获取assets里的txt文件
     *
     * @param txtName txt文件的文件名
     */
    public static String getAssetsTxt(String txtName) {
        String txt = "";
        try {
            InputStream inputStream = getResources().getAssets().open(txtName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            txt = new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return txt;
    }

    /**
     * 获取assets里的json文件
     *
     * @param jsonName  json文件的文件名
     * @param className 解析用的类
     */
    public static <T> T getAssetsJson(String jsonName, Class<T> className) {
        String json = "";
        try {
            InputStream inputStream = getResources().getAssets().open(jsonName);
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            json = new String(buffer, "UTF-8");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(json, className);
    }

    /**
     * 获取assets里的txt文件(外层是集合的)
     *
     * @param jsonName  json文件的文件名
     * @param className 解析用的类
     */
    public static <T> List<T> getAssetsJsonList(String jsonName, Class<T> className) {
        String json = "";
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open(jsonName);
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            json = new String(buffer, "UTF-8");
            inputStream.close();
            inputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new Gson().fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * 复制assets文件夹内容
     *
     * @param assetsFolderPath assets文件夹路径
     * @param saveFolderPath   保存文件夹地址
     */
    public static boolean copyAssetsFolderToSD(Context context, String assetsFolderPath, String saveFolderPath) {
        boolean isSuccess = true;
        try {
            String[] fileNames = context.getAssets().list(assetsFolderPath);
            if (fileNames != null && fileNames.length > 0) {
                for (String fileName : fileNames) {
                    if (!copyAssetsFolderToSD(context, assetsFolderPath + File.separator + fileName, saveFolderPath + File.separator + fileName)) {
                        isSuccess = false;
                    }
                }
            } else {
                isSuccess = copyAssetsFileToSD(context, assetsFolderPath, saveFolderPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            YcLog.e("复制assets文件夹出错：" + assetsFolderPath);
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 复制assets文件内容
     *
     * @param assetsPath   assets文件路径
     * @param saveFilePath 保存文件路径（含文件名和后缀）
     */
    public static boolean copyAssetsFileToSD(Context context, String assetsPath, String saveFilePath) {
        try {
            File outFile = YcFileUtils.createFile(saveFilePath);
            InputStream is = context.getAssets().open(assetsPath);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            return true;
        } catch (Exception e) {
            YcLog.e("复制assets文件出错：" + assetsPath);
            e.printStackTrace();
            return false;
        }
    }

    public static void sendRefreshToSysPhone(Context context, String folderPath) {
        Uri uri = Uri.fromFile(new File(folderPath));
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * 创建view
     */
    public static View createView(Context context, @LayoutRes int layoutRes) {
        return LayoutInflater.from(context).inflate(layoutRes, null, false);
    }
}
