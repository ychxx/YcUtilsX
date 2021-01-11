package com.yc.ycutilsx.kotlin.mvvm

import android.os.Bundle
import android.os.PersistableBundle
import com.yc.ycutilsx.kotlin.getNoEmpty

/**
 *
 */
class KtActivity : BaseActivity() {
    var data: String? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showMsg("test")
        val noEmpty: String = data.getNoEmpty()
    }
}