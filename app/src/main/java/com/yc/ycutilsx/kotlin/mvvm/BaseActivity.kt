package com.yc.ycutilsx.kotlin.mvvm

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 *
 */
open class BaseActivity : AppCompatActivity() {
    fun Activity.showMsg(msg: String) {
        runOnUiThread {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }
}