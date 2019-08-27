package com.yc.yclibx.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import com.yc.yclibx.comment.YcLog;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 *
 */
public class YcBluetoothConnGatt {
    private Consumer<byte[]> mObserver;//搜索蓝牙设备回调
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // 连接成功后启动服务发现
                Log.e("AAAAAAAA", "启动服务发现:" + mBluetoothGatt.discoverServices());
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            //成功发现服务后可以调用相应方法得到该BLE设备的所有服务，并且打印每一个服务的UUID和每个服务下各个特征的UUID
            if (status == BluetoothGatt.GATT_SUCCESS) {
                List<BluetoothGattService> supportedGattServices = mBluetoothGatt.getServices();
                for (int i = 0; i < supportedGattServices.size(); i++) {
                    Log.e("AAAAA", "1:BluetoothGattService UUID=:" + supportedGattServices.get(i).getUuid());
                    List<BluetoothGattCharacteristic> listGattCharacteristic = supportedGattServices.get(i).getCharacteristics();
                    for (int j = 0; j < listGattCharacteristic.size(); j++) {
                        Log.e("a", "2:   BluetoothGattCharacteristic UUID=:" + listGattCharacteristic.get(j).getUuid());
                    }
                }
            } else {
                Log.e("AAAAA", "onservicesdiscovered收到: " + status);
            }
            BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb"));
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"));
//            ((BluetoothGattDescriptor)localObject).setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//            mBluetoothGatt.readCharacteristic(characteristic);
            if (!gatt.setCharacteristicNotification(characteristic, true)) {
                YcLog.e("Couldn't set notifications for RX characteristic!");
            }
        }


        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] data = characteristic.getValue();
            StringBuilder test = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                test.append(" ").append(Integer.toHexString(data[i] & 0xFF));
            }
            Log.e("原始数据", test.toString());
            onSend(data);
        }
    };

    /**
     * 将数据发射给设置好的回调
     */
    private synchronized void onSend(byte[] inputData) {
        Log.e("AAAAA", "收到的数据个数: " + inputData.length);
        Disposable d = Observable.just(inputData).observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);
        mDisposable.add(d);
    }

    private List<Disposable> mDisposable = new ArrayList<>();//用来在结束扫描后，停止回调

    public void onDestroy() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }
        if (mDisposable != null) {
            for (Disposable d : mDisposable) {
                if (d != null) {
                    d.dispose();
                }
            }
            mDisposable.clear();
        }
    }

    public void setInputCall(Consumer<byte[]> observer) {
        mObserver = observer;
    }

    public void conn(BluetoothDevice device, Context context) {
        BluetoothAdapter bltAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bltAdapter != null && bltAdapter.isDiscovering()) {
            bltAdapter.cancelDiscovery();
        }
        mBluetoothGatt = device.connectGatt(context, false, mGattCallback);
    }
}
