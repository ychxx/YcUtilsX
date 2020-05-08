package com.yc.ycutilsx.fragment;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.yc.ycutilsx.R;

/**
 *
 */
public class PageViewActivity extends AppCompatActivity {
    ViewPager mViewPager;
    TabLayout mTabLayout;
    TestFragment mTestFragment11;
    TestFragment mTestFragment12;
    TestFragment2 mTestFragment21;
    TestFragment2 mTestFragment22;
    PageAdapterNew mPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pageview_activity);
        mViewPager = findViewById(R.id.vp);
        mTabLayout = findViewById(R.id.tab);
        mTestFragment11 = TestFragment.newInstance("这是11");
        mTestFragment12 = TestFragment.newInstance("这是12");
        mTestFragment21 = TestFragment2.newInstance("这是21");
        mTestFragment22 = TestFragment2.newInstance("这是22");
        mPagerAdapter = new PageAdapterNew(getSupportFragmentManager());
        mPagerAdapter.addFragmentList1(mTestFragment11);
        mPagerAdapter.addFragmentList1(mTestFragment12);
        mPagerAdapter.addFragmentList2(mTestFragment21);
        mPagerAdapter.addFragmentList2(mTestFragment12);
        mPagerAdapter.addFragmentList2(mTestFragment22);
        mViewPager.setOffscreenPageLimit(5);//设置缓存
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mPagerAdapter.getSwitchType().equals(ViewPageAdapter.SwitchType.TYPE_1)) {
                    mTestFragment11.setTitle("这是1-11！！");
                    mTestFragment12.setTitle("这是1-12！！");
                } else {
                    mTestFragment21.setTitle("这是2-21！！");
                    mTestFragment12.setTitle("这是2-12！！");
                    mTestFragment22.setTitle("这是2-22！！");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        findViewById(R.id.button).setOnClickListener(v -> {
            mPagerAdapter.switchType(ViewPageAdapter.SwitchType.TYPE_1);
            mTestFragment11.setTitle("这是1-11！！");
            mTestFragment12.setTitle("这是1-12！！");
        });
        findViewById(R.id.button2).setOnClickListener(v -> {
            mPagerAdapter.switchType(ViewPageAdapter.SwitchType.TYPE_2);
            mTestFragment21.setTitle("这是2-21！！");
            mTestFragment12.setTitle("这是2-12！！");
            mTestFragment22.setTitle("这是2-22！！");
        });
    }
}
