package com.yc.yclibx.constant;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
@IntDef({YcBluetoothConnStateEnum.CONN_SUCCESS, YcBluetoothConnStateEnum.CONN_FAIL})
@Retention(RetentionPolicy.SOURCE)
public @interface YcBluetoothConnStateEnum {
    int CONN_SUCCESS = 0;
    int CONN_FAIL = 1;
}
