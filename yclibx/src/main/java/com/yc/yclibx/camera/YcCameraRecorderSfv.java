package com.yc.yclibx.camera;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.comment.YcOnDestroy;
import com.yc.yclibx.comment.YcRandom;
import com.yc.yclibx.constant.YcCameraStateEnum;
import com.yc.yclibx.exception.YcException;
import com.yc.yclibx.file.YcFileUtils;

/**
 * 用于手机录像
 */
public class YcCameraRecorderSfv extends SurfaceView implements YcOnDestroy {
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera = null;
    @YcCameraStateEnum
    private int mCameraState;//状态
    private ErrorCall mErrorCall;//错误回调
    private String mRecorderSavePath = Environment.getExternalStorageDirectory() + "/YcUtils/" + YcRandom.getString(5) + "_test.mp4";//视频保存地址
    private boolean mIsSurfaceCreated = false;//surface 是否创建
    /**
     * 录制视频参数
     */
    private MediaRecorder mMediaRecorder;

    public YcCameraRecorderSfv(Context context) {
        this(context, null);
    }

    public YcCameraRecorderSfv(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mCameraState = YcCameraStateEnum.INIT;
        mSurfaceHolder = getHolder();
        YcCameraHelper.setSurfaceHolderConfigure(mSurfaceHolder, new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mIsSurfaceCreated = true;
                mSurfaceHolder = holder;
                YcLog.e("surfaceCreated");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mSurfaceHolder = holder;
                startPreview();
                YcLog.e("surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                YcLog.e("surfaceDestroyed");
                mIsSurfaceCreated = false;
//                onPause();
            }
        });
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
                YcCameraHelper.setCameraOfRecorder(mCamera, getContext());
                //设置预显示
                mCamera.setPreviewDisplay(mSurfaceHolder);
            }
            //开启预览
            mCamera.startPreview();
            mCameraState = YcCameraStateEnum.PREVIEW;
        } catch (Exception e) {
            onErrorCall("无法开启视频预览");
            e.printStackTrace();
        }
    }

    /**
     * 根据当前照相机状态(前置或后置)，打开对应相机
     */
    private void openCamera() {
        YcLog.e("openCamera");
        //获取摄像头id
        int mCameraId = YcCameraHelper.findCamera(true);
        try {
            //打开摄像头
            mCamera = Camera.open(mCameraId);
        } catch (Exception e) {
            mCamera = null;
            onErrorCall("打开摄像头失败");
            e.printStackTrace();
        }
    }

    /**
     * 开始录像
     */
    public void startRecord() {
        try {
            if (mMediaRecorder == null) {
                mMediaRecorder = new MediaRecorder();
            }
            YcCameraHelper.setRecorderInfo(mMediaRecorder, mSurfaceHolder.getSurface(), mCamera);
            mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int what, int extra) {

                }
            });
            mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                @Override
                public void onInfo(MediaRecorder mr, int what, int extra) {

                }
            });
            if (YcFileUtils.createFile(mRecorderSavePath) != null) {//创建保存录像的文件
                mMediaRecorder.setOutputFile(mRecorderSavePath);  //设置输出地址
                mMediaRecorder.setOrientationHint(90);
                mCamera.unlock();//开始录像时，必须解锁摄像头
                mMediaRecorder.prepare();
                mMediaRecorder.start();
                mCameraState = YcCameraStateEnum.PLAYING;
            } else {
                onErrorCall("创建路径文件失败，地址错误");
                Log.e("", "录像地址错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            onErrorCall("开始录像失败" + e.getMessage());
        }
    }

    /**
     * 保存录像
     */
    public void saveRecord(SaveCall saveCall) {
        //当结束录制之后，就将当前的资源都释放
        mCameraState = YcCameraStateEnum.PAUSE;
        try {
            if (mMediaRecorder != null) {
                mMediaRecorder.setOnErrorListener(null);
                mMediaRecorder.setOnInfoListener(null);
                mMediaRecorder.setPreviewDisplay(null);
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                saveCall.onSuccessCall();
            }
        } catch (Exception e) {
            e.printStackTrace();
            onErrorCall("保存录像失败" + e.getMessage());
        }
    }

    /**
     * 在Activity的onResume时开启预览
     */
    public void onResume() {
        YcLog.e("onResume");
        startPreview();
    }

    /**
     * 在Activity的onPause时释放摄像头的资源
     */
    public void onPause() {
        YcLog.e("onPause");
        mCameraState = YcCameraStateEnum.PAUSE;
        releaseMediaRecorder();
        releaseCamera();
    }

    /**
     * 结束必须释放资源，否则可能导致摄像头一直被占用，照相机无法使用（当系统照相机也无法使用，重启手机可解决问题）
     */
    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            if (mCameraState == YcCameraStateEnum.PLAYING) {
                mMediaRecorder.stop();
            }
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            if (mCamera != null) {
                mCamera.lock();           // lock camera for later use
            }
        }
        mCameraState = YcCameraStateEnum.RELEASE;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
        mCameraState = YcCameraStateEnum.RELEASE;
    }

    public String getRecorderSavePath() {
        return mRecorderSavePath;
    }

    public void setRecorderSavePath(String recorderSavePath) {
        this.mRecorderSavePath = recorderSavePath;
    }

    /**
     * 设置错误回调
     */
    public void setErrorCall(ErrorCall errorCall) {
        this.mErrorCall = errorCall;
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
        releaseMediaRecorder();
        releaseCamera();
    }

    public interface ErrorCall {
        void onErrorCall(YcException exception);
    }

    public interface SaveCall {
        void onSuccessCall();
    }
}
