package com.yc.yclibx.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.yc.yclibx.R;
import com.yc.yclibx.bean.YcBannerBean;
import com.yc.yclibx.comment.YcUI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * 自定义Banner无限轮播控件
 */
public class YcBannerView extends RelativeLayout {

    ViewPager viewPager;
    LinearLayout points;
    private CompositeDisposable mCompositeDisposable;
    //默认轮播时间，3s
    private int delayTime = 3;
    //    private List<ImageView> imageViewList;
    private List<YcBannerBean> bannerList;
    //下方点 选中显示的颜色
    private int selectRes = R.drawable.shape_dots_select;
    //下方点 没选中显示的颜色
    private int unSelectRes = R.drawable.shape_dots_unselect;
    //当前页的下标
    private int currentPos;

    private boolean isStopScroll = false;//是否停止轮播
    YcBannerAdapter.ViewPagerOnItemClickListener mItemClickListener;//点击回调事件

    public YcBannerView(Context context) {
        this(context, null);
    }


    public YcBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public YcBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.yc_widget_banner, this, true);
        viewPager = (ViewPager) findViewById(R.id.layout_banner_viewpager);
        points = (LinearLayout) findViewById(R.id.layout_banner_points_group);
    }


    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    public YcBannerView delayTime(int time) {
        this.delayTime = time;
        return this;
    }


    /**
     * 设置Points资源 Res
     *
     * @param selectRes   选中状态
     * @param unselcetRes 非选中状态
     */
    public void setPointsRes(int selectRes, int unselcetRes) {
        this.selectRes = selectRes;
        this.unSelectRes = unselcetRes;
    }

    /**
     * 图片轮播需要传入参数
     */
    public void build(List<YcBannerBean> list) {
        destroy();
        if (list.size() == 0) {
            this.setVisibility(GONE);
            return;
        }

        bannerList = new ArrayList<>();
        bannerList.clear();
        bannerList.addAll(list);
        final int pointSize;
        pointSize = bannerList.size();
        //判断是否清空 指示器点
        if (points.getChildCount() != 0) {
            points.removeAllViewsInLayout();
        }
        //初始化与个数相同的指示器点
        for (int i = 0; i < pointSize; i++) {
            View dot = new View(getContext());
            dot.setBackgroundResource(unSelectRes);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(YcUI.dpToPx(5), YcUI.dpToPx(5));
            params.leftMargin = 10;
            dot.setLayoutParams(params);
            dot.setEnabled(false);
            points.addView(dot);
        }

        points.getChildAt(0).setBackgroundResource(selectRes);
        //监听图片轮播，改变指示器状态
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                pos = pos % pointSize;
                currentPos = pos;
                for (int i = 0; i < points.getChildCount(); i++) {
                    points.getChildAt(i).setBackgroundResource(unSelectRes);
                }
                points.getChildAt(pos).setBackgroundResource(selectRes);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isStopScroll) {
                            startScroll();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING://拖动状态
                        stopScroll();
                        mCompositeDisposable.clear();
                        break;
                }
            }
        });
        YcBannerAdapter bannerAdapter = new YcBannerAdapter(list, getContext());
        viewPager.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
        bannerAdapter.setViewPagerOnItemClickListener(mItemClickListener);
        //图片开始轮播
        startScroll();
    }

    /**
     * 设置ViewPager的Item点击回调事件
     */
    public YcBannerView setOnItemClickListener(YcBannerAdapter.ViewPagerOnItemClickListener viewPagerOnItemClickListener) {
        mItemClickListener = viewPagerOnItemClickListener;
        return this;
    }

    /**
     * 图片开始轮播
     */
    private void startScroll() {
        mCompositeDisposable = new CompositeDisposable();
        isStopScroll = false;
        Disposable disposable = Observable.timer(delayTime, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (isStopScroll) {
                        return;
                    }
                    isStopScroll = true;
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                });
        mCompositeDisposable.add(disposable);
    }
//
//    /**
//     * 设置默认显示的轮播图，必须在build前设置
//     *
//     * @param resImg 本地资源id
//     */
//    private void setDefaultImg(int resImg) {
//        defaultImg = resImg;
//    }

    /**
     * 图片停止轮播
     */
    private void stopScroll() {
        isStopScroll = true;
    }


    public void destroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    private double mRatio = 280.0 / 840;

    /**
     * 设置高宽比例
     *
     * @param ratio 比例(高/宽)
     */
    public void setHeightAndWidthRatio(double ratio) {
        if (ratio > 0)
            mRatio = ratio;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            this.getHeight();
            int width = this.getWidth();
            ViewGroup.LayoutParams params = this.getLayoutParams();
            params.height = (int) (width * mRatio);
            params.width = width;
            this.setLayoutParams(params);
        }
    }
}
