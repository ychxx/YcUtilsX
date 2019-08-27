package com.yc.yclibx.comment;

import android.util.Log;

/**
 *
 */

public class YcLog {
    private static final String TAG = "YcUtils";

    public static void d(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
