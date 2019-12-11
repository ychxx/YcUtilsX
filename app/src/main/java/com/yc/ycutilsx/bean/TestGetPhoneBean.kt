package com.yc.ycutilsx.bean

/**
 *
 */
class TestGetPhoneBean {
    var name: String? = ""
    var num = ArrayList<String>()
    var note: String? = ""
    override fun toString(): String {
        var numSum = ""
        for (a in num) {
            numSum += "$a,"
        }
        return "TestGetPhoneBean(name=$name, num=$numSum, note=$note)"
    }

}
