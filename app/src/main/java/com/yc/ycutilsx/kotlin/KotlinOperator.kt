package com.yc.ycutilsx.kotlin

import android.annotation.SuppressLint
import android.content.SharedPreferences
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
        val student1 = ClassInfo.Student12(1, "", true)
        val value2 = when {
            student1.name == "无名" -> "这是无名"
            student1.sex -> "这是男的"
            student1 is ClassInfo.Person -> "asd"
            else -> {
                val result = "未识别出"
                result
            }
        }
        // result ="231"//在 when 主语中引入的变量的作用域仅限于 when 主体  即whenVar在when外面无法引用
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

    fun funList(arrayInt: IntArray, listInt: List<Int>) {
        funList(intArrayOf(1, 2, 3, 4), listOf(1, 2, 3))
        var list = mutableListOf(1, 2, 3, 4)
        list.add(1)
        list.add(2)
//        list.add("123")
        //kotlin里的List<T>只能get不能set，MutableList才能进行add操作
        var list2 = listOf<Int>(1, 2, 3, 54)
        val item = list2[3]
    }

    //内联函数，可消除匿名函数每次调用时，造成额外的内存和性能开销
    inline fun inlineFun(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
        return operation(num1, num2) + 10086
    }

    //noinline非内联,方法必须是inline才能将入参定义为非内联，
    inline fun inlineFun2(block1: (Int, Int) -> Int, noinline operation: (Int, Int) -> Int) {

    }

    inline fun inlineFun3(n1: Int, n2: Int): Int {
        return n1 + n2
    }

    fun inlineFun4(n1: Int, n2: Int): Int {
        return n1 + n2
    }

    val mInlineFun3 = fun(n1: Int, n2: Int): Int {
        return n1 + n2
    }
    val mInlineFun5 = fun(block1: (Int, Int) -> Int, operation: (Int, Int) -> Int) {

    }

    fun inlineFun12(num1: Int, num2: Int, operation: (Int, Int) -> Int, num3: Int): Int {
        return operation(num1, num2) + 10086
    }

    fun inlineFun13(
        num1: Int,
        num2: Int,
        operation: (Int, Int) -> Int,
        operation2: (Int, Int) -> Int
    ): Int {
        return operation(num1, num2) + 10086
    }

    // 内联和非内联区别：
    // 1.内联在编译时进行了代码替换，没有参数属性，内联函数类型只允许传递给另一个内联函数，非内联函数类型参数可以自由传递给其他任何函数；
    // 2.内联函数所引用的Lambda表达式可使用return关键字返回，非内联函数只能进行局部返回。
    fun testInline() {
        val result1 = inlineFun(1, 2) { n1, n2 -> n1 + n2 }
        val result2 = inlineFun(1, 2, { n1, n2 -> n1 + n2 })
        val result12 = inlineFun12(1, 2, { n1, n2 -> n1 + n2 }, 3)//中间的不能提到外面去，只有最右边的可以
        val result13 =
            inlineFun13(1, 2, { n1, n2 -> n1 + n2 }) { n1, n2 -> n1 + n2 }//中间的不能提到外面去，只有最右边的可以
        val result3 = inlineFun2({ n1, n2 -> n1 + n2 }, { n1, n2 -> n1 + n2 })
        val result4 = inlineFun2(mInlineFun3, mInlineFun3)
        val result5 = inlineFun(1, 2, ::inlineFun3)
        val result6 = inlineFun2(::inlineFun3, ::inlineFun3)
        val result7 = inlineFun2(::inlineFun4, ::inlineFun4)

    }

    //使用高阶函数模仿实现apply函数
    fun StringBuilder.myApply(block1: StringBuilder.() -> Unit): StringBuilder {
        block1()
        return this
    }

    fun <T> T.myApply2(block1: T.() -> Unit): T {
        block1()
        return this
    }

    interface AClass {}

    fun <T : AClass> T.myApply3(block1: T.() -> Unit): T {
        block1()
        return this
    }

    //使用高阶函数，SharedPreferences类中添加一个open函数，接收函数类型的参数。open函数内拥有SharedPre的上下文
    fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit) {
        //直接掉用edit来获取SharedPreference.Editor对象。
        val editor = edit()
        //open函数接受的是SharedPreferences.Editor的函数类型参数，调用editor.block()对函数类型参数进行调用
        editor.block()
        //最后提交数据
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun testMyApply() {
        val list = listOf("111", "123", "333")
        val result = StringBuilder().myApply {
            append("start")
            for (item in list) {
                append(item)
            }
            append("end")
        }
        val result3 = StringBuilder().myApply2 {
            append("start")
            for (item in list) {
                append(item)
            }
            append("end")
        }
        val result2 = StringBuffer().apply {
            append("start")
            for (item in list) {
                append(item)
            }
            append("end")
        }

        val mSharedPreferences: SharedPreferences? = null
        //优化前
        val editor = mSharedPreferences?.edit()
        editor?.putInt("key_int", 1)
        editor?.apply()
        //优化后
        mSharedPreferences?.open {
            putInt("key_int", 1)
        }
    }

    fun list() {
        var listCreate11 = mutableListOf<String>()//可变的list类似 java的ArrayList
        var listCreate12 = mutableListOf("11", 233, 1.0f)//可变的list类似 java的ArrayList
        var listCreate13 = mutableListOf<String>("111", "112", "113")//可变的list类似 java的ArrayList
        var listCreate21 = arrayListOf<String>()//
        var listCreate22 = listOf("aa", 12, 933.0)
        var listCreate23 = listOf<String>("aa1", "aa2", "aa3")

        listCreate11.add("123")
        listCreate11.add(1, "222")
        listCreate11.removeAt(2)
        listCreate11.removeAll(listCreate11)
        listCreate11.clear()
        listCreate11[2] = "123"

//        listCreate11.li
    }

    fun getStringVal(): Any {
        return "asd"
    }

    open class TestExtends {
        open fun println() {
            println("testExtends")
        }
    }

    open class TestExtends2 : TestExtends() {
        override fun println() {
            println("testExtends2")
        }
    }

    companion object {
        fun test(s: TestExtends) {
            s.println()
            s.printlnExtends(1133)
        }

        fun test2(s: TestExtends2) {
            s.println()
            s.printlnExtends(233)
        }

        fun TestExtends.printlnExtends(int: Int) {
            println("TestExtends $int")
            this.println()
        }

        fun TestExtends2.printlnExtends(int: Int) {
            println("TestExtends2 $int")
            this.println()

        }

        fun staticPseudo() {
            println("本质上是个假的静态方法，只是一个内部伴生类")
        }

        @JvmStatic
        fun staticFun() {
            println("注解方式实现java中的静态方法")
        }

        @JvmStatic
        fun main(args: Array<String>) {
//            val kotlinOperator = KotlinOperator()
//            kotlinOperator.operationSection()
//            kotlinOperator.operationWhen(123)
            /**测试扩展**/
//            test(TestExtends())
//            test2(TestExtends2())
            staticPseudo()
            com.yc.ycutilsx.kotlin.staticFun()
            staticFun()
        }
    }
}
