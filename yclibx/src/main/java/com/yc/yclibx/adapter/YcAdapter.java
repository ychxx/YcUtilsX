package com.yc.yclibx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class YcAdapter<T> extends BaseAdapter {
    private final Context mContext;
    private final int mLayoutResId;
    protected List<T> mData;

    public YcAdapter(@NonNull Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    public YcAdapter(@NonNull Context context, int layoutResId, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.mContext = context;
        this.mLayoutResId = layoutResId;
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        if (data != null)
            mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return position < 0 || position >= mData.size() ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getLayoutResId(T item, int position) {
        return this.mLayoutResId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final T item = getItem(position);
        final YcAdapterHelper helper = YcAdapterHelper.get(mContext, convertView, parent,
                getLayoutResId(item, position), position);
        onUpdate(helper, item, position);
        return helper.getView();
    }

    public abstract void onUpdate(@NonNull YcAdapterHelper helper, @NonNull T item, @NonNull int position);

    @Override
    public boolean isEnabled(int position) {
        return position < mData.size();
    }

    public void add(@NonNull T item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addAll(@NonNull List<T> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void set(@NonNull T oldItem, @NonNull T newItem) {
        set(mData.indexOf(oldItem), newItem);
    }

    public void set(int index, @NonNull T item) {
        if (index >= 0 && index < getCount()) {
            mData.set(index, item);
            notifyDataSetChanged();
        }
    }

    public void remove(@NonNull T item) {
        mData.remove(item);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        if (index >= 0 && index < getCount()) {
            mData.remove(index);
            notifyDataSetChanged();
        }
    }

    public void replaceAll(@NonNull List<T> item) {
        mData.clear();
        mData.addAll(item);
        notifyDataSetChanged();
    }

    public boolean contains(@NonNull T item) {
        return mData.contains(item);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
}
