package com.yc.yclibx.constant;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 照相机状态
 */
@IntDef({YcCameraStateEnum.INIT,
        YcCameraStateEnum.PREVIEW,
        YcCameraStateEnum.PLAYING,
        YcCameraStateEnum.PAUSE,
        YcCameraStateEnum.FINISH,
        YcCameraStateEnum.ERROR,
        YcCameraStateEnum.RELEASE})
@Retention(RetentionPolicy.SOURCE)
public @interface YcCameraStateEnum {
    int INIT = 0;//初始化
    int PREVIEW = 1;//预览
    int PLAYING = 2;//正在进行拍照/录像
    int PAUSE = 3;//暂停
    int FINISH = 4;//完成
    int ERROR = 5;//出错
    int RELEASE = 6;//释放资源
}
