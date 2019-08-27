package com.yc.yclibx.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.appcompat.app.AppCompatActivity;

import com.yc.yclibx.bean.YcBluetoothDiscoveryBean;
import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.constant.YcBluetoothDiscoveryStateEnum;
import com.yc.yclibx.exception.YcBltException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 蓝牙扫描接收器
 */
public class YcBluetoothDiscoveryReceiver extends BroadcastReceiver {
    private HashSet<BluetoothDevice> mDevice = new HashSet<>();//搜索到的蓝牙设备
    private Consumer<YcBluetoothDiscoveryBean> mObserver;//搜索蓝牙设备回调
    private boolean mIsDestroy = false;//是否结束
    private List<Disposable> mDisposable = new ArrayList<>();//用来在结束扫描后，停止回调
    private BluetoothAdapter mBtAdapter;
    private boolean mIsReceiver = false;
    private WeakReference<AppCompatActivity> mActivity;

    public YcBluetoothDiscoveryReceiver(AppCompatActivity activity) {
        mActivity = new WeakReference<AppCompatActivity>(activity);
    }

    /**
     * 注册蓝牙广播
     */
    public YcBluetoothDiscoveryReceiver registerReceiver(Activity activity) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        mIsDestroy = false;
        mIsReceiver = true;
        activity.registerReceiver(this, intentFilter);
        return this;
    }

    /**
     * 设置搜索蓝牙设备回调
     */
    public YcBluetoothDiscoveryReceiver setDiscoveryCall(Consumer<YcBluetoothDiscoveryBean> observer) {
        mObserver = observer;
        return this;
    }

    /**
     * 开始搜索蓝牙设备(开始前会进行 是否有蓝牙相关权限、蓝牙模块是否开启检测。检测都有后才会开启蓝牙扫描)
     */
    public void startDiscoveryDevices() throws YcBltException {
        if (!YcBluetoothHelper.isHasBltPermission(mActivity.get())) {
            YcBluetoothHelper.requestBltPermission(mActivity.get(), () -> {
                startDiscoveryDevices();
            }, () -> {
                YcLog.e("请求蓝牙相关权限失败");
            });
        } else {
            if (mBtAdapter == null) {
                mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            realStartDiscoveryDevices();
        }
    }

    /**
     * 真正开始扫描蓝牙设备
     */
    @SuppressLint("MissingPermission")
    private void realStartDiscoveryDevices() {
        onCancelDiscovery();
        if (!mIsReceiver) {
            YcLog.e("没有调用registerReceiver(activity)进行注册，回调是不会执行的！！");
        }
        mBtAdapter.startDiscovery();
    }

    /**
     * 接收搜索到的蓝牙设备结果
     */
    @SuppressLint({"CheckResult", "MissingPermission"})
    @Override
    public void onReceive(Context context, Intent intent) {
        if (mIsDestroy)
            return;
        String action = intent.getAction();
        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            onCall(new YcBluetoothDiscoveryBean(YcBluetoothDiscoveryStateEnum.DISCOVERY_START));
            YcLog.e("蓝牙设备名---------------- " + "开始搜索 ...");
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            onCall(new YcBluetoothDiscoveryBean(YcBluetoothDiscoveryStateEnum.DISCOVERY_FINISHED, mDevice));
            YcLog.e("蓝牙设备名---------------- " + "搜索结束");
        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {//扫描完成 或 扫描被取消
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device != null) {
                mDevice.add(device);
                onCall(new YcBluetoothDiscoveryBean(YcBluetoothDiscoveryStateEnum.DISCOVERING, device));
                YcLog.e("蓝牙设备名---------------- " + device.getName());
            }
        }
    }

    /**
     * 将数据发射给设置好的回调
     */
    private void onCall(YcBluetoothDiscoveryBean foundBean) {
        if (mObserver == null || mIsDestroy)
            return;
        Disposable d = Observable.just(foundBean).observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);
        mDisposable.add(d);
    }

    /**
     * 取消蓝牙扫描
     */
    @SuppressLint("MissingPermission")
    public void onCancelDiscovery() {
        if (mBtAdapter == null) {
            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
    }

    /**
     * 注销掉，在Activity的onDestroy方法里调用
     */
    @SuppressLint("MissingPermission")
    public void onDestroy(Activity activity) {
        mIsDestroy = true;
        mIsReceiver = false;
        activity.unregisterReceiver(this);
        if (mDisposable != null) {
            for (Disposable d : mDisposable) {
                if (d != null) {
                    d.dispose();
                }
            }
            mDisposable.clear();
        }
        // 取消蓝牙设备搜索
        if (mBtAdapter != null && mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
    }
}
