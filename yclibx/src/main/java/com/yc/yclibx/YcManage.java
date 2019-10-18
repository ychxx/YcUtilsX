package com.yc.yclibx;

import com.yc.yclibx.comment.YcOnDestroy;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理类（主要用于在Activity结束时的回调，例如Dialog的关闭防止窗体泄露等等）
 */
public class YcManage {
    private List<WeakReference<YcOnDestroy>> mData = new ArrayList<>();

    public void add(YcOnDestroy onDestroy) {
        mData.add(new WeakReference<>(onDestroy));
    }

    public void remove(YcOnDestroy ycOnDestroy) {
        for (int i = mData.size() - 1; i >= 0; i--) {
            YcOnDestroy onDestroy = mData.get(i).get();
            if (onDestroy == ycOnDestroy)
                mData.remove(i);
        }
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
