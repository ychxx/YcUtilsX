package com.yc.yclibx.adapter;

import android.content.Context;

import androidx.annotation.IntDef;

import com.yc.yclibx.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class YcMultiple3RecycleViewAdapter<Data1, Data2, Data3> extends YcBaseMultipleRecycleViewAdapter {
    List<Data1> mData1 = new ArrayList<>();
    List<Data2> mData2 = new ArrayList<>();
    List<Data3> mData3 = new ArrayList<>();

    public YcMultiple3RecycleViewAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    @DataType
    public int getItemType(int position) {
        if (position < mData1.size()) {
            return DataType.ITEM_1;
        } else if (position - mData1.size() < mData2.size()) {
            return DataType.ITEM_2;
        } else if (position - mData1.size() - mData2.size() < mData3.size()) {
            return DataType.ITEM_3;
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
            case DataType.ITEM_3:
                return position - mData1.size() - mData2.size();
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
                return (T) mData2.get(realIndex);
            case DataType.ITEM_3:
            default:
                return (T) mData3.get(realIndex);
        }
    }

    @Override
    public int getItemCount() {
        return mData1.size() + mData2.size() + mData3.size();
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

    public void addAllData3(List<Data3> DataList3, boolean isClear) {
        if (isClear) {
            mData3.clear();
        }
        mData3.addAll(DataList3);
        notifyDataSetChanged();
    }

    public void addAllData(List<Data1> DataList1, List<Data2> DataList2, List<Data3> DataList3, boolean isClear) {
        if (isClear) {
            mData1.clear();
            mData2.clear();
            mData3.clear();
        }
        mData1.addAll(DataList1);
        mData2.addAll(DataList2);
        mData3.addAll(DataList3);
        notifyDataSetChanged();
    }

    @IntDef({DataType.ITEM_1, DataType.ITEM_2, DataType.ITEM_3})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataType {
        int ITEM_1 = 0;
        int ITEM_2 = 1;
        int ITEM_3 = 2;
    }
}
