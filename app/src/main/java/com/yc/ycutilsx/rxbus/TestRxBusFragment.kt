package com.yc.ycutilsx.rxbus

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.yc.yclibx.comment.YcRxBus

import com.yc.ycutilsx.R
import io.reactivex.functions.Consumer

/**
 *
 */
class TestRxBusFragment : Fragment() {
    private lateinit var rxBusFragmentRegister: Button
    private lateinit var rxBusFragmentStickyRegister: Button
    private lateinit var rxBusFragmentDataTv: TextView
    private lateinit var rxBusFragmentStickyDataTv: TextView
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
        rxBusFragmentRegister = view.findViewById(R.id.rxBusFragmentRegister)
        rxBusFragmentStickyRegister = view.findViewById(R.id.rxBusFragmentStickyRegister)

        rxBusFragmentDataTv = view.findViewById(R.id.rxBusFragmentDataTv)
        rxBusFragmentStickyDataTv = view.findViewById(R.id.rxBusFragmentStickyDataTv)
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
