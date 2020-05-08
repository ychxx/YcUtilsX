package com.yc.ycutilsx.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.yc.yclibx.adapter.YcPageAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PageAdapterNew extends YcPageAdapter {
    @StringDef({ViewPageAdapter.SwitchType.TYPE_1, ViewPageAdapter.SwitchType.TYPE_2})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SwitchType {
        String TYPE_1 = "1";
        String TYPE_2 = "2";
    }

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList1 = new ArrayList<>();
    private List<Fragment> mFragmentList2 = new ArrayList<>();
    @ViewPageAdapter.SwitchType
    private String mSwitchType = ViewPageAdapter.SwitchType.TYPE_1;

    public PageAdapterNew(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (mSwitchType) {
            default:
            case ViewPageAdapter.SwitchType.TYPE_1:
                return mFragmentList1.get(position);
            case ViewPageAdapter.SwitchType.TYPE_2:
                return mFragmentList2.get(position);
        }
    }

    public void addFragmentList1(Fragment fragment) {
        this.mFragmentList1.add(fragment);
    }

    public void addFragmentList2(Fragment fragment) {
        this.mFragmentList2.add(fragment);
    }

    private boolean isSwitchType = false;


    @Override
    public int getCount() {
        switch (mSwitchType) {
            default:
            case ViewPageAdapter.SwitchType.TYPE_1:
                return mFragmentList1.size();
            case ViewPageAdapter.SwitchType.TYPE_2:
                return mFragmentList2.size();
        }
    }

    public void switchType(@ViewPageAdapter.SwitchType String switchType) {
        if (!mSwitchType.equals(switchType)) {
            isSwitchType = true;
            mSwitchType = switchType;
            notifyDataSetChanged();
        }
        isSwitchType = false;
    }

    @Override
    protected int getFragmentIndex(Fragment data) {
        switch (mSwitchType) {
            default:
            case ViewPageAdapter.SwitchType.TYPE_1:
                return mFragmentList1.indexOf(data);
            case ViewPageAdapter.SwitchType.TYPE_2:
                return mFragmentList2.indexOf(data);
        }
    }

    @Override
    protected boolean isChangeType() {
        return isSwitchType;
    }

    public String getSwitchType() {
        return mSwitchType;
    }
}
