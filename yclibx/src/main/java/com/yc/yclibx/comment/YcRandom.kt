package com.yc.yclibx.comment

import java.util.Random

/**
 * 生成随机数
 */

object YcRandom {

    /**
     * 生成一个png格式的图片名
     *
     * @return
     */
    val nameImgOfPNG: String
        get() = System.currentTimeMillis().toString() + "_" + getString(3) + ".png"

    /**
     * 生成一个JPG格式的图片名
     *
     * @return
     */
    val nameImgOfJPG: String
        get() = System.currentTimeMillis().toString() + "_" + getString(3) + ".jpg"

    /**
     * 生成一个[min,max]范围的随机数(包含min和max)
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    fun getInt(min: Int, max: Int): Int {
        return Random().nextInt(max - min + 1) + min//nextInt(x)的范围是[0,x)不包含x，故+1
    }

    /**
     * 生成n位的整数
     *
     * @param n 整数的位数
     */
    public fun getInt(n: Int): Int {
        val min = Math.pow(10.0, n.toDouble()).toInt() / 10
        val max = min * 10 - 1
        return getInt(min, max)
    }

    /**
     * 生成n位的字符串
     *
     * @param n 整数的位数
     */
    fun getString(n: Int): String {
        return getInt(n).toString() + ""
    }
}
