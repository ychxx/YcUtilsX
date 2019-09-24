package com.yc.yclibx.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 *
 */
public class YcRecyclerViewHolder extends RecyclerView.ViewHolder {
    YcAdapterHelper mYcAdapterHelper;

    public YcAdapterHelper getYcAdapterHelper() {
        return mYcAdapterHelper;
    }

    public YcRecyclerViewHolder(YcAdapterHelper adapterHelper) {
        super(adapterHelper.getView());
        mYcAdapterHelper = adapterHelper;
    }
}