package com.yc.ycutilsx

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.yc.yclibx.adapter.YcAdapterHelper
import com.yc.yclibx.adapter.YcRecycleViewItemDecoration
import com.yc.yclibx.adapter.YcRecyclerViewAdapter
import com.yc.yclibx.comment.YcLog
import com.yc.yclibx.comment.YcResources
import com.yc.yclibx.file.YcFileUtils
import com.yc.yclibx.file.YcImgUtils
import com.yc.yclibx.permissions.YcUtilPermission
import com.yc.ycutilsx.proxy.TestProxyActivity
import com.yc.ycutilsx.rxbus.TestRxBusActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_item.*

class MainActivity : AppCompatActivity() {
    class DataBean(var content: String, var maker: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = object : YcRecyclerViewAdapter<DataBean>(this, R.layout.main_item) {
            override fun onUpdate(helper: YcAdapterHelper, item: DataBean, position: Int) {
                helper.setText(R.id.mainItemBtn, item.content)
//                helper?.setOnClickListener(R.id.mainItemBtn) { YcLog.e("asdasd") }
            }
        }
        adapter.setItemClickListener { viewHolder, view, position ->
            when (adapter.getItem(position).maker) {
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
                4 -> {
                    YcImgUtils.loadNetImg(
                        this,
                        "http://120.35.11.49:39090/oa/orderInstallFile/thumb/1212914668465610753.JPEG",
                        object : YcImgUtils.ImgLoadCall2 {
                            override fun onLoadSuccess(resource: Bitmap?) {
                                YcLog.e("onLoadSuccess")
                            }

                            override fun onLoadFailed(errorDrawabl: Drawable?) {
                                YcLog.e("onLoadFailed")
                            }

                        })
                }
                5 -> {
                    startActivity(Intent(this, TestScaleImageActivity::class.java))
                }
                223 -> {
                    if (YcResources.copyAssetsFolderToSD(
                            this,
                            "temp",
                            YcFileUtils.SD_PATH + "/zhaopian"
                        )
                    ) {
                        YcResources.sendRefreshToSysPhone(this, YcFileUtils.SD_PATH + "/zhaopian")
                        Toast.makeText(this, "照片复制到手机里成功", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "照片复制到手机里失败", Toast.LENGTH_LONG).show()
                    }
                }
                224 -> {
                    startActivity(Intent(this, TestGetPhone2::class.java))
                }
                else -> {

                }
            }
        }
        adapter.add(DataBean("复制小米手机图片到手机本地", 223))
        adapter.add(DataBean("查看联系人", 224))
        adapter.add(DataBean("测试图片的放大缩小旋转移动", 5))
//        startActivity(Intent(this,TestAdapterActivity::class.java))
//        adapter.add(DataBean("rxBus", 0))
//        adapter.add(DataBean("Camera", 1))
//        adapter.add(DataBean("测试获取联系人信息", 2))
//        adapter.add(DataBean("测试动态代理", 3))
//        adapter.add(DataBean("测试图片加载失败", 4))
//        adapter.add(DataBean("复制assets资源到sd卡里", 5))
//        adapter.add(DataBean("通知刷新", 6))
        testRecycleView.adapter = adapter
        testRecycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        val itemDecoration = YcRecycleViewItemDecoration()
//        itemDecoration.setSpace(8)
//        testRecycleView.addItemDecoration(itemDecoration)

    }
}
