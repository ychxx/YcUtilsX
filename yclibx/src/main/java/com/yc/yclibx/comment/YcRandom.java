package com.yc.yclibx.comment;

import java.util.Random;

/**
 * 生成随机数
 */

public class YcRandom {
    /**
     * 生成一个[min,max]范围的随机数(包含min和max)
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static int getInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;//nextInt(x)的范围是[0,x)不包含x，故+1
    }

    /**
     * 生成n位的整数
     *
     * @param n 整数的位数
     */
    public static int getInt(int n) {
        int min = (int) Math.pow(10, n) / 10;
        int max = min * 10 - 1;
        return getInt(min, max);
    }

    /**
     * 生成n位的字符串
     *
     * @param n 整数的位数
     */
    public static String getString(int n) {
        return getInt(n) + "";
    }

    /**
     * 生成一个png格式的图片名
     *
     * @return
     */
    public static String getNameImgOfPNG() {
        return System.currentTimeMillis() + "_" + getString(3) + ".png";
    }

    /**
     * 生成一个JPG格式的图片名
     *
     * @return
     */
    public static String getNameImgOfJPG() {
        return System.currentTimeMillis() + "_" + getString(3) + ".jpg";
    }
}
