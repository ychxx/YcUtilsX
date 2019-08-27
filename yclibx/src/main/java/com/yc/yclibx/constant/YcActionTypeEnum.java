package com.yc.yclibx.constant;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 打开第三方应用的类型
 */
@IntDef({YcActionTypeEnum.SELECTOR, YcActionTypeEnum.CAMERA, YcActionTypeEnum.WEB, YcActionTypeEnum.CROP
        , YcActionTypeEnum.TELEPHONE, YcActionTypeEnum.SMS, YcActionTypeEnum.SETTING, YcActionTypeEnum.APP_INFO})
@Retention(RetentionPolicy.SOURCE)
public @interface YcActionTypeEnum {
    int SELECTOR = 23301; //文件选择器
    int CAMERA = 23302;//照相机页面
    int WEB = 23303;//浏览器
    int CROP = 23304;//裁剪页面
    int TELEPHONE = 23305;//拨号页面
    int SMS = 23306;//发短信
    int SETTING = 23307;//系统设置页面
    int APP_INFO = 23308;//App信息页面

}