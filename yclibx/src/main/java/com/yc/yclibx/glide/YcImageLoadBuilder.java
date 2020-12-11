package com.yc.yclibx.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 *
 */

public class YcImageLoadBuilder extends YcImageLoadOptions implements IYcImage {
    private static RequestOptions mRequestOptionsDefault;

    @SuppressLint("CheckResult")
    private static RequestOptions createDefaultRequestOptions() {
        RequestOptions requestOptions = new RequestOptions();
        YcImageLoadBuilder builder = new YcImageLoadBuilder();
        if (builder.isCacheDisk) {
            requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        } else {
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        requestOptions.skipMemoryCache(builder.isCacheMemory);
        requestOptions.error(builder.imageError);
        requestOptions.placeholder(builder.imageLoading);//加载中的图片
        if (builder.mSignature != null) {
            requestOptions.signature(builder.mSignature);
        }
        return requestOptions;
    }

    public static RequestOptions getRequestOptionsDefault() {
        if (mRequestOptionsDefault == null) {
            mRequestOptionsDefault = createDefaultRequestOptions();
        }
        return mRequestOptionsDefault;
    }

    public static RequestOptions cloneRequestOptionsDefault() {
        if (mRequestOptionsDefault == null) {
            mRequestOptionsDefault = createDefaultRequestOptions();
        }
        return mRequestOptionsDefault.clone();
    }

    @Override
    public void load(Context context, File file, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(file)
                .into(imageView);
    }

    @Override
    public void load(Context context, int imgResId, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(imgResId)
                .into(imageView);
    }

    @Override
    public void load(Context context, String imgPath, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(imgPath)
                .into(imageView);
    }

    @Override
    public void load(File file) {

    }

    @Override
    public void load(int imgResId) {

    }

    @Override
    public void load(String imgPath) {

    }

}
