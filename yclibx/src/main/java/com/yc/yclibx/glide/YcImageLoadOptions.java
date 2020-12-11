package com.yc.yclibx.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.signature.ObjectKey;
import com.yc.yclibx.YcUtilsInit;

/**
 *
 */

public class YcImageLoadOptions {
    @DrawableRes
    protected int imageError = YcUtilsInit.IMG_FAIL_ID_RES;
    @DrawableRes
    protected int imageLoading = YcUtilsInit.IMG_LOADING_ID_RES;
    protected ImageView imageView;
    protected boolean isCacheDisk;//是否磁盘缓存
    protected boolean isCacheMemory = true;//是否内存缓存
    protected ObjectKey mSignature = null;//用于标示同一个url，但图片不相同时的缓存区分
    protected YcImageViewTarget mImageViewTarget;

    public YcImageLoadOptions setImageViewTarget(YcImageViewTarget imageViewTarget) {
        this.mImageViewTarget = imageViewTarget;
        return this;
    }

    public YcImageLoadOptions setSignature(String signature) {
        this.mSignature = new ObjectKey(signature);
        return this;
    }

    public YcImageLoadOptions setCacheDisk(boolean cacheDisk) {
        isCacheDisk = cacheDisk;
        return this;
    }

    public YcImageLoadOptions setCacheMemory(boolean cacheMemory) {
        isCacheMemory = cacheMemory;
        return this;
    }

    public YcImageLoadOptions setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public YcImageLoadOptions setImageError(int imageError) {
        this.imageError = imageError;
        return this;
    }


    public YcImageLoadOptions setImageLoading(int imageLoading) {
        this.imageLoading = imageLoading;
        return this;
    }

    public interface Listeners {
        void call(String msg);
    }

    public interface Listeners2 {
        void call(Drawable drawable);
    }

    public interface Listeners3 {
        void call(Bitmap msg);
    }

}
