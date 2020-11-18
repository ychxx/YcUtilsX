package com.yc.yclibx.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.comment.YcOnDestroy;
import com.yc.yclibx.constant.YcCameraStateEnum;
import com.yc.yclibx.exception.YcException;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


/**
 * 用于自定义照相机
 */
public class YcCameraPhotoSfv extends SurfaceView implements YcOnDestroy {
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera = null;
    @YcCameraStateEnum
    private int mCameraState;//状态
    private YcCameraPhotoSfv.ErrorCall mErrorCall;//错误回调
    private boolean mIsSurfaceCreated = false;//surface 是否创建
    private TakePictureCall mTakePictureCall;
    private boolean mOpenBackCamera = true;
    private boolean mIsOpenLight = false;//是否开启闪光灯
    private Stack<Disposable> mDisposableList = new Stack<>();

    public YcCameraPhotoSfv(Context context) {
        this(context, null);
    }

    public YcCameraPhotoSfv(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mCameraState = YcCameraStateEnum.INIT;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
//                YcLog.e("surfaceCreated");
                mIsSurfaceCreated = true;
                mSurfaceHolder = holder;

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//                YcLog.e("surfaceChanged");
                mSurfaceHolder = holder;
                startPreview();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
//                YcLog.e("surfaceDestroyed");
                mIsSurfaceCreated = false;
//                onPause();
            }
        });
//        mSurfaceTexture = new SurfaceTexture(10);
        setOnClickListener(v -> autoFocus(null));
    }

    public void autoFocus(Camera.AutoFocusCallback focusCallback) {
        if (mCamera != null) {
            mCamera.autoFocus(focusCallback);
        }
    }

    /**
     * 开始预览
     */
    public void startPreview() {
        try {
            if (!mIsSurfaceCreated) {//在surface 创建完成前开启摄像头是无效的
                return;
            }
            if (mCamera == null) {
                YcLog.e("startPreview mCamera == null");
                openCamera();
                YcCameraHelper.setCameraOfPhoto(mCamera, getContext());
                mCamera.setPreviewDisplay(mSurfaceHolder);
            }
            //开启预览
            switchLight(mIsOpenLight);
            mCamera.startPreview();
            mCameraState = YcCameraStateEnum.PREVIEW;
        } catch (Exception e) {
            releaseCamera();
            onErrorCall("无法开启视频预览");
            e.printStackTrace();
        }
    }

    /**
     * 根据当前照相机状态(前置或后置)，打开对应相机
     */
    private void openCamera() {
//        YcLog.e("openCamera");
        //获取摄像头id
        int mCameraId = YcCameraHelper.findCamera(mOpenBackCamera);
        try {
            //打开摄像头
            mCamera = Camera.open(mCameraId);
        } catch (Exception e) {
            mCamera = null;
            onErrorCall("打开摄像头失败");
            e.printStackTrace();
        }
    }

    /* ___________________________________以下为拍照模块______________________________________*/
    public synchronized void takePicture(TakePictureCall call) {
        mTakePictureCall = call;
        if (mCamera == null || mCameraState == YcCameraStateEnum.FOCUSING || mCameraState == YcCameraStateEnum.PLAYING)
            return;
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(getContext(), "请插入存储卡", Toast.LENGTH_SHORT).show();
            return;
        }
        mCameraState = YcCameraStateEnum.FOCUSING;
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
//                Log.e("zome", "Zome:" + camera.getParameters().getZoom());
                if (success) {
                    try {
                        mCameraState = YcCameraStateEnum.PLAYING;
                        mCamera.takePicture(null, null, new Camera.PictureCallback() {
                            @Override
                            public void onPictureTaken(byte[] data, Camera camera) {
                                mCameraState = YcCameraStateEnum.FINISH;
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                Matrix matrix = new Matrix();
                                if (mOpenBackCamera) {
                                    matrix.setRotate(90);
                                } else {
                                    matrix.setRotate(270);
                                    matrix.postScale(-1, 1);
                                }
                                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                                mTakePictureCall.Call(bitmap);
                            }
                        });
                    } catch (Exception e) {
                        onErrorCall("保存图片失败");
                        //TODO best 待优化。拍照失败
                        mDisposableList.add(Observable.timer(1500, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> {
                                    mCameraState = YcCameraStateEnum.PREVIEW;
                                }));
                        e.printStackTrace();
                    }
                } else {
                    //TODO best 待优化。聚焦失败
                    mDisposableList.add(Observable.timer(1500, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aLong -> {
                                mCameraState = YcCameraStateEnum.PREVIEW;
                            }));
                }
            }
        });
    }

    public void setIsOpenLight(boolean isOpenLight) {
        mIsOpenLight = isOpenLight;
        switchLight(mIsOpenLight);
    }

    /**
     * 切换前/后摄像头
     *
     * @param backCamera true 后置， false前置
     * @return 成功失败
     */
    public boolean switchBackCamera(boolean backCamera) {
        if (mOpenBackCamera == backCamera) return false;
        mOpenBackCamera = backCamera;
        if (mCamera != null) {
            releaseCamera();
            startPreview();
        }
        return true;
    }

    /**
     * 开关闪光灯
     **/
    public void switchLight(boolean open) {
        if (mCamera == null)
            return;
        try {
            if (open) {
//                Camera.Parameters.FLASH_MODE_ON 拍照时打开闪光灯
//                Camera.Parameters.FLASH_MODE_OFF 拍照时始终关闭闪光灯
//                Camera.Parameters.FLASH_MODE_AUTO 系统决定是否开启闪光灯（推荐使用）
//                Camera.Parameters.FLASH_MODE_TORCH 手电筒模式 一直开着闪光灯。
                Camera.Parameters parameter = mCamera.getParameters();
                if (!parameter.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                    parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mCamera.setParameters(parameter);
                }
            } else {
                Camera.Parameters parameter = mCamera.getParameters();
                if ((parameter.getFlashMode() != null) && (!parameter.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF))) {
                    parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(parameter);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在Activity的onPause时释放摄像头的资源
     */
    public void onPause() {
        YcLog.e("onPause");
        mCameraState = YcCameraStateEnum.PAUSE;
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.setPreviewCallbackWithBuffer(null);
            mCamera.stopPreview();
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
        if (mDisposableList != null && mDisposableList.size() > 0) {
            for (Disposable disposable : mDisposableList) {
                disposable.dispose();
            }
            mDisposableList.clear();
        }
        mCameraState = YcCameraStateEnum.RELEASE;
    }

    /**
     * 错误回调
     */
    private void onErrorCall(String msg) {
        mCameraState = YcCameraStateEnum.ERROR;
        YcLog.e("onErrorCall" + msg);
        if (mErrorCall != null) {
            mErrorCall.onErrorCall(new YcException(msg));
        }
    }

    @Override
    public void onDestroy() {
        releaseCamera();
    }

    public interface ErrorCall {
        void onErrorCall(YcException exception);
    }


    public interface TakePictureCall {
        void Call(Bitmap bitmap);
    }
}
