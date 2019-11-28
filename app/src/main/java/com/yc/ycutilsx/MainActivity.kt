package com.yc.ycutilsx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yc.yclibx.adapter.YcAdapterHelper
import com.yc.yclibx.adapter.YcRecycleViewItemDecoration
import com.yc.yclibx.adapter.YcRecyclerViewAdapter
import com.yc.yclibx.comment.YcLog
import com.yc.ycutilsx.rxbus.TestRxBusActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_item.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = object : YcRecyclerViewAdapter<String>(this, R.layout.main_item) {
            override fun onUpdate(helper: YcAdapterHelper?, item: String?, position: Int) {
                helper?.setText(R.id.mainItemBtn, item)
//                helper?.setOnClickListener(R.id.mainItemBtn) { YcLog.e("asdasd") }
            }
        }
        adapter.setItemClickListener { viewHolder, view, position ->
            when (position) {
                0 -> {
                    startActivity(Intent(this, TestRxBusActivity::class.java))
                }
                1 -> {
                    startActivity(Intent(this, TestCameraActivity::class.java))
                }
                else -> {

                }
            }
        }
//        startActivity(Intent(this,TestAdapterActivity::class.java))
        adapter.add("rxBus")
        adapter.add("Camera")
        testRecycleView.adapter = adapter
        testRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        val itemDecoration = YcRecycleViewItemDecoration()
//        itemDecoration.setSpace(8)
//        testRecycleView.addItemDecoration(itemDecoration)
    }
}
