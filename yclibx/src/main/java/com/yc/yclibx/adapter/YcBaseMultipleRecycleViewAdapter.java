package com.yc.yclibx.adapter;

import android.content.Context;

/**
 *
 */

public abstract class YcBaseMultipleRecycleViewAdapter extends YcBaseRecyclerAdapter {

    public YcBaseMultipleRecycleViewAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(getLayoutResId(position));
    }

    public abstract int getLayoutResId(int position);

    public abstract int getItemType(int position);

    public abstract int getItemIndex(int position);
    @SuppressWarnings("unchecked")
    public abstract <T extends Object> T getItem(int position);
}
