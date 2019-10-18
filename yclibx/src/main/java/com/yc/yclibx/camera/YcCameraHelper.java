package com.yc.yclibx.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.yc.yclibx.YcUtilsInit;
import com.yc.yclibx.comment.YcUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.hardware.Camera.Parameters.FOCUS_MODE_AUTO;

/**
 * 自定义照相机帮助类
 */
public class YcCameraHelper {
    /**
     * 设置SurfaceHolder配置
     */
    public static void setSurfaceHolderConfigure(SurfaceHolder surfaceHolder, SurfaceHolder.Callback callback) {
        surfaceHolder.setKeepScreenOn(true);//屏幕常亮
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);//translucent半透明 transparent透明
        surfaceHolder.addCallback(callback);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /**
     * 设置摄像头的配置（录像）
     *
     * @param camera
     * @param context
     */
    public static void setCameraOfRecorder(Camera camera, Context context) {
        Camera.Parameters parameters = camera.getParameters();
//        setPictureSize(parameters);

        parameters.setPictureFormat(ImageFormat.JPEG); //设置图片格式
        parameters.setJpegQuality(100); //设置图片质量
        //TODO 先用着
        parameters.setPictureSize(640, 480);
        parameters.setPreviewSize(640, 480);
        //自动聚焦模式
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
        parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes != null && !supportedFocusModes.isEmpty()) {
            for (String mode : supportedFocusModes) {
                if (mode.contains("continuous-video")) {
                    parameters.setFocusMode("continuous-video");
                }
            }
        }
        if (parameters.getSupportedSceneModes().contains(Camera.Parameters.SCENE_MODE_HDR)) {//开启HDR
            parameters.setSceneMode(Camera.Parameters.SCENE_MODE_HDR);
        }
        if (parameters.isVideoStabilizationSupported()) { //开启防抖动
            parameters.setVideoStabilization(true);
        }
        if (getOrientation(context) != Configuration.ORIENTATION_LANDSCAPE) {
            camera.getParameters().set("orientation", "portrait");
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);
        } else {
            camera.getParameters().set("orientation", "landscape");
            camera.setDisplayOrientation(0);
            parameters.setRotation(0);
        }
        camera.setParameters(parameters);
    }

    /**
     * 获取屏幕方向
     *
     * @return 横屏2、竖屏1
     */
    public static int getOrientation(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return Configuration.ORIENTATION_LANDSCAPE;
        } else {
            return Configuration.ORIENTATION_PORTRAIT;
        }
    }

    /**
     * 查询摄像头id
     *
     * @param openBackCamera 是否是后置摄像头
     */
    public static int findCamera(boolean openBackCamera) {
        int cameraCount;
        try {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                int facing = openBackCamera ? 0 : 1;
                if (cameraInfo.facing == facing) {
                    return camIdx;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (openBackCamera) {
                Log.e("CameraHelper", "手机没有后置摄像头");
            } else {
                Log.e("CameraHelper", "手机没有前置摄像头");
            }
        }
        return 0;
    }

    /**
     * 设置录像的参数
     */
    public static void setRecorderInfo(MediaRecorder recorder, Surface surface, Camera camera) {
        recorder.reset();
        recorder.setCamera(camera);
        recorder.setOrientationHint(90);
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //音频编码格式对应应为AAC
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //视频编码格式对应应为H264
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //相机参数配置类
        //CameraParams.PREVIEW_WIDTH, CameraParams.PREVIEW_HEIGHT
        recorder.setVideoSize(640, 480);
        recorder.setVideoEncodingBitRate(2 * 1024 * 1024);
        recorder.setVideoFrameRate(30);
        recorder.setPreviewDisplay(surface);
//            if(maxRecordTime!=0) {
        recorder.setMaxDuration(8000);
    }

    /**
     * 设置摄像头的配置（拍照）
     *
     * @param camera
     * @param context
     */
    public static void setCameraOfPhoto(Camera camera, Context context) {
        if (camera == null) return;
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewFormat(ImageFormat.NV21);//预览的格式
        parameters.setRotation(0);
        List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
        // 获取摄像头支持的预览视图比例PreviewSize列表
        List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();
        List<Camera.Size> sameSizeList = new ArrayList<>();
        for (int i = 0; i < pictureSizeList.size(); i++) {
            for (int j = 0; j < previewSizeList.size(); j++) {
                if (pictureSizeList.get(i).height == previewSizeList.get(j).height && pictureSizeList.get(i).width == previewSizeList.get(j).width) {
                    sameSizeList.add(pictureSizeList.get(i));
                    break;
                }
            }
        }
        Camera.Size size = getAppropriateSize(sameSizeList);
        parameters.setPreviewSize(size.width, size.height);
        parameters.setPictureSize(size.width, size.height);
        if (isSupportedFormats(parameters.getSupportedPictureFormats(), ImageFormat.FLEX_RGBA_8888)) {
            parameters.setPictureFormat(ImageFormat.FLEX_RGBA_8888);
            parameters.setJpegQuality(100);
        }
        if (isSupportedFocusMode(parameters.getSupportedFocusModes(), FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(FOCUS_MODE_AUTO);
        }
        if (context.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "portrait");
            camera.setDisplayOrientation(90);
        } else {
            parameters.set("orientation", "landscape");
            camera.setDisplayOrientation(0);
        }
        camera.setParameters(parameters);
        String mode = camera.getParameters().getFocusMode();
        if (("auto".equals(mode)) || ("macro".equals(mode))) {
            camera.autoFocus(null);
        }
    }

    /**
     * 获取适合屏幕尺寸的照相尺寸
     *
     * @param sizeList 照相尺寸集合
     */
    public static Camera.Size getAppropriateSize(List<Camera.Size> sizeList) {
        DisplayMetrics displayMetrics = YcUI.getDisplayMetrics();
        return getAppropriateSize(sizeList, displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    /**
     * 获取适合的照相尺寸
     *
     * @param sizeList 照相尺寸集合
     * @param width    宽
     * @param height   高
     */
    public static Camera.Size getAppropriateSize(List<Camera.Size> sizeList, int width, int height) {
        Camera.Size appropriateSize = null;
        for (int i = 0; i < sizeList.size(); i++) {
            if (sizeList.get(i).height == height && sizeList.get(i).width == width) {
                if (appropriateSize == null || appropriateSize.height < sizeList.get(i).height)
                    appropriateSize = sizeList.get(i);
            }
        }
        if (appropriateSize != null)
            return appropriateSize;
        for (int i = 0; i < sizeList.size(); i++) {
            if (sizeList.get(i).width == width) {
                if (appropriateSize == null)
                    appropriateSize = sizeList.get(i);
                else if (Math.abs(appropriateSize.height - height) > Math.abs(sizeList.get(i).height - height)) {
                    appropriateSize = sizeList.get(i);
                }
            }
        }
        if (appropriateSize == null)
            appropriateSize = sizeList.get(0);
        return appropriateSize;
    }

    public static boolean isSupportedFocusMode(List<String> focusList, String focusMode) {
        for (int i = 0; i < focusList.size(); i++) {
            if (focusMode.equals(focusList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSupportedFormats(List<Integer> supportedFormats, int jpeg) {
        for (int i = 0; i < supportedFormats.size(); i++) {
            if (jpeg == supportedFormats.get(i)) {
                return true;
            }
        }
        return false;
    }
}
