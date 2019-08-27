package com.yc.yclibx.comment;

import java.util.List;

/**
 * 空处理
 */

public class YcEmpty {
    /**
     * 是否为空
     *
     * @param data
     * @return
     */
    public static boolean isEmpty(String data) {
        return data == null || data.length() == 0;
    }

    /**
     * 是否为空
     *
     * @param data
     * @return
     */
    public static boolean isEmpty(List data) {
        return data == null || data.size() <= 0;
    }

    /**
     * 获取字符串
     *
     * @param data 数据
     * @return null时返回“”
     */
    public static String getNoEmpty(String data) {
        return isEmpty(data) ? "" : data;
    }

    /**
     * 获取字符串
     *
     * @param data        数据
     * @param dataIfEmpty null时返回的字符串
     * @return
     */
    public static String getNoEmpty(String data, String dataIfEmpty) {
        return isEmpty(data) ? dataIfEmpty : data;
    }

    /**
     * 获取字符串
     *
     * @param data       原始数据
     * @param condition  条件
     * @param resultData 等于condition时返回的数据
     * @return
     */
    public static String getIf(String data, String condition, String resultData) {
        if (data.equals(condition)) {
            return resultData;
        } else {
            return data;
        }
    }

    /**
     * 获取字符串
     *
     * @param data        原始数据
     * @param condition   条件
     * @param resultData1 等于condition时返回的数据
     * @param resultData2 不等于condition时返回的数据
     * @return
     */
    public static String getIf(String data, String condition, String resultData1, String resultData2) {
        if (data.equals(condition)) {
            return resultData1;
        } else {
            return resultData2;
        }
    }

    /**
     * 获取字符串
     *
     * @param data       原始数据
     * @param conditions 条件
     * @param resultData 等于condition时返回的数据
     * @return data与conditions[i]相等返回对应的resultData[i]，都不相等则返回data
     */
    public static String getIf(String data, String[] conditions, String[] resultData) {
        int length = Math.min(conditions.length, resultData.length);
        for (int i = 0; i < length; i++) {
            if (data.equals(conditions[i])) {
                return resultData[i];
            }
        }
        return data;
    }
}
