package com.yc.ycutilsx

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.yc.yclibx.adapter.YcAdapterHelper
import com.yc.yclibx.adapter.YcRecyclerViewAdapter
import com.yc.yclibx.widget.YcRecycleViewItemDecoration
import kotlinx.android.synthetic.main.activity_test_adapter.*

/**
 *
 */
class TestAdapterActivity : AppCompatActivity() {
    lateinit var mAdapter: YcRecyclerViewAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_adapter)
        mAdapter = object : YcRecyclerViewAdapter<String>(this, R.layout.test_adapter_item) {
            override fun onUpdate(helper: YcAdapterHelper?, item: String, position: Int) {
                helper?.setText(R.id.testAdapterItemTv, "第 " + position + "行")
            }
        }
        mAdapter.setItemClickListener { _, _, position ->
            Log.e("触发了点击", "点击了" + position + " 数值" + mAdapter.getItemId(position))
        }
        for (index in 1..59 step 1)
            mAdapter.add("index")
        testAdapterRv.adapter = mAdapter
        testAdapterRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        testAdapterRv.addItemDecoration(YcRecycleViewItemDecoration())

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(11, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        testAdapterRv2.layoutManager = staggeredGridLayoutManager
        testAdapterRv2.addItemDecoration(YcRecycleViewItemDecoration())
        testAdapterRv2.adapter = mAdapter

    }
}