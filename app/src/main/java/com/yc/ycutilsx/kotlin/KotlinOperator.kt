package com.yc.ycutilsx.kotlin

import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 *  操作符
 */
class KotlinOperator {
    /**
     * 位运算
     * shl(bits)    – 有符号左移
     * shr(bits)    – 有符号右移
     * ushr(bits)   – 无符号右移
     * and(bits)    – 位与
     * or(bits)     – 位或
     * xor(bits)    – 位异或
     * inv()        – 位非
     */
    fun operationBit() {
        val x = (1 shl 2) and 0x0000FF000
    }

    /**
     * 区间0
     * 区间实例以及区间检测：a..b、 x in a..b、 x !in a..b
     */
    fun operationSection() {
        var instance: String by Delegates.notNull()

        println(1 in 1..191)
        println('1' in 'a'..'1')
        for (s in 'z'..'1') {
            println("test:$s")
        }
        println("212" + 11212)
    }

    fun operationWhen(data: Any) {
        var a = when (data) {
            1 -> println("1")
            2 -> println("2")
            3, 4 -> println("3,4")
            in 5..100 -> println("5~100")
            is String -> println("is String")
            233 -> {//代码块
                println("233")
                data
            }
            else -> data
        }
        println(a)
        var a1 = 212
        var a2 = 321
        var a3 = if (a1 > a2) a1 else a2
        println(a3)
        a3 = when {//也可以用于取代if
            a1 > a2 -> a1
            else -> a2
        }
        println(a3)
        val value = when (val response = getStringVal()) {
            is String -> {
                var whenVar = 123123
                response
            }
            is Int -> 233
            else -> 1231
        }
//      在 when 主语中引入的变量的作用域仅限于 when 主体  即whenVar在when外面无法引用
    }

    fun operationFor() {
        for (i in 1..100 step 2) println(i)
        for (i in 100 downTo 1 step 6) {//从100到1 每次步数为6

        }
        var list2 = mutableListOf("asd", "asdasdas")
        list2.add("1212")
        for (item in list2) {
            println("集合：$item")
        }
        for (index in list2.indices step 1) {//step 可以省略，默认为1
            println("集合下标：$index")
        }
        for ((index, value) in list2.withIndex()) {//下标和值
            println("集合：$index -- $value")
        }
        //while跟java一样，kotlin也支持java里的 Break 与 continue
    }

    fun getStringVal(): Any {
        return "asd"
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val kotlinOperator = KotlinOperator()
            kotlinOperator.operationSection()
            kotlinOperator.operationWhen(123)
        }
    }
}
