package com.yc.yclibx.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.yc.yclibx.R;
import com.yc.yclibx.comment.YcEmpty;
import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.configure.GlideApp;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 *
 */
public class YcImgUtils {
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


    public interface ImgLoadCall {
        void call(Bitmap resource);
    }

    public interface ImgLoadCall2 {
        void onLoadSuccess(Bitmap resource);

        void onLoadFailed(Drawable errorDrawabl);
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

    /**
     * 加载网络图片
     */
    public static Disposable loadNetImg(Context context, String imgUrl, ImageView imageView) {
        return loadNetImg(context, imgUrl, imageView, IMG_FAIL_RELOAD_NUM);
    }

    /**
     * 加载网络图片
     *
     * @param reloadNum 失败后再次加载的次数
     */
    @SuppressLint("CheckResult")
    public static Disposable loadNetImg(final Context context, final String imgUrl, final ImageView imageView, final int reloadNum) {
        //使用RxJava将线程切换到UI线程，防止部分手机 在加载图片失败后再次加载时出现线程相关异常
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> {
                    GlideApp.with(context)
                            .load(imgUrl)
                            .error(IMG_FAIL_ID_RES)//失败显示的图片
                            .placeholder(IMG_LOADING_ID_RES)//加载中的图片
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .listener(new RequestListener<Drawable>() {//添加失败重新加载监听
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    if (reloadNum > 0)
                                        loadNetImg(context, imgUrl, imageView, reloadNum - 1);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(imageView);
                });
//              .asGif()//指定加载类型
//              .asBitmap()//指定加载类型   git图会变成第一帧静态图
//              .asDrawable()//指定加载类型
//              .asFile()//指定加载类型
//              .error(IMG_FAIL_ID_RES)//失败显示的图片
//              .placeholder(IMG_LOADING_ID_RES)//加载中的图片
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

