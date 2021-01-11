package com.yc.ycutilsx.kotlin

import android.content.Context
import com.yc.yclibx.adapter.YcAdapterHelper
import com.yc.yclibx.adapter.YcRecyclerViewAdapter

/**
 *
 */
public class ClassInfo {
    //主构造函数
    //val声明代表变量不需要重新赋值,未声明对象数据类型，则默认为val
    open class Person(private val age: Int, var name: String, sex: Boolean) {
        companion object {
            //将其定义成常量，定义常量的关键字为const,只有在单例类、companion object和顶层方法才能使用const关键字
            const val TYPE_RECEIVED = 0
        }

        init {
            print("age:$age")
            name = "姓名：$name"
//            sex = "性别"+sex//这里会报错
            print("name:${name}")
        }

    }

    public class Student11(age: Int, name: String) : Person(age, name, true) {

    }

    public class Student12(age: Int, name: String, var sex: Boolean) : Person(age, name, sex) {

    }

    //次构造函数
    //一个类可以有一个主构造函数和多个次构造函数，后者可以用于实例化一个类，只不过它是由函数体的。
    // 当一个类两者都有时，所有的次构造函数必须调用主构造函数（间接调用也可以）
    class Student2(age: Int, name: String, sex: Boolean) : Person(age, name, sex) {
        constructor(age: Int, name: String) : this(age, name, true) {

        }

        constructor(age: Int) : this(age, "无名", true) {

        }

//        constructor() : super(1, "无名", true) {} 报错 有主构造函数时，次构造函数必须调用主构造函数，而不能直接调用父类构造函数
    }

    class Student3 : Person {
        //        constructor(name: String) {} 编译不报错但运行报错，提示必须直接或间接调用super
        constructor(age: Int, name: String) : super(age, name, true) {

        }

        constructor(age: Int) : this(age, "无名") {

        }

        constructor() : super(100, "无名", true) {

        }
    }

    class InitVariable {
        private lateinit var adapter: YcRecyclerViewAdapter<String>
        fun create(context: Context) {
            //判断变量是否进行初始化，如果初始化，则不用重复对变量初始化，否则初始化
            if (!::adapter.isInitialized) {
                adapter = object : YcRecyclerViewAdapter<String>(context) {
                    override fun onUpdate(helper: YcAdapterHelper, item: String?, position: Int) {
                    }
                }
            }
        }
    }

    interface ResultTestSealed1
    class Success(val msg: String) : ResultTestSealed1
    class Fail(val error: String) : ResultTestSealed1

    fun getResultMsg(resultTest: ResultTestSealed1) = when (resultTest) {
        is Success -> resultTest.msg
        is Fail -> resultTest.error
        //else这块是完全执行不到的，但缺少的话代码将无法编译过。
        //如果新增一个UnKnown类并实现了Result接口，用于表示未知的执行结果，但忘记写分支，将会抛出异常使程序崩溃
        else -> throw IllegalArgumentException()
    }


}

/**
 * 密封类（目的是解决为满足编译器要求编写无用条件分支的情况）
 * Sealed classes are used for representing restricted class hierarchies,(密封类用于表示受限类层次结构)
 * when a value can have one of the types from a limited set,(当值可以具有有限集合中的一种类型时)
 * but cannot have any other type.(但不能有其他类型)
 * They are, in a sense, an extension of enum classes:(从某种意义上讲，它们是枚举类的扩展)
 * the set of values for an enum type is also restricted0,(枚举类型的值集也受到限制)
 * but each enum constant exists only as a single instance,(但是每个枚举常量仅作为一个实例存在)
 * whereas a subclass of a sealed class can have multiple instances which can contain state.(而密封类的子类可以具有多个实例，这些实例可以包含状态)
 *
 */
sealed class ResultTestSealed2
class Success2(val msg: String) : ResultTestSealed2()
class Fail2(val error: String) : ResultTestSealed2()

fun getResultMsg2(resultTest: ResultTestSealed2) = when (resultTest) {
    is Success2 -> resultTest.msg
    is Fail2 -> resultTest.error
}