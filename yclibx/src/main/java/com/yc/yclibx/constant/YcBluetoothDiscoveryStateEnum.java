package com.yc.yclibx.constant;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 搜索蓝牙设备状态
 */
@IntDef({YcBluetoothDiscoveryStateEnum.DISCOVERY_START, YcBluetoothDiscoveryStateEnum.DISCOVERING, YcBluetoothDiscoveryStateEnum.DISCOVERY_FINISHED})
@Retention(RetentionPolicy.SOURCE)
public @interface YcBluetoothDiscoveryStateEnum {
    /**
     * 开始搜索蓝牙设备
     */
    int DISCOVERY_START = 0;
    /**
     * 正在搜索蓝牙设备
     */
    int DISCOVERING = 1;
    /**
     * 搜索蓝牙设备完成
     */
    int DISCOVERY_FINISHED = 2;
}
