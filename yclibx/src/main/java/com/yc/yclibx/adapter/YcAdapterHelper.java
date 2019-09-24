package com.yc.yclibx.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;

/**
 *
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class YcAdapterHelper extends YcAdapterHelperOperation {
    private final SparseArray<View> mViews = new SparseArray<>();
    private View mConvertView;
    private int mPosition;
    private int mLayoutId;

    public YcAdapterHelper(Context context, ViewGroup parent, int layoutId, int position) {
        mPosition = position;
        mLayoutId = layoutId;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static YcAdapterHelper get(Context context, View convertView, ViewGroup parent, int layoutId) {
        return get(context, convertView, parent, layoutId, -1);
    }

    public static YcAdapterHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new YcAdapterHelper(context, parent, layoutId, position);
        }
        YcAdapterHelper existingHelper = (YcAdapterHelper) convertView.getTag();
        if (existingHelper.mLayoutId != layoutId) {
            return new YcAdapterHelper(context, parent, layoutId, position);
        }
        existingHelper.mPosition = position;
        return existingHelper;
    }

    public View getView() {
        return mConvertView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
