package com.yc.ycutilsx.fragment;

import android.os.Parcelable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    @StringDef({SwitchType.TYPE_1, SwitchType.TYPE_2})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SwitchType {
        String TYPE_1 = "1";
        String TYPE_2 = "2";
    }

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList1 = new ArrayList<>();
    private List<Fragment> mFragmentList2 = new ArrayList<>();
    @SwitchType
    private String mSwitchType = SwitchType.TYPE_1;

    public ViewPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    public void addFragmentList1(Fragment fragment) {
        this.mFragmentList1.add(fragment);
    }

    public void addFragmentList2(Fragment fragment) {
        this.mFragmentList2.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (mSwitchType) {
            default:
            case SwitchType.TYPE_1:
                return mFragmentList1.get(position);
            case SwitchType.TYPE_2:
                return mFragmentList2.get(position);
        }
    }

    @Override
    public int getCount() {
        switch (mSwitchType) {
            default:
            case SwitchType.TYPE_1:
                return mFragmentList1.size();
            case SwitchType.TYPE_2:
                return mFragmentList2.size();
        }
    }


    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        if (!((Fragment) object).isAdded()) {
            return PagerAdapter.POSITION_NONE;
        }
        if (!mFragmentList1.contains(object) || !mFragmentList2.contains(object)) {
            return PagerAdapter.POSITION_NONE;
        } else {
            switch (mSwitchType) {
                default:
                case SwitchType.TYPE_1:
                    return mFragmentList1.indexOf(object);
                case SwitchType.TYPE_2:
                    return mFragmentList2.indexOf(object);
            }
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment instantiateItem = ((Fragment) super.instantiateItem(container, position));
        Fragment item = getItem(position);
        if (instantiateItem == item) {
            return instantiateItem;
        } else {
            //如果集合中对应下标的fragment和fragmentManager中的对应下标的fragment对象不一致，那么就是新添加的，所以自己add进入；这里为什么不直接调用super方法呢，因为fragment的mIndex搞的鬼，以后有机会再补一补。
            mFragmentManager.beginTransaction().add(container.getId(), item).commitNowAllowingStateLoss();
            return item;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        //如果getItemPosition中的值为PagerAdapter.POSITION_NONE，就执行该方法。
        switch (mSwitchType) {
            default:
            case SwitchType.TYPE_1:
                if (mFragmentList1.contains(fragment)) {
                    super.destroyItem(container, position, fragment);
                    return;
                }
            case SwitchType.TYPE_2:
                if (mFragmentList2.contains(fragment)) {
                    super.destroyItem(container, position, fragment);
                    return;
                }
        }

        //自己执行移除。因为mFragments在删除的时候就把某个fragment对象移除了，所以一般都得自己移除在fragmentManager中的该对象。
        mFragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss();

    }

    public void switchType(@SwitchType String switchType) {
        mSwitchType = switchType;
        notifyDataSetChanged();
    }
}
