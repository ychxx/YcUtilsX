package com.yc.yclibx.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.yc.yclibx.YcUtilsInit;
import com.yc.yclibx.comment.YcEmpty;
import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.glide.GlideApp;
import com.yc.yclibx.glide.GlideRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 *
 */
public class YcImgUtils {



    public interface ImgLoadCall {
        void call(Bitmap resource);
    }

    public interface ImgLoadCall2 {
        void onLoadSuccess(Bitmap resource);

        void onLoadFailed(Drawable errorDrawable);
    }

    /**
     * 加载网络图片(返回Bitmap)
     */
    public static Disposable loadNetImg(Context context, String imgUrl, final ImgLoadCall2 imgLoadCall) {
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> {
                    GlideApp.with(context)
                            .asBitmap()
                            .load(imgUrl)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    imgLoadCall.onLoadSuccess(resource);
                                }

                                @Override
                                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                    imgLoadCall.onLoadFailed(errorDrawable);
                                }
                            });
                });
    }

    public static Disposable loadNetImg(Context context, String imgUrl, boolean isCache, final ImgLoadCall2 imgLoadCall) {
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> {
                    DiskCacheStrategy strategy;
                    if (isCache) {
                        strategy = DiskCacheStrategy.AUTOMATIC;
                    } else {
                        strategy = DiskCacheStrategy.NONE;
                    }
                    GlideApp.with(context)
                            .asBitmap()
                            .load(imgUrl)
                            .diskCacheStrategy(strategy)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    imgLoadCall.onLoadSuccess(resource);
                                }

                                @Override
                                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                    imgLoadCall.onLoadFailed(errorDrawable);
                                }
                            });
                });
    }

    /**
     * 加载网络图片(返回Bitmap)
     */
    public static Disposable loadNetImg(Context context, String imgUrl, final ImgLoadCall imgLoadCall) {
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> {
                    GlideApp.with(context)
                            .asBitmap()
                            .load(imgUrl)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    imgLoadCall.call(resource);
                                }

                                @Override
                                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                    super.onLoadFailed(errorDrawable);
                                }
                            });
                });
    }

    public static Disposable loadNetImg(Context context, String imgUrl, HashMap<String, String> headerData, final ImgLoadCall imgLoadCall) {
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> {
                    GlideApp.with(context)
                            .asBitmap()
                            .load(new GlideUrl(imgUrl, () -> headerData))
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    imgLoadCall.call(resource);
                                }

                                @Override
                                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                    super.onLoadFailed(errorDrawable);
                                }
                            });
                });
    }


    /**
     * 加载网络图片
     */
    public static Disposable loadNetImg(Context context, String imgUrl, ImageView imageView) {
        return loadNetImg(context, imgUrl, imageView, null, true);
    }

    public static Disposable loadNetImg(Context context, String imgUrl, boolean isCache, ImageView imageView) {
        return loadNetImg(context, imgUrl, imageView, null, isCache);
    }

    public static Disposable loadNetImg(Context context, String imgUrl, final HashMap<String, String> headerData, ImageView imageView) {
        return loadNetImg(context, imgUrl, imageView, () -> headerData, true);
    }

    public static Disposable loadNetImg(final Context context, final String imgUrl, final ImageView imageView, boolean isCache) {
        return loadNetImg(context, imgUrl, imageView, null, isCache);
    }

    /**
     * 加载网络图片
     *
     */
    @SuppressLint("CheckResult")
    public static Disposable loadNetImg(final Context context, final String imgUrl, final ImageView imageView, Headers headers, final boolean isCache) {
        //使用RxJava将线程切换到UI线程，防止部分手机 在加载图片失败后再次加载时出现线程相关异常
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> {
                    GlideRequest<Drawable> glideRequest;
                    if (headers == null) {
                        glideRequest = GlideApp.with(context).load(imgUrl);
                    } else {
                        glideRequest = GlideApp.with(context).load(new GlideUrl(imgUrl, headers));
                    }
                    DiskCacheStrategy strategy;
                    if (isCache) {
                        strategy = DiskCacheStrategy.AUTOMATIC;
                    } else {
                        strategy = DiskCacheStrategy.NONE;
                    }
                    glideRequest.error(YcUtilsInit.IMG_FAIL_ID_RES)//失败显示的图片
                            .placeholder(YcUtilsInit.IMG_LOADING_ID_RES)//加载中的图片
                            .diskCacheStrategy(strategy)
                            .into(imageView);
                });
//              .asGif()//指定加载类型
//              .asBitmap()//指定加载类型   git图会变成第一帧静态图
//              .asDrawable()//指定加载类型
//              .asFile()//指定加载类型
//              .error(YcUtilsInit.IMG_FAIL_ID_RES)//失败显示的图片
//              .placeholder(YcUtilsInit.IMG_LOADING_ID_RES)//加载中的图片
                /*DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
                  DiskCacheStrategy.NONE 不使用磁盘缓存
                  DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
                  DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
                  DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。*/
//               .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//               Glide会自动读取ImageView的缩放类型，所以一般在布局文件指定scaleType即可。
//               .fitCenter()//图片样式-居中填充

    }

    /**
     * 加载本地图片
     *
     * @param imgPath   图片路径
     * @param imageView
     */
    @SuppressLint("CheckResult")
    public static Disposable loadLocalImg(Context context, String imgPath, ImageView imageView) {
        if (YcEmpty.isEmpty(imgPath)) {
            YcLog.e("图片加载失败!图片地址为空");
        }
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> GlideApp.with(context)
                        .load(new File(imgPath))
                        .into(imageView));
//        try {
//            Bitmap bmp = null;
//            bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(new File(imgPath)));
//            imageView.setImageBitmap(bmp);
//        } catch (IOException e) {
//            YcLog.e("图片加载失败!");
//            e.printStackTrace();
//        }
    }

    @SuppressLint("CheckResult")
    public static Disposable loadLocalImg(Context context, File imgFile, ImageView imageView) {
        if (imgFile != null) {
            YcLog.e("图片加载失败!图片的File为空");
        }
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> GlideApp.with(context)
                        .load(imgFile)
                        .into(imageView));
    }

    @SuppressLint("CheckResult")
    public static Disposable loadResIdImg(Context context, int imgResId, ImageView imageView) {
        if (imgResId == 0) {
            YcLog.e("图片加载失败!图片资源id为空");
        }
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> GlideApp.with(context)
                        .load(imgResId)
                        .into(imageView));
    }

    @SuppressLint("CheckResult")
    public static Disposable loadUrlImg(Context context, String imgUrl, ImageView imageView) {
        if (YcEmpty.isEmpty(imgUrl)) {
            YcLog.e("图片加载失败!图片的url为空");
        }
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> GlideApp.with(context)
                        .load(imgUrl)
                        .into(imageView));
    }

    /**
     * bitmap转成png格式保存
     *
     * @param bitmap      资源
     * @param imgPathSave 保存路径
     */
    public static boolean saveImg(Bitmap bitmap, String imgPathSave) {
        return saveImg(bitmap, imgPathSave, Bitmap.CompressFormat.PNG);
    }

    public static boolean saveImg(Bitmap bitmap, String imgPathSave, Bitmap.CompressFormat format) {
        try {
            File file = YcFileUtils.createFile(imgPathSave);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(format, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            YcLog.e("图片保存失败!");
            return false;
        }
    }
}

