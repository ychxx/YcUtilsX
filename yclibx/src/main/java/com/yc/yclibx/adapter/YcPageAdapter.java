package com.yc.yclibx.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

/**
 * 支持动态添加Fragment
 */
public abstract class YcPageAdapter extends PagerAdapter {
    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<Fragment.SavedState>();
    private ArrayList<Item> mItems = new ArrayList<>();
    private Fragment mCurrentPrimaryItem = null;
    private boolean mNeedProcessCache = false;

    public YcPageAdapter(@NonNull FragmentManager fm) {
        mFragmentManager = fm;

    }

    protected abstract boolean isChangeType();

    protected abstract int getFragmentIndex(Fragment fragment);

    @NonNull
    public abstract Fragment getItem(int position);

    @Override
    public void startUpdate(ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // If we already have this item instantiated, there is nothing
        // to do.  This can happen when we are restoring the entire pager
        // from its saved state, where the fragment manager has already
        // taken care of restoring the fragments we previously had instantiated.
        if (mItems.size() > position) {
            Item item = mItems.get(position);
            if (item != null) {
                //判断位置是否相等，如果不相等说明新数据有增加或删除(导致了ViewPager那边有空位)，
                // 而这时notifyDataSetChanged方法还没有完成，ViewPager会先调用instantiateItem来获取新的页面
                //所以为了不取错页面，我们需要对缓存进行检查和调整位置：checkProcessCacheChanged
                if (item.fragmentIndex == position) {
                    return item;
                } else {
                    checkProcessCacheChanged();
                }
            }
        }
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        Fragment fragment = getItem(position);
        if (mSavedState.size() > position) {
            Fragment.SavedState fss = mSavedState.get(position);
            if (fss != null) {
                fragment.setInitialSavedState(fss);
            }
        }
        while (mItems.size() <= position) {
            mItems.add(null);
        }
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        Item item = new Item(fragment, position);
        mItems.set(position, item);
        mCurTransaction.add(container.getId(), fragment);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Item ii = (Item) object;

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        while (mSavedState.size() <= position) {
            mSavedState.add(null);
        }
        mSavedState.set(position, ii.fragment.isAdded() ? mFragmentManager.saveFragmentInstanceState(ii.fragment) : null);
        mItems.set(position, null);
        mCurTransaction.remove(ii.fragment);
    }

    @Override
    @SuppressWarnings("ReferenceEquality")
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Item ii = (Item) object;
        Fragment fragment = ii.fragment;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Fragment fragment = ((Item) object).fragment;
        return fragment.getView() == view;
    }


    @Override
    public int getItemPosition(Object object) {
        mNeedProcessCache = true;
        Item itemInfo = (Item) object;
        int oldPosition = mItems.indexOf(itemInfo);
        if (oldPosition >= 0) {
            if (!isChangeType()) {
                return POSITION_UNCHANGED;
            } else {
                Item oldItemInfo = mItems.get(oldPosition);
                int oldDataNewPosition = getFragmentIndex(itemInfo.fragment);
                if (oldDataNewPosition < 0) {
                    oldDataNewPosition = POSITION_NONE;
                }
                //把新的位置赋值到缓存的itemInfo中，以便调整时使用
                if (oldItemInfo != null) {
                    oldItemInfo.fragmentIndex = oldDataNewPosition;
                }
                return oldDataNewPosition;
            }
        }
        return POSITION_UNCHANGED;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //通知ViewPager更新完成后对缓存的ItemInfo List进行调整
        checkProcessCacheChanged();
    }

    private void checkProcessCacheChanged() {
        //只有调用过getItemPosition(也就是有notifyDataSetChanged)才进行缓存的调整
        if (!mNeedProcessCache) return;
        mNeedProcessCache = false;
        ArrayList<Item> newItems = new ArrayList<>(mItems.size());
        //先存入空数据
        for (int i = 0; i < mItems.size(); i++) {
            newItems.add(null);
        }
        //根据缓存的itemInfo中的新position把itemInfo入正确的位置
        for (Item item : mItems) {
            if (item != null) {
                if (item.fragmentIndex >= 0) {
                    while (newItems.size() <= item.fragmentIndex) {
                        newItems.add(null);
                    }
                    newItems.set(item.fragmentIndex, item);
                }
            }
        }
        mItems = newItems;
    }

    @Override
    public Parcelable saveState() {
        Bundle state = null;
        if (mSavedState.size() > 0) {
            state = new Bundle();
            Fragment.SavedState[] fss = new Fragment.SavedState[mSavedState.size()];
            mSavedState.toArray(fss);
            state.putParcelableArray("states", fss);
        }
        for (int i = 0; i < mItems.size(); i++) {
            Item item = mItems.get(i);
            if (item != null) {
                Fragment f = item.fragment;
                if (f != null && f.isAdded()) {
                    if (state == null) {
                        state = new Bundle();
                    }
                    String key = "f" + i;
                    mFragmentManager.putFragment(state, key, f);
                }
            }
        }
        return state;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle.getParcelableArray("states");
            mSavedState.clear();
            mItems.clear();
            if (fss != null) {
                for (int i = 0; i < fss.length; i++) {
                    mSavedState.add((Fragment.SavedState) fss[i]);
                }
            }
            Iterable<String> keys = bundle.keySet();
            for (String key : keys) {
                if (key.startsWith("f")) {
                    int index = Integer.parseInt(key.substring(1));
                    Fragment f = mFragmentManager.getFragment(bundle, key);
                    if (f != null) {
                        while (mItems.size() <= index) {
                            mItems.add(null);
                        }
                        f.setMenuVisibility(false);
                        Item iiNew = new Item(f, index);
                        mItems.set(index, iiNew);
                    } else {
//                        Log.w(TAG, "Bad fragment at key " + key);
                    }
                }
            }
        }
    }

    protected Fragment getCurrentPrimaryItem() {
        return mCurrentPrimaryItem;
    }

    protected Fragment getFragmentByPosition(int position) {
        if (position < 0 || position >= mItems.size()) return null;
        return mItems.get(position).fragment;
    }

    protected static class Item {
        Fragment fragment;
        int fragmentIndex = -1;

        Item(Fragment fragment, int fragmentIndex) {
            this.fragment = fragment;
            this.fragmentIndex = fragmentIndex;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public int getFragmentIndex() {
            return fragmentIndex;
        }

        public void setFragmentIndex(int fragmentIndex) {
            this.fragmentIndex = fragmentIndex;
        }
    }
}
