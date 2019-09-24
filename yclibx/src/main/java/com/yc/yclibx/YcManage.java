package com.yc.yclibx;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.yc.yclibx.comment.YcOnDestroy;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 *
 */
public class YcManage {
    private List<WeakReference<YcOnDestroy>> mData = new ArrayList<>();

    public void add(YcOnDestroy onDestroy) {
        mData.add(new WeakReference<>(onDestroy));
    }

    public void removeAll() {
        for (int i = mData.size() - 1; i >= 0; i--) {
            YcOnDestroy onDestroy = mData.get(i).get();
            if (onDestroy != null) {
                onDestroy.onDestroy();
            }
            mData.remove(i);
        }
    }
}
