package com.yc.ycutilsx.kotlin

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import com.yc.yclibx.comment.YcLog
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit
import kotlin.math.cos

/**
 *
 */
abstract class KotlinHelper {
    /**
     * kotlin四种修饰词 private,protected,internal,public
     * internal 声明，在同一模块中的任何地方可见
     */

    /**
     * 可变变量定义：var 关键字（kotlin变量没有类型java的默认值，默认值就是null）
     * var <标识符> : <类型> = <初始化值>
     * var <标识符> = <初始化值>
     */
    var var11: String = String()
        get() {
            return "$field: var11"
        }
        set(value) {
            field = "var11:$value"
        }
    var var12 = String()

    var var14 = ""//类型推断
    //解除非空限制，改变量允许为空
    var var15: Int? = null
    //    var var16: String = null//正常复制null，编译会报错
    /** 不用初始化的两种方式，kotlin空处理机制所有变量都需要初始化 **/
    abstract var var17: String//抽象变量
    lateinit var var13: String//lateinit var <标识符> : <类型>   //延迟初始化，类似java声明不初始化,且只能用于var
    val name: String by lazy { var13 + "" }//
    /**
     * 1.val 只读变量（value 的缩写），只能赋值一次，不能修改(类似Java中final修饰的变量)
     * 2.val 声明的变量不能进行重新赋值，也就是说不能调用 setter 函数，但可以重写getter函数（这点与java的final修饰不同）。
     */
    val val1 = String()
        //
        get() {
            return "$field:val1"
        }
    //    lateinit val val3:String //val不可设置lateinit

    /**--------------------集合------------------------**/
    class TestBean {
        var mTestBean = "TestBean"
    }

    var list11 = listOf(1, 2, "3434", TestBean(), 123)
    var list12 = arrayOf(TestBean(), 2, "3434", 3, 4)
    fun testList() {
        for (item in list12) {
            if (item == "3434") {
                println("item == 3434:${item == "3434"}")
                break
            } else {
                println(item)
            }
        }
        var item = list12[2]
    }

    // 类似的还有longArrayOf floatArray doubleArray charArray booleanArrayOf等
    // intArrayOf 是 Kotlin 的 built-in 函数
    var list2 = intArrayOf(1, 2, 3, 4)


    /**
     * 返回值String
     * 参数 Int a (不可以为空), String b（可以为空）
     */
    @SuppressLint("CheckResult")
    fun testFun(a: Int, b: String?): String {


//        val1 = "23"
        val subscribe = Observable.timer(1, TimeUnit.HOURS)
            .subscribe {
                YcLog.e(it.toString())
                YcLog.e(a.toString() + b)
            }
        return a.toString() + b
    }

    fun <T : Any> testFun2(): T? {
//        lateinit var t : T //T必须写个继承的类型(Any类型于java的Object)，否则会报lateinit修饰符不允许用于上限为空的类型的局部变量
//        return t
        var t: T? = null
        return t
        var test = test<String>(1)
    }

    fun <T : Any> test(a1: Int): List<T>? {
        return ArrayList<T>()
    }

    interface ITest {
        fun test()
        fun testDefault() {
            println("类型与java的default方法")
        }
    }

    class Test : ITest {
        private var param: String? = null
        private var param2: Int? = null
            get() {
                return field
            }
            set(value) {
                field = value
            }
        private lateinit var test: Test

        constructor() {
        }

        constructor(param3: Int) {
            funTest(1, 2)
        }

        var funTest: (Int, Int) -> Int = { x, y ->
            x + y
        }

        override fun test() {
//            param3
            println("这是Test")
        }
    }


    companion object {

        var empty = fun List<Any>.(index: Int): Any? {
            return if (this.size >= index) {
                YcLog.e("越界了")
                null
            } else {
                this[index]
            }
        }


        var testFun = fun Int.(): Int = this + 2

        val unBoxInt: Int = 1 // 未装箱 int
        val boxInt: Int? = 1 // 装箱 Integer
        val boxInt2: Int? = boxInt // 装箱 Integer
        val boxInt3: Int? = unBoxInt // 装箱 Integer
        var boxIntList: List<Int> = listOf(1, 2) //装箱 Integer
        var unBoxArray: IntArray = intArrayOf(1, 2)//未装箱 int[]
        var unBoxArray2: IntArray = intArrayOf(1, 2)//未装箱 int[]
        @JvmStatic
        fun main(args: Array<String>) {
            var a =cos(Math.PI / 180.0 * 120 / 2)
            print(a)
//            var list = ArrayList<String>()
//            var testArray = ArrayList<String>()
//            testArray.filter {
//                it == "21"
//            }.empty(1)
//            var list2: List<String>
//
//            list.add("")
//            list.empty(2)
//            2.testFun()
            /**Kotlin中所有东西都是对象
             * 数字在 Java 平台是物理存储为 JVM 的原生类型，除非我们需要一个可空的引用（如 Int?）或泛型。 后者情况下会把数字装箱
             * 而且这个范围是[-128， 127]，原因是kotlin在IntegerCache.cache
             * var a:Int ?= m
             * var b:Int ?= n
             * 当n或m有一个不在范围里时，a===b结果为false(必须是可空的引用，例如?=或者泛型)
             * **/
//            println(unBoxArray === unBoxArray2)
//            var varTest: String
//            var varTest2: String? = null
//            varTest = null //报错
//            varTest2 = null   //正确
//            varTest2.toString()
//            varTest2?.toString()//为空时不执行?.后面的方法
//            varTest2!!.toString()//非空断言,一定会执行toString()方法，为空时会报错
//            varTest = "1"
//            varTest2 = "1"
//            varTest.equals(varTest2)
//            varTest == varTest2//等同于equals()
//            varTest === varTest2//比较内存地址


//            var iTest: ITest
//            iTest = Test()
//            iTest.test()
//            iTest.testDefault()
        }
    }
}