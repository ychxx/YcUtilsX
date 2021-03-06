package com.yc.ycutilsx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.yclibx.YcUtilsInit;
import com.yc.yclibx.comment.YcScreenShot;
import com.yc.yclibx.comment.YcScreenShotUtil;
import com.yc.yclibx.comment.YcUI;
import com.yc.yclibx.file.YcImgUtils;

import java.nio.ByteBuffer;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScreenShotActivity extends AppCompatActivity {
    YcScreenShot ycScreenShot;
    ImageView imageView2;
    ImageView imageView3;
    Bitmap mBitmap;

    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_shot_activity);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        findViewById(R.id.screenShotSystemTv2).setOnClickListener(v -> {
//            ycScreenShot.againScreenShot(imageView3);
        });
        findViewById(R.id.screenShotImgTv).setOnClickListener(v -> {
            imageView2.setImageBitmap(mBitmap);
        });
        ycScreenShot = new YcScreenShot(this);
        ycScreenShot.setGetBitmapCall((bitmap, isShow) -> mBitmap = bitmap);
        findViewById(R.id.screenShotSystemTv).setOnClickListener(v -> {
            ycScreenShot.getBitmapData();
//            Observable.just(1)
//                    .subscribeOn(Schedulers.io())//上游
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(integer -> ycScreenShot.getBitmapData());

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ycScreenShot.start();
    }

}
