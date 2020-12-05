package com.yc.yclibx.comment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.yc.yclibx.YcManage;
import com.yc.yclibx.YcUtilsInit;
import com.yc.yclibx.toactivity.YcForResult;

import java.nio.ByteBuffer;
import java.util.Stack;

import io.reactivex.disposables.Disposable;

/**
 * 调用系统截屏
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class YcScreenShot implements YcOnDestroy {
    MediaProjectionManager mMediaProjectionManager;
    private Stack<Activity> mActivity = new Stack<>();
    private YcManage mYcManage;
    private VirtualDisplay mVirtualDisplay;
    private GetBitmapCall mGetBitmapCall;
    private Disposable disposable;
    private ImageReader mImageReader;
    private MediaProjection mediaProjection;
    public boolean mIsGetPermission = false; //是否已经获取到权限（截图权限）

    public YcScreenShot(Activity activity, YcManage ycManage) {
        mActivity.add(activity);
        mYcManage = ycManage;
    }

    public YcScreenShot(Activity activity) {
        mActivity.add(activity);
    }

    public void setGetBitmapCall(GetBitmapCall getBitmapCall) {
        this.mGetBitmapCall = getBitmapCall;
    }


    public void start() {
        if (mIsGetPermission) return;
        if (mMediaProjectionManager == null) {
            mMediaProjectionManager = (MediaProjectionManager) mActivity.get(0).getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        }
        YcForResult ycForResult = new YcForResult(mActivity.get(0));
        if (disposable != null && mYcManage != null) {
            mYcManage.remove(this);
            disposable.dispose();
            disposable = null;
        }
        disposable = ycForResult.start(mMediaProjectionManager.createScreenCaptureIntent())
                .subscribe(ycForResultBean -> {
                    mediaProjection = mMediaProjectionManager.getMediaProjection(ycForResultBean.getResultCode(), ycForResultBean.getData());
                    mIsGetPermission = true;
                });
        if (mYcManage != null)
            mYcManage.add(this);
    }

    public void getBitmapData() {
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
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
                image.close();
                if (mVirtualDisplay == null) {
                    return;
                }
                mVirtualDisplay.release();
                mVirtualDisplay = null;
                if (mGetBitmapCall != null) {
                    mGetBitmapCall.getBitmap(bitmap,true);
                }
            }
        }, null);
    }

    @Override
    public void onDestroy() {
        if (disposable != null)
            disposable.dispose();
    }

    public static interface GetBitmapCall {
        void getBitmap(Bitmap bitmap, boolean isSuccess);
    }
}
