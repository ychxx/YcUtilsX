package com.yc.ycutilsx;

import android.content.Context;

import androidx.annotation.IntDef;

import com.yc.yclibx.adapter.YcAdapterHelper;
import com.yc.yclibx.adapter.YcBaseMultipleRecycleViewAdapter;
import com.yc.yclibx.adapter.YcMultiple3RecycleViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TestMultipleRecycleViewAdapter extends YcMultiple3RecycleViewAdapter {

    public TestMultipleRecycleViewAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public void onUpdate(@NotNull YcAdapterHelper helper, int position) {
        switch (getItemType(position)) {
            case DataType.ITEM_1:
                break;
            case DataType.ITEM_2:
                break;
            case DataType.ITEM_3:
                break;
        }
    }

    @Override
    public int getLayoutResId(int position) {
        switch (getItemType(position)) {
            case DataType.ITEM_1:
                return R.layout.activity_test_adapter_item_1;
            case DataType.ITEM_2:
                return R.layout.activity_test_adapter_item_2;
            case DataType.ITEM_3:
            default:
                return R.layout.activity_test_adapter_item_3;
        }
    }
}
