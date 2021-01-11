package com.yc.yclibx.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

    public static void load(Context context, @DrawableRes int imgResId, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(imgResId)
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .into(imageView);
    }

    public static void load(Context context, YcImageLoadOptions options) {
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
        List<Integer> list = Test.getBeans(s -> 10086);


    }

    public static void test2(Predicate<String> predicate) {
//        predicate.
    }

    interface Test<T> {
        T getTestBean(String s);

        static <T> List<T> getBeans(Test<T> handle) {
            List<T> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                T bean = handle.getTestBean(i + "asd");
                if (bean != null) {
                    list.add(bean);
                }
            }
            return list;
        }
    }
}
