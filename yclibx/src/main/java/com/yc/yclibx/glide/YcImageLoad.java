package com.yc.yclibx.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 *
 */

public class YcImageLoad {


    @SuppressLint("CheckResult")
    private static RequestOptions createRequestOptions(YcImageLoadOptions builder) {
        RequestOptions requestOptions = new RequestOptions();
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
    public static void load(Context context, File file, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(file)
                .into(imageView);
    }
    public static void load(Context context, @DrawableRes int imgResId ,ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(imgResId)
                .into(imageView);
    }
    public static void load(Context context,String url, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .into(imageView);
    }

    public static void load(Context context,YcImageLoadOptions options) {
        RequestOptions requestOptions = createRequestOptions(options);
//        GlideApp.with(options.mContext)
//                .asBitmap()
//                .apply(requestOptions)
//                .load(options.imageLoading)
//                .into(options.imageView);
    }


    public static YcImageLoadOptions create() {
        return new YcImageLoadOptions();
    }

    public static void main(String[] args) {
//        Context context = null;
//        ImageView imageView = null;
//        String url = null;
//        String path = null;
//        YcImageLoad.load(context, new File(path), imageView);
//        YcImageLoad.create()
//                .setCacheDisk(true)
//                .setSignature("")
//                .load(context, new File(path), imageView);
    }
}
