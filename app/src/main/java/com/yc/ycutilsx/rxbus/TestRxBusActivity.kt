package com.yc.ycutilsx.rxbus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yc.yclibx.comment.YcLog
import com.yc.yclibx.comment.YcRxBus

import com.yc.ycutilsx.R
import com.yc.ycutilsx.TestRxbusHelper
import io.reactivex.disposables.Disposable
/**
 *
 */
class TestRxBusActivity : AppCompatActivity() {
    private lateinit var disposable: Disposable
    private lateinit var rxBusActivitySendDataTv: Button
    private lateinit var rxBusActivitySendStickyDataTv: Button
    private lateinit var rxBusActivityRegisterTv: Button
    private lateinit var rxBusActivityToNextBtn: Button
    private lateinit var rxBusActivityDataTv: TextView
    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rx_bus_activity)
        rxBusActivitySendDataTv = findViewById(R.id.rxBusActivitySendDataTv)
        rxBusActivitySendStickyDataTv = findViewById(R.id.rxBusActivitySendStickyDataTv)
        rxBusActivityRegisterTv = findViewById(R.id.rxBusActivityRegisterTv)
        rxBusActivityToNextBtn = findViewById(R.id.rxBusActivityToNextBtn)
        rxBusActivityDataTv = findViewById(R.id.rxBusActivityDataTv)
        TestRxbusHelper.add(this)
        val fragment = TestRxBusFragment()
        supportFragmentManager.beginTransaction().add(R.id.rxBusActivityF, fragment).show(fragment)
            .commitNow()
        rxBusActivitySendDataTv.setOnClickListener {
            val date1 = TestRxRusBean()
            date1.date = "数据1"
            YcRxBus.newInstance().post(date1)
        }
        rxBusActivitySendStickyDataTv.setOnClickListener {
            val date2 = TestRxRusBean()
            date2.date = "数据2"
            YcRxBus.newInstance().postSticky(date2)
        }
        rxBusActivityRegisterTv.setOnClickListener {
            YcRxBus.newInstance().registerUnsafe(TestRxRusBean::class.java).subscribe {
                rxBusActivityDataTv.text = "收到的数据：${it.date}"
            }
        }
        rxBusActivityToNextBtn.setOnClickListener {
            startActivity(Intent(this, TestRxBusActivity3::class.java))
        }
    }

    fun getActivity(): AppCompatActivity {
        return this
    }

    override fun onDestroy() {
        TestRxbusHelper.remove(this)
        disposable?.isDisposed
        super.onDestroy()
    }
}
