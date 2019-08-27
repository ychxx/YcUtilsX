package com.yc.yclibx.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 蓝牙相关错误码
 */
@IntDef({YcBluetoothExceptionErrorEnum.ERROR_NOT_SUPPER_BLT, YcBluetoothExceptionErrorEnum.ERROR_NOT_BLT_PERMISSION, YcBluetoothExceptionErrorEnum.ERROR_OPEN_BLT_FAIL, YcBluetoothExceptionErrorEnum.error_4})
@Retention(RetentionPolicy.SOURCE)
public @interface YcBluetoothExceptionErrorEnum {
    int ERROR_NOT_SUPPER_BLT = 0;//手机没有蓝牙
    int ERROR_NOT_BLT_PERMISSION = 1;//没有蓝牙权限
    int ERROR_OPEN_BLT_FAIL = 2;//开启蓝牙模块失败
    int error_4 = 3;
}
