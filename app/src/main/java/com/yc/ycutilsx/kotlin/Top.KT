package com.yc.ycutilsx.kotlin

import android.app.Activity

fun staticFun() {
    println("顶层方法实现java中的静态方法")
}

fun String?.isEmpty(): Boolean {
    return this == null
}

//利用顶层方法实现 扩展函数，用于处理接口空数据
fun String?.getNoEmpty(default: String = ""): String {
    return this ?: default
}