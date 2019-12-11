package com.yc.ycutilsx.rxbus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yc.yclibx.comment.YcLog
import com.yc.yclibx.comment.YcRxBus

import com.yc.ycutilsx.R
import com.yc.ycutilsx.TestRxbusHelper
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.rx_bus_activity.*
import org.json.JSONObject

/**
 *
 */
class TestRxBusActivity : AppCompatActivity() {
    lateinit var disposable: Disposable
    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rx_bus_activity)
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
