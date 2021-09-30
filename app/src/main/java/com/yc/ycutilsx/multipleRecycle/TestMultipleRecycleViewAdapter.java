package com.yc.ycutilsx.multipleRecycle;

import android.content.Context;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import com.yc.yclibx.adapter.YcAdapterHelper;
import com.yc.yclibx.adapter.YcBaseMultipleRecycleViewAdapter;
import com.yc.yclibx.adapter.YcBaseRecyclerAdapter;
import com.yc.yclibx.adapter.YcMultiple3RecycleViewAdapter;
import com.yc.ycutilsx.R;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class TestMultipleRecycleViewAdapter extends YcBaseRecyclerAdapter {

    @StringDef({DataType.ITEM_1, DataType.ITEM_2, DataType.ITEM_3})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataType {
        String ITEM_1 = "0";
        String ITEM_2 = "1";
        String ITEM_3 = "2";
    }

    private List<Data> mData = new ArrayList<>();

    public TestMultipleRecycleViewAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    public int getItemViewType(int position) {
        return getLayoutResId(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onUpdate(@NotNull YcAdapterHelper helper, int position) {
        switch (mData.get(position).type) {
            case DataType.ITEM_1:
                Data.ItemData1 itemData1 = (Data.ItemData1) mData.get(position).itemData;
                helper.setText(R.id.testAdapterItem1Tv, itemData1.name);
                break;
            case DataType.ITEM_2:
                Data.ItemData2 itemData2 = (Data.ItemData2) mData.get(position).itemData;
                helper.setText(R.id.testAdapterItem2Tv, itemData2.test);
                break;
            case DataType.ITEM_3:
                Data.ItemData3 itemData3 = (Data.ItemData3) mData.get(position).itemData;
                helper.setText(R.id.testAdapterItem3Tv, itemData3.proId);
                break;
        }
    }

    public int getLayoutResId(int position) {
        switch (mData.get(position).type) {
            case DataType.ITEM_1:
                return R.layout.activity_test_adapter_item_1;
            case DataType.ITEM_2:
                return R.layout.activity_test_adapter_item_2;
            case DataType.ITEM_3:
            default:
                return R.layout.activity_test_adapter_item_3;
        }
    }


    public List<Data> getData() {
        return mData;
    }

    public void setData(List<Data> data) {
        if (data != null)
            mData = data;
        notifyDataSetChanged();
    }

    public void add(@NonNull Data item) {
        mData.add(item);
        notifyDataSetChanged();
    }
}
