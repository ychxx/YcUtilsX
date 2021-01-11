package com.yc.ycutilsx.kotlin

import kotlin.reflect.KProperty

/**
 * 委托
 */
class KotlinDelegate {

    class Delegate {
        var mRealItem: Any? = null
        operator fun getValue(delegateClass: Class1, prop: KProperty<*>): Any? {
            return mRealItem
        }

        operator fun setValue(delegateClass: Class1, prop: KProperty<*>, value: Any?) {
            mRealItem = value
        }
    }

    class Class1 {
        var mItem by Delegate()
    }
}
