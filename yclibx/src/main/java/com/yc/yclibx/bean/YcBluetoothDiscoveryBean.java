package com.yc.yclibx.bean;

import android.bluetooth.BluetoothDevice;


import com.yc.yclibx.constant.YcBluetoothDiscoveryStateEnum;

import java.util.HashSet;

/**
 * 蓝牙搜索bean
 */
public class YcBluetoothDiscoveryBean {
    private @YcBluetoothDiscoveryStateEnum
    int mState;
    private HashSet<BluetoothDevice> mAllBluetoothDevices;
    private BluetoothDevice mBluetoothDevice;

    public YcBluetoothDiscoveryBean(int state) {
        this.mState = state;
    }

    public YcBluetoothDiscoveryBean(int state, HashSet<BluetoothDevice> bluetoothDevices) {
        this.mState = state;
        this.mAllBluetoothDevices = bluetoothDevices;
    }

    public YcBluetoothDiscoveryBean(int state, BluetoothDevice bluetoothDevice) {
        this.mState = state;
        this.mBluetoothDevice = bluetoothDevice;
    }

    public @YcBluetoothDiscoveryStateEnum
    int getState() {
        return mState;
    }
    public HashSet<BluetoothDevice> getAllBluetoothDevices() {
        return mAllBluetoothDevices;
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }
}
