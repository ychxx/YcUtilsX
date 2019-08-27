package com.yc.yclibx.widget.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.yc.yclibx.R;
import com.yc.yclibx.bean.YcBannerBean;
import com.yc.yclibx.file.YcImgUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/8/7 21:18
 * 100332338@qq.com
 * <p/>
 * Banner适配器
 */
public class YcBannerAdapter extends PagerAdapter {
    private List<YcBannerBean> mBannerData = new ArrayList<>();
    private List<ImageView> mImageViewList = new ArrayList<>();
    private ViewPagerOnItemClickListener mViewPagerOnItemClickListener;
    private int defaultImg = R.drawable.img_loading;//默认显示的轮播图

    YcBannerAdapter(List<YcBannerBean> list, Context context) {
        this.mBannerData = list;
        for (YcBannerBean banner : mBannerData) {
            ImageView mImageView = new ImageView(context);
            YcImgUtils.loadNetImg(context, banner.getImg(), mImageView);
        }
        if (mBannerData.size() == 2) {
            for (int i = 0; i < 2; i++) {
                ImageView mImageView = new ImageView(context);
                YcImgUtils.loadNetImg(context, mBannerData.get(i).getImg(), mImageView);
            }
        }
    }

    void setViewPagerOnItemClickListener(ViewPagerOnItemClickListener mViewPagerOnItemClickListener) {
        this.mViewPagerOnItemClickListener = mViewPagerOnItemClickListener;
    }

    @Override
    public int getCount() {
        //当图片大于1张时候才可以轮播
        if (mBannerData.size() > 1)
            return Integer.MAX_VALUE;
        else
            return mBannerData.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项

        if (position < 0) {
            position = mImageViewList.size() + position;
        }
        position %= mImageViewList.size();
        ImageView imageView = mImageViewList.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = imageView.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(imageView);
        }
        imageView.setTag(position);
        imageView.setOnClickListener(v1 -> {
            if (mViewPagerOnItemClickListener != null) {
                int position1 = (int) v1.getTag();
                mViewPagerOnItemClickListener.onItemClick(mBannerData.get(position1 % mBannerData.size()));
            }
        });
        container.addView(imageView);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }


    public interface ViewPagerOnItemClickListener {
        /**
         * 轮播图点击事件
         */
        void onItemClick(YcBannerBean position);
    }
}
