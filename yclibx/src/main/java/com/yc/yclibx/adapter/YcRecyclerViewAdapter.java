package com.yc.yclibx.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class YcRecyclerViewAdapter<T> extends YcBaseRecyclerAdapter {
    private List<T> mData = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public YcRecyclerViewAdapter(@NonNull Context context) {
        super(context);
    }

    public YcRecyclerViewAdapter(@NonNull Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    public void onUpdate(YcAdapterHelper helper, int position) {
        onUpdate(helper, getItem(position), position);
    }

    public abstract void onUpdate(YcAdapterHelper helper, T item, int position);

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void add(@NonNull T item) {
        mData.add(item);
        notifyItemInserted(mData.size());
    }

    public void addAll(@NonNull List<T> list) {
        mData.addAll(list);
        notifyItemRangeInserted(mData.size() - list.size(), list.size());
    }

    public void set(@NonNull T oldItem, @NonNull T newItem) {
        set(mData.indexOf(oldItem), newItem);
    }

    public void set(int index, @NonNull T item) {
        if (index >= 0 && index < getItemCount()) {
            mData.set(index, item);
            notifyItemChanged(index);
        }
    }

    public void remove(@NonNull T item) {
        remove(mData.indexOf(item));
    }

    public void remove(int index) {
        if (index >= 0 && index < getItemCount()) {
            mData.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void replaceAll(@NonNull List<T> item) {
        replaceAll(item, true);
    }

    /**
     * 替换数据
     *
     * @param item                 新数据列表
     * @param notifyDataSetChanged 是否执行：notifyItem...方法
     */
    public void replaceAll(@NonNull List<T> item, boolean notifyDataSetChanged) {
        if (notifyDataSetChanged) {
            clear();
            addAll(item);
        } else {
            mData.clear();
            mData.addAll(item);
        }
    }

    public boolean contains(@NonNull T item) {
        return mData.contains(item);
    }

    public void clear() {
        final int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
    }

    public T getItem(int position) {
        return position < 0 || position >= mData.size() ? null : mData.get(position);
    }

    @Override
    protected void setItemClick(YcRecyclerViewHolder ycRecyclerViewHolder) {
        if (null != mItemClickListener) {
            ycRecyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(ycRecyclerViewHolder, v, ycRecyclerViewHolder.getAdapterPosition());
                }
            });
        }
        if (null != mItemLongClickListener) {
            ycRecyclerViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mItemLongClickListener.onItemLongClick(ycRecyclerViewHolder, v, ycRecyclerViewHolder.getAdapterPosition());
                }
            });
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }
}
