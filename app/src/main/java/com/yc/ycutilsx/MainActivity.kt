package com.yc.ycutilsx

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yc.yclibx.adapter.YcAdapterHelper
import com.yc.yclibx.adapter.YcRecycleViewItemDecoration
import com.yc.yclibx.adapter.YcRecyclerViewAdapter
import com.yc.yclibx.comment.YcLog
import com.yc.yclibx.file.YcImgUtils
import com.yc.yclibx.permissions.YcUtilPermission
import com.yc.ycutilsx.proxy.TestProxyActivity
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
                2 -> {
                    startActivity(Intent(this, TestGetPhone2::class.java))
                }
                3 -> {
                    startActivity(Intent(this, TestProxyActivity::class.java))
                }
                4->{
                   YcImgUtils.loadNetImg(this,"http://120.35.11.49:39090/oa/orderInstallFile/thumb/1212914668465610753.JPEG",object :YcImgUtils.ImgLoadCall2{
                       override fun onLoadSuccess(resource: Bitmap?) {
                         YcLog.e("onLoadSuccess")
                       }

                       override fun onLoadFailed(errorDrawabl: Drawable?) {
                           YcLog.e("onLoadFailed")
                       }

                   })
                }
                else -> {

                }
            }
        }

//        startActivity(Intent(this,TestAdapterActivity::class.java))
        adapter.add("rxBus")
        adapter.add("Camera")
        adapter.add("测试获取联系人信息")
        adapter.add("测试动态代理")
        adapter.add("测试图片加载失败")
        testRecycleView.adapter = adapter
        testRecycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        val itemDecoration = YcRecycleViewItemDecoration()
//        itemDecoration.setSpace(8)
//        testRecycleView.addItemDecoration(itemDecoration)
        YcUtilPermission.newInstance(this)
            .addPermissions(Manifest.permission.READ_CONTACTS)
            .addPermissions(Manifest.permission.READ_PHONE_STATE)
            .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .addPermissions(Manifest.permission.CALL_PHONE)
            .setSuccessCall {
                startActivity(Intent(this, TestGetPhone2::class.java))
            }
            .start()
    }
}
