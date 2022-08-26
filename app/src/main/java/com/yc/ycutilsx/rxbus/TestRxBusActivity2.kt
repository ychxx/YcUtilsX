package com.yc.ycutilsx.rxbus

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yc.yclibx.comment.YcLog
import com.yc.yclibx.comment.YcRxBus
import com.yc.ycutilsx.R

/**
 *
 */
class TestRxBusActivity2 : AppCompatActivity() {
    private lateinit var rxBusActivity2SendDataTv: Button
    private lateinit var rxBusActivity2RegisterTv: Button
    private lateinit var rxBusActivity2DataTv: TextView
    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rx_bus_activity2)
        rxBusActivity2SendDataTv = findViewById(R.id.rxBusActivity2SendDataTv)
        rxBusActivity2RegisterTv = findViewById(R.id.rxBusActivity2RegisterTv)

        rxBusActivity2DataTv = findViewById(R.id.rxBusActivity2DataTv)
        rxBusActivity2SendDataTv.setOnClickListener {
            val date1 = TestRxRusBean()
            date1.date = "数据6666"
            YcRxBus.newInstance().post(date1)
        }
        rxBusActivity2RegisterTv.setOnClickListener {
            YcRxBus.newInstance().registerStickyUnsafe(TestRxRusBean::class.java).subscribe {
                rxBusActivity2DataTv.text = "收到的数据：${it.date}"
                if (rxBusActivity2DataTv.text.toString().isEmpty()) {
                    YcLog.e("为空")
                } else {
                    YcLog.e("非空")
                }
            }
        }

        YcRxBus.newInstance().registerUnsafe(TestRxRusBean::class.java).subscribe {
            rxBusActivity2DataTv.text = it.date
        }
    }
}
