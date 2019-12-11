package com.yc.ycutilsx;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TestRxbusHelper {
    private static List<AppCompatActivity> mActivitie = new ArrayList<>();

    public static void add(AppCompatActivity activity) {
        mActivitie.add(activity);
    }

    public static void remove(AppCompatActivity activity) {
        mActivitie.remove(activity);
    }

    public static boolean isCurrent(AppCompatActivity activity) {
        AppCompatActivity current = mActivitie.get(mActivitie.size() - 1);
        if (current == activity) {
            return true;
        } else {
            return false;
        }
    }
}
