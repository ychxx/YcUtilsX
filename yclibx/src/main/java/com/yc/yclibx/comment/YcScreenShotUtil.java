package com.yc.yclibx.comment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.yc.yclibx.YcUtilsInit;
import com.yc.yclibx.toactivity.YcForResult;

import java.nio.ByteBuffer;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 截图
 */
public class YcScreenShotUtil {
    private MediaProjectionManager mMediaProjectionManager;
    private Stack<Activity> mActivity = new Stack<>();
    private VirtualDisplay mVirtualDisplay;
    private GetBitmapCall mGetBitmapCall;
    private Disposable disposable;
    private Disposable disposable2;
    private ImageReader mImageReader;

    public YcScreenShotUtil(Activity activity) {
        mActivity.add(activity);
    }

    public void setGetBitmapCall(GetBitmapCall getBitmapCall) {
        this.mGetBitmapCall = getBitmapCall;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void start() {
        if (mMediaProjectionManager == null) {
            mMediaProjectionManager = (MediaProjectionManager) mActivity.get(0).getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        }
        YcForResult ycForResult = new YcForResult(mActivity.get(0));
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        disposable = ycForResult.start(mMediaProjectionManager.createScreenCaptureIntent())
                .subscribe(ycForResultBean -> {
                    if (ycForResultBean.getResultCode() == Activity.RESULT_OK) {
                        if (disposable2 != null)
                            disposable2.dispose();
                        disposable2 = Observable.timer(1, TimeUnit.SECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(ycForResultBean.getResultCode(), ycForResultBean.getData());
                                        getBitmapData(mediaProjection);
                                    }
                                });
                    } else {
                        mGetBitmapCall.getBitmap(null, false);
                    }
                });
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getBitmapData(MediaProjection mediaProjection) {
        int windowWidth = YcUI.getScreenWidth();
        int windowHeight = YcUI.getScreenHeight();
        mImageReader = ImageReader.newInstance(windowWidth, windowHeight, 0x1, 2); //ImageFormat.RGB_565
        mVirtualDisplay = mediaProjection.createVirtualDisplay("screen-mirror",
                windowWidth, windowHeight, (int) YcUtilsInit.getResources().getDisplayMetrics().density, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);

        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Image image = mImageReader.acquireLatestImage();
                int width = image.getWidth();
                int height = image.getHeight();
                final Image.Plane[] planes = image.getPlanes();
                final ByteBuffer buffer = planes[0].getBuffer();
                int pixelStride = planes[0].getPixelStride();
                int rowStride = planes[0].getRowStride();
                int rowPadding = rowStride - pixelStride * width;
                Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
                bitmap.copyPixelsFromBuffer(buffer);
                bitmap = Bitmap.createBitmap(bitmap, 0,    0, width, height);
                image.close();
                if (mVirtualDisplay == null) {
                    return;
                }
                mVirtualDisplay.release();
                mVirtualDisplay = null;
                if (mGetBitmapCall != null) {
                    mGetBitmapCall.getBitmap(bitmap, true);
                }
            }
        }, null);
    }

    public void onDestroy() {
        if (disposable != null)
            disposable.dispose();
        if(disposable2!=null){
            disposable2.dispose();
        }
    }

    public static interface GetBitmapCall {
        void getBitmap(Bitmap bitmap, boolean isSuccess);
    }
}
