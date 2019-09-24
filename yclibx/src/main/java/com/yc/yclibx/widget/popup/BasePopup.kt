package com.yc.yclibx.widget.popup

import android.content.Context
import android.view.ViewGroup
import android.widget.PopupWindow

/**
 *
 */

class BasePopup : PopupWindow {
    private val initImmediately: Boolean = true//初始化

    @JvmOverloads
    constructor(
        context: Context,
        width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
        height: Int = ViewGroup.LayoutParams.MATCH_PARENT
    ) {
        if (initImmediately) {
            initView(context, width, height)
        }
    }

    private fun initView(context: Context, width: Int, height: Int) {

    }
}