package com.yc.yclibx.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.yc.yclibx.comment.YcLog;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class YcBluetoothThread {
    public static UUID COMMON_UUID = UUID.fromString("0000FFE1-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket mBtSocket = null;

    @SuppressLint("CheckResult")
    public YcBluetoothThread startConn(BluetoothDevice bluetoothDevice) {
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        if (bluetoothDevice == null) {
            YcLog.e("要连接的蓝牙设备(BluetoothDevice)为空");
            return this;
        }
        Observable.just(bluetoothDevice)
                .observeOn(Schedulers.newThread())
                .flatMap(new Function<BluetoothDevice, ObservableSource<BluetoothSocket>>() {
                    @Override
                    public ObservableSource<BluetoothSocket> apply(BluetoothDevice bluetoothDevice) throws Exception {
//                        BluetoothSocket btSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(COMMON_UUID);
                        COMMON_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//                        COMMON_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
                        BluetoothSocket btSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(COMMON_UUID);
                        btSocket.connect();
                        return Observable.just(btSocket);
                    }
                }).observeOn(Schedulers.newThread())
                .subscribe(new Consumer<BluetoothSocket>() {
                    @Override
                    public void accept(BluetoothSocket bluetoothSocket) throws Exception {
                        if (mBtSocket != null && mBtSocket.isConnected()) {
                            //连接成功YcBluetoothConnStateEnum.CONN_SUCCESS
                            YcLog.e("连接成功   --");
                            inputData(bluetoothSocket.getInputStream());
                        } else {
                            //连接失败
                            YcLog.e("连接失败   --");
                            YcLog.e(mBtSocket.isConnected() + "   --");

                        }
                    }
                });
        return this;
    }

    private void inputData(InputStream inputStream) {
        try {
            int bytes;
            while (true) {
                byte[] buffer = new byte[256];
                bytes = inputStream.read(buffer);
                synchronized (buffer) {
                    String input = "";
                    for (int i = 0; i < bytes; i++) {
                        if (buffer[i] < 0) {
                            input += buffer[i] + 0x100 + ",";
                        } else {
                            input += buffer[i] + ",";
                        }
                    }
                    Log.e("蓝牙接收的数据", input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
