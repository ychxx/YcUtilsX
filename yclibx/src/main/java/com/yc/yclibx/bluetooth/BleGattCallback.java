package com.yc.yclibx.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.os.SystemClock;

import com.yc.yclibx.comment.YcLog;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by lan on 2016/5/24.
 */
public class BleGattCallback extends BluetoothGattCallback {

    private static final String myUUID = "0000ffe0-0000-1000-8000-00805f9b34fb";

    //蓝牙连接变动
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        YcLog.e("gatt = " + gatt + " status = " + status + ", newstate = " + newState);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            if (BluetoothProfile.STATE_CONNECTED == newState) {
                gatt.discoverServices();
//                BleConfig.isConnected = true;
            } else if (BluetoothProfile.STATE_DISCONNECTED == newState) {
                if (gatt != null) {
                    gatt.disconnect();
                    gatt.close();
                }
            }
        } else {
            if (gatt != null) {
                gatt.disconnect();
                gatt.close();
            }
        }
    }


    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        YcLog.e("onServicesDiscovered status :" + status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            BluetoothGattService bluetoothGattService = gatt.getService(UUID.fromString(myUUID));
            List<BluetoothGattCharacteristic> characteristicList = bluetoothGattService.getCharacteristics();
//            gatt.setCharacteristicNotification(characteristicList.get(1), true);

            for (BluetoothGattCharacteristic characteristic : characteristicList) {
                gatt.setCharacteristicNotification(characteristic, true);
                List<BluetoothGattDescriptor> listDescriptor = characteristic.getDescriptors();
                for (BluetoothGattDescriptor descriptor : listDescriptor) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    gatt.writeDescriptor(descriptor);
                }
            }
//            List<BluetoothGattService> gattServiceList = gatt.getServices();
//            for (BluetoothGattService bluetoothGattService : gattServiceList) {
//                List<BluetoothGattCharacteristic> characteristicList = bluetoothGattService.getCharacteristics();
//                for (BluetoothGattCharacteristic characteristic : characteristicList) {
//                    gatt.setCharacteristicNotification(characteristic, true);
//                }
//            }

            // 获取SERVICE列表
//            theService = gatt.getService(UUID.fromString(myUUID));

//            if (theService != null) {
//                YcLog.e("ServiceName:" + theService.getUuid());
//                List<BluetoothGattCharacteristic> list = theService.getCharacteristics();
//
//                readCharacteristic = theService.getCharacteristic(UUID.fromString(READ_UUID));
//                writeCharacteristic = theService.getCharacteristic(UUID.fromString(WRITE_UUID));
//                if (writeCharacteristic != null) {
//                    boolean rr = gatt.setCharacteristicNotification(writeCharacteristic, true);
//                    YcLog.e("设置结果" + rr);
//                    List<BluetoothGattDescriptor> listDescriptor = writeCharacteristic.getDescriptors();
//                    for (BluetoothGattDescriptor descriptor : listDescriptor) {
//                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//                        gatt.writeDescriptor(descriptor);
//                    }
//                }
//            }
        } else {
        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        YcLog.e("onDescriptorWrite status :" + status);
    }

    //数据变化
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        YcLog.e("onCharacteristicChanged");
        byte[] data = characteristic.getValue();
        YcLog.e("Changed 接收到数据:" + Arrays.toString(data));
        YcLog.e("Changed 接收到数据:" + new String(data));
    }

    //读数据
    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        YcLog.e("onCharacteristicRead status :" + status);
        byte[] data = characteristic.getValue();
        YcLog.e("Read 接收到数据:" + Arrays.toString(data));
        YcLog.e("Read 接收到数据:" + new String(data));
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        YcLog.e("onCharacteristicWrite status :" + status);
        byte[] data = characteristic.getValue();

        String msg = byte2HexStr(data);
        YcLog.e("Write=======" + msg);

        SystemClock.sleep(20);
    }


    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorRead(gatt, descriptor, status);
        YcLog.e("onDescriptorRead:" + descriptor.toString() + "  -- " + status);
    }


    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            sb.append(byte2HexStr(b[n]));
        }
        return sb.toString().toUpperCase().trim();
    }

    public static String byte2HexStr(byte b) {
        String stmp = Integer.toHexString(b & 0xFF);
        return (stmp.length() == 1) ? "0" + stmp : stmp;
    }

}
