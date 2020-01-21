package com.yc.yclibx.comment;

import com.yc.yclibx.exception.YcCalculateException;

import java.math.BigDecimal;

/**
 * 主要用于计算小数时避免精确度问题
 */

public class YcCalculator {
    /**
     * 精确的加法运算
     *
     * @param a 被加数
     * @param b 加数
     * @return 两个参数的和
     */
    public static double add(double a, double b) {
        BigDecimal b1 = new BigDecimal(a + "");
        BigDecimal b2 = new BigDecimal(b + "");
        return b1.add(b2).doubleValue();
    }

    /**
     * 精确的减法运算
     *
     * @param a 被减数
     * @param b 减数
     * @return 两个参数的差
     */
    public static double subtract(double a, double b) {
        BigDecimal b1 = new BigDecimal(a + "");
        BigDecimal b2 = new BigDecimal(b + "");
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param a 被乘数
     * @param b 乘数
     * @return 两个参数的积
     */
    public static double multiply(double a, double b) {
        BigDecimal b1 = new BigDecimal(a + "");
        BigDecimal b2 = new BigDecimal(b + "");
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后3位，以后的数字四舍五入
     *
     * @param a 被除数
     * @param b 除数
     * @return 两个参数的商
     */
    public static double divide(double a, double b) throws YcCalculateException {
        return divide(a, b, 3);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入
     *
     * @param a     被除数
     * @param b     除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divide(double a, double b, int scale) throws YcCalculateException {
        if (b == 0) {
            throw new YcCalculateException("除数不能为0!");
        }
        /*
         * 通过BigDecimal的divide方法进行除法时就会抛异常的，异常如下：
         * java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result. at java.math.BigDecimal.divide(Unknown Source)
         * 解决之道：就是给divide设置精确的小数点divide(xxxxx,2, BigDecimal.ROUND_HALF_EVEN)
         * BigDecimal.ROUND_HALF_UP : 向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
         */
        BigDecimal b1 = new BigDecimal(a + "");
        BigDecimal b2 = new BigDecimal(b + "");
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 进行四舍五入
     *
     * @param value 要进行四舍五入的值
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return
     */
    public static double roundOff(double value, int scale) {
        BigDecimal value1 = new BigDecimal(value + "");
        return value1.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
