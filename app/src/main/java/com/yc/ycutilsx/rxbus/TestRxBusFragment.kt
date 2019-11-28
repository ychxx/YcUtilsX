package com.yc.ycutilsx.rxbus

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yc.yclibx.comment.YcRxBus

import com.yc.ycutilsx.R
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.rx_bus_fragment.*

/**
 *
 */
class TestRxBusFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.rx_bus_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rxBusFragmentRegister.setOnClickListener {
            YcRxBus.newInstance().registerUnsafe(TestRxRusBean::class.java).subscribe {
                rxBusFragmentDataTv.text = "收到的数据：${it.date}"
            }
        }
        rxBusFragmentStickyRegister.setOnClickListener {
            YcRxBus.newInstance().registerStickyUnsafe(TestRxRusBean::class.java).subscribe {
                rxBusFragmentStickyDataTv.text = "收到的延迟数据：${it.date}"
            }
        }
    }
}
