package com.yc.ycutilsx.rxbus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yc.yclibx.comment.YcLog
import com.yc.yclibx.comment.YcRxBus

import com.yc.ycutilsx.R
import kotlinx.android.synthetic.main.rx_bus_activity.*
import kotlinx.android.synthetic.main.rx_bus_activity2.*

/**
 *
 */
class TestRxBusActivity2 : AppCompatActivity() {
    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rx_bus_activity2)

        rxBusActivity2SendDataTv.setOnClickListener {
            val date1 = TestRxRusBean()
            date1.date = "数据6666"
            YcRxBus.newInstance().post(date1)
        }
        rxBusActivity2RegisterTv.setOnClickListener {
            YcRxBus.newInstance().registerStickyUnsafe(TestRxRusBean::class.java).subscribe {
                rxBusActivity2DataTv.text = "收到的数据：${it.date}"
                if (rxBusActivity2DataTv == null) {
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
