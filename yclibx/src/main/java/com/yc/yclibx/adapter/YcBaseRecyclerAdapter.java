package com.yc.yclibx.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.yclibx.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class YcBaseRecyclerAdapter extends RecyclerView.Adapter<YcRecyclerViewHolder> {
    YcAdapterHelper mAdapterHelper;
    private final Context mContext;
    private final int mLayoutResId;


    public YcBaseRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        this.mLayoutResId = R.layout.yc_item_default;
    }

    public YcBaseRecyclerAdapter(@NonNull Context context, int layoutResId) {
        this.mContext = context;
        this.mLayoutResId = layoutResId;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.yc_item_default;
    }

    @NotNull
    @Override
    public YcRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final YcAdapterHelper helper = YcAdapterHelper.get(mContext, null, parent, viewType, -1);
        final YcRecyclerViewHolder ycRecyclerViewHolder = new YcRecyclerViewHolder(helper);
        setItemClick(ycRecyclerViewHolder);
        return ycRecyclerViewHolder;
    }

    protected void setItemClick(YcRecyclerViewHolder ycRecyclerViewHolder){

    }

    @Override
    public void onBindViewHolder(@NonNull YcRecyclerViewHolder holder, int position) {
        YcAdapterHelper helper = holder.getYcAdapterHelper();
        onUpdate(helper, position);
    }

    @Override
    public void onBindViewHolder(@NonNull YcRecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (null != payloads && payloads.size() > 0) {
            YcAdapterHelper helper = holder.getYcAdapterHelper();
            onPartialRefresh(helper, payloads);
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    /**
     * 用于局部刷新
     *
     * @param helper
     * @param payloads
     */
    public void onPartialRefresh(@NonNull YcAdapterHelper helper, @NonNull List<Object> payloads) {

    }

    public abstract void onUpdate(YcAdapterHelper helper, int position);


}
