package com.yc.ycutilsx.input.compat;

import android.os.IBinder;

import com.yc.ycutilsx.input.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by qiulinmin on 2017/3/7.
 */
public class ServiceManagerCompat {

    private static Class sClass = null;

    public static final String METHOD_QUERY_LOCAL_INTERFACE = "queryLocalInterface";

    public static Class Class() throws ClassNotFoundException {
        if (sClass == null) {
            sClass = Class.forName("android.os.ServiceManager");
        }
        return sClass;
    }

    public static IBinder getService(String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return (IBinder) ReflectUtil.invokeStaticMethod(Class(), "getService", new Class<?>[]{String.class}, new Object[]{name});
    }

    public static Map sCache() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object sCache = ReflectUtil.getStaticFiled(Class(), "sCache");
        if (sCache instanceof  Map) {
            return (Map) sCache;
        }
        return null;
    }

}
