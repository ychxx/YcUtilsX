package com.yc.yclibx.comment;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
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

    public static String getNoEmptyString(List data, int index) {
        return getNoEmptyString(data, index, "");
    }

    /**
     * 获取字符串(从集合里获取某个下标数据)
     *
     * @param data        数据
     * @param dataIfEmpty null时返回的字符串
     * @return
     */
    public static String getNoEmptyString(List data, int index, String dataIfEmpty) {
        if (!isEmpty(data) && data.size() > index) {
            return data.get(index).toString();
        } else {
            return dataIfEmpty;
        }
    }

    public static <T> T getNoEmptyT(Class<T> classT, List data, int index) {
        try {
            if (!isEmpty(data) && data.size() > index) {
                return (T) data.get(index);
            } else {
                Constructor<T> constructor = classT.getConstructor();
                return constructor.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getNoEmpty(String data, String defaultData, String unit) {
        if (YcEmpty.isEmpty(data)) {
            return defaultData;
        } else {
            return data + unit;
        }
    }

    public static String getNoEmpty(String data, String defaultData, String prefix, String unit) {
        if (YcEmpty.isEmpty(data)) {
            return prefix + defaultData;
        } else {
            return prefix + data + unit;
        }
    }

    public static String getNoEmptySplit(String data, String defaultData, String regex) {
        return getNoEmptySplit(data, defaultData, "", "", regex);
    }

    public static String getNoEmptySplit(String data, String defaultData, String prefix, String unit, String regex) {
        if (YcEmpty.isEmpty(data)) {
            return prefix + defaultData;
        } else {
            try {
                String result = data.split(regex)[0];
                return prefix + result + unit;
            } catch (Exception e) {
                e.printStackTrace();
                return prefix + data + unit;
            }
        }
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
