package com.yc.ycutilsx.kotlin

import java.util.*

/**
 * Creator: yc
 * Date: 2021/2/22 20:36
 * UseDes:
 */
internal class TestJaa {
    internal inner class Ta

    fun test(vararg ta: Ta) {
        print(ta.toString())
    }

    fun main1() {
        val lis = Arrays.asList(Ta(), Ta())
        val data = lis.toTypedArray()
        test(*data)
    }
}