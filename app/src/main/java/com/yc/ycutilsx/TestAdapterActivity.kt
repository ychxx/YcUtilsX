package com.yc.ycutilsx

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yc.yclibx.adapter.YcAdapterHelper
import com.yc.yclibx.adapter.YcRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_test_adapter.*

/**
 *
 */
class TestAdapterActivity : AppCompatActivity() {
    lateinit var mAdapter: YcRecyclerViewAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_adapter)
        mAdapter = object : YcRecyclerViewAdapter<String>(this) {
            override fun onUpdate(helper: YcAdapterHelper?, position: Int) {
                helper?.setText(R.id.ycItemDefaultTv, "第" + position + "行")
            }
        }
        mAdapter.setItemClickListener { viewHolder, view, position ->
            Log.e("触发了点击", "点击了" + position + " 数值" + mAdapter.getItemId(position))
        }
        for (index in 1..10000 step 1)
            mAdapter.add("index")
        testAdapterRv.adapter = mAdapter
        testAdapterRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}