package com.yc.yclibx.adapter;

import android.content.Context;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class YcMultiple2RecycleViewAdapter<Data1, Data2> extends YcBaseMultipleRecycleViewAdapter {
    List<Data1> mData1 = new ArrayList<>();
    List<Data2> mData2 = new ArrayList<>();

    public YcMultiple2RecycleViewAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    @DataType
    public int getItemType(int position) {
        if (position < mData1.size()) {
            return DataType.ITEM_1;
        } else if (position - mData1.size() < mData2.size()) {
            return DataType.ITEM_2;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public int getItemIndex(int position) {
        switch (getItemType(position)) {
            case DataType.ITEM_1:
                return position;
            case DataType.ITEM_2:
                return position - mData1.size();
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getItem(int position) {
        int realIndex = getItemIndex(position);
        switch (getItemType(position)) {
            case DataType.ITEM_1:
                return (T) mData1.get(realIndex);
            case DataType.ITEM_2:
            default:
                return (T) mData2.get(realIndex);
        }
    }

    @Override
    public int getItemCount() {
        return mData1.size() + mData2.size();
    }

    public void addAllData1(List<Data1> dataList1, boolean isClear) {
        if (isClear) {
            mData1.clear();
        }
        mData1.addAll(dataList1);
        notifyDataSetChanged();
    }

    public void addAllData2(List<Data2> dataList2, boolean isClear) {
        if (isClear) {
            mData2.clear();
        }
        mData2.addAll(dataList2);
        notifyDataSetChanged();
    }

    public void addAllData(List<Data1> DataList1, List<Data2> DataList2, boolean isClear) {
        if (isClear) {
            mData1.clear();
            mData2.clear();
        }
        mData1.addAll(DataList1);
        mData2.addAll(DataList2);
        notifyDataSetChanged();
    }

    @IntDef({DataType.ITEM_1, DataType.ITEM_2})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataType {
        int ITEM_1 = 0;
        int ITEM_2 = 1;
    }
}
