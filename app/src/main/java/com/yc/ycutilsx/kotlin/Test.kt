package com.yc.ycutilsx.kotlin

/**
 * Creator: yc
 * Date: 2021/2/19 10:22
 * UseDes:
 */
class Test {
    fun test(){
        val page: Test2.Page = Test2.Page(123)
        page.print()
    }
}
open class Test2{
    fun print(){
        println("123123")
    }
    data class Page constructor(val data:Int): Test2() {}
}
