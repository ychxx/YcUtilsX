package com.yc.ycutilsx

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yc.yclibx.adapter.YcAdapterHelper
import com.yc.yclibx.adapter.YcRecyclerViewAdapter
import com.yc.yclibx.bluetooth.BleGattCallback
import com.yc.yclibx.bluetooth.YcBluetoothDiscoveryReceiver
import com.yc.yclibx.bluetooth.YcBluetoothHelper
import com.yc.yclibx.bluetooth.YcBluetoothThread
import com.yc.yclibx.permissions.YcUtilPermission
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

class TestBleActivity : AppCompatActivity() {
    private val mBluetoothThread: YcBluetoothThread by lazy {
        YcBluetoothThread().apply {

        }
    }
    private val mAdapter by lazy {
        object : YcRecyclerViewAdapter<BluetoothDevice>(this, R.layout.main_item) {
            override fun onUpdate(helper: YcAdapterHelper, item: BluetoothDevice, position: Int) {
                helper.setText(R.id.mainItemBtn, item.address + "   " + item.name)
            }
        }
    }
    private val mReceiver: YcBluetoothDiscoveryReceiver by lazy {
        YcBluetoothDiscoveryReceiver(this).apply {
            setDiscoveryCall {
                val name = it?.bluetoothDevice?.name
                //智能二合一测距仪(智能角尺)
                if (name?.contains("JD01", ignoreCase = true) == true) {
                    mAdapter.add(it.bluetoothDevice)
                    mReceiver.onCancelDiscovery()
                }
                //智能二合一测距仪(智能卷尺)
                if (name?.contains("DT20", ignoreCase = true) == true) {
                    mAdapter.add(it.bluetoothDevice)
                    mReceiver.onCancelDiscovery()
                }
                //激光测距仪
                if (name?.contains("LS01", ignoreCase = true) == true) {
                    mAdapter.add(it.bluetoothDevice)
                    mReceiver.onCancelDiscovery()
                }

                if ("ycble" == it?.bluetoothDevice?.name) {
                    mAdapter.add(it.bluetoothDevice)
                    mReceiver.onCancelDiscovery()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_ble_ac)
        findViewById<Button>(R.id.btnScan).setOnClickListener {
            mReceiver.startDiscoveryDevices()
        }
        findViewById<RecyclerView>(R.id.rv).apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@TestBleActivity, LinearLayoutManager.VERTICAL, false)
        }
        mAdapter.setItemClickListener { viewHolder, view, position ->
            val item = mAdapter.getItem(position)
            connBle(item)
//            mBluetoothThread.startConn(item)
        }
        mReceiver.registerReceiver(this)
        YcBluetoothHelper.requestBltPermission(this, YcUtilPermission.SuccessCall {

        })

    }

    @SuppressLint("CheckResult")
    private fun connBle(bluetoothDevice: BluetoothDevice) {
        Observable.just<BluetoothDevice>(bluetoothDevice)
            .observeOn(Schedulers.newThread())
            .subscribe { bluetoothSocket ->
                YcBluetoothThread.COMMON_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
                //                        COMMON_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
//                        COMMON_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
                val connectGatt = bluetoothDevice.connectGatt(this, false, BleGattCallback())

//                val btSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(YcBluetoothThread.COMMON_UUID)
//                try {
//                    btSocket.connect()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//                if (mBtSocket != null && mBtSocket.isConnected()) {
//                    //连接成功YcBluetoothConnStateEnum.CONN_SUCCESS
//                    YcLog.e("连接成功   --")
//                    inputData(bluetoothSocket.inputStream)
//                } else {
//                    //连接失败
//                    YcLog.e("连接失败   --")
//                    YcLog.e(mBtSocket.isConnected().toString() + "   --")
//                }
            }
    }
}
