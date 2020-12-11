package com.yc.yclibx;

import android.app.Application;
import android.content.res.Resources;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;

import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.file.YcImgUtils;

import org.xutils.x;

/**
 * 初始化作用
 */

public class YcUtilsInit {
    private static Application mApplication = null;
    /**
     * 加载网络图片失败时显示的图片
     */
    public static int IMG_FAIL_ID_RES = R.drawable.img_loading;
    /**
     * 加载网络图片加载时显示的图片
     */
    public static int IMG_LOADING_ID_RES = R.drawable.img_loading;


    /**
     * 加载网络图片失败重新加载的次数
     */
    public static int IMG_FAIL_RELOAD_NUM = 0;

    /**
     * 使用PrefHelper、下载必须先初始化
     *
     * @param application
     */
    public static void init(Application application) {
        mApplication = application;
        //下载XUtils3初始化
        x.Ext.init(application);
        x.Ext.setDebug(false); // 是否输出debug日志, 开启debug会影响性能.
    }

    /**
     * 设置加载网络图片时失败重新加载的次数
     */
    public static void setReloadImgNum(int num) {
        if (num < 0) {
            YcLog.d("设置的失败重新加载次数小于0，按不重新加载执行");
        }
        IMG_FAIL_RELOAD_NUM = num;
    }

    /**
     * 设置加载网络图片失败时显示的图片
     */
    public static void setLoadImgDefaultFail(@DrawableRes int imgIdRes) {
        IMG_FAIL_ID_RES = imgIdRes;
    }

    /**
     * 设置加载网络图片时显示的图片
     */
    public static void setLoadImgDefaultLoading(@DrawableRes int imgIdRes) {
        IMG_LOADING_ID_RES = imgIdRes;
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static Resources getResources() {
        if (mApplication == null) {
            return Resources.getSystem();
        } else {
            return mApplication.getResources();
        }
    }
}
