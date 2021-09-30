package com.yc.ycutilsx.multipleRecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yc.ycutilsx.R

class TestMultipleRecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_multiple_recycle)
        val adapter = TestMultipleRecycleViewAdapter(this)
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter
        adapter.setData(
            arrayListOf(
                Data(TestMultipleRecycleViewAdapter.DataType.ITEM_1, Data.ItemData1()),
                Data(TestMultipleRecycleViewAdapter.DataType.ITEM_2, Data.ItemData2()),
                Data(TestMultipleRecycleViewAdapter.DataType.ITEM_2, Data.ItemData2()),
                Data(TestMultipleRecycleViewAdapter.DataType.ITEM_1, Data.ItemData1()),
                Data(TestMultipleRecycleViewAdapter.DataType.ITEM_2, Data.ItemData2()),
                Data(TestMultipleRecycleViewAdapter.DataType.ITEM_3, Data.ItemData3())
            )
        )
    }
}
