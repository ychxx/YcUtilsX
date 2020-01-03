package com.yc.ycutilsx.kotlin

/**
 *
 * 1.lazy{} 只能用在val类型, lateinit 只能用在var类型
 * 2.lateinit不能用在可空的属性上和java的基本类型上 如：lateinit var age: Int  //会报错
 * 3.lateinit可以在任何位置初始化并且可以初始化多次。而lazy在第一次被调用时就被初始化，想要被改变只能重新定义
 * 4.lateinit 有支持（反向）域（Backing Fields）
 *
 * val=public final+get()
 * const val=public static final
 */
class KotlinOrder {
    /**
     * 在 Kotlin 中的一个类可以有一个主构造函数以及一个或多个次构造函数。
     * 主构造函数是类头的一部分：它跟在类名（与可选的类型参数）后。
     * kotlin里类默认都是final的只有声明了open的才可以被继承
     */
    open class TestConstructor internal constructor(param: String, param2: Int) {
        init {
            println(param)
//            param2.also(::println)
        }

        open fun println() {//方法也是默认final只有open的子类才能重写
            println("printlin")
        }
    }

    /**
     * 可见性修饰符（private protected internal public）时可以省略constructor
     */
    class TestConstructor3(param: String, param2: Int)

    open class TestConstructor4(param: String, param2: Int) : TestConstructor(param, param2) {
        fun test() {
            super.println()//调用父类里的方法
        }

        open override fun println() {
            println("println 4")
        }
    }

    class TestConstructor5(param: String, param2: Int) : TestConstructor4(param, param2) {
        override fun println() {
            println("println 5")
            super.println()
        }

    }

    class TestConstructor2 {
        var param: String = ""

        constructor(param: String) : this(param, 123123) {
            println("asdasd")
        }

        constructor(param: String, param2: Int) {
            println(param)
            this.param = param
            param2.also(::println)
        }
    }

    fun testConstructor() {
        val value = KotlinOrder.TestConstructor("12", 21)
        val value2 = TestConstructor2("12", 21)
    }

    var param1 = "param1"
        set(value) {
            println("param1 set :$field")
            field = value
        }
        get() {
            println("param1 get:$field")
            return field
        }
    var param12: String? = null
        set(value) {
            println("param12 set :$field")
            field = value
        }
        get() {
            println("param12 get:$field")
            return field
        }
    lateinit var param131: String
    lateinit var param132: String
    var param14: String? = null
    var param142: String? = null
    var param15 = ""
    var param16 = "param16"
        set(value) {
            println("param16 set :$field")
            field = value
        }
        get() {
            println("param16 get:$field")
            return field
        }

    val param2 = "param2"
        get() {
            println("param2 get:$field")
            return field
        }
    val param22: String? = null
        get() {
            println("param22 get:$field")
            return field
        }
    //只会在第一次生效
    val param23: String by lazy {
        "param23:$param1"
    }

    companion object {
        init {//类似于java的static{}静态代码块
            println("static")
        }
    }

    init {//初始化块
        println("init init")
        param14 = "14"
        println("init param14:$param14")
        param15 = "15"
        println("init param15:$param15")
    }

    constructor() {
        println("constructor")
        param16 = "16"
        println("constructor:$param16")
        param131 = "131"
        println("constructor:$param131")
    }

    fun testFun() {
        println("$9.99")
        var a = 1
        var b = a.plus(12)
        println("${b}")
        param1 = "param1-param23"
        println("testFun:$param23")
        param1 = "param1-param23改"
        println("testFun:$param23")
//        param23 = ""
        println("testFun:$param131")
//        println("testFun:$param132")//kotlin.UninitializedPropertyAccessException: lateinit property param132 has not been initialized
        println("testFun:$param142")
    }
}