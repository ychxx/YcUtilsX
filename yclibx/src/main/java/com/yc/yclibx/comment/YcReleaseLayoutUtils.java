package com.yc.yclibx.comment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.yc.yclibx.R;

/**
 * 替换布局
 */
public class YcReleaseLayoutUtils {
    @LayoutRes
    private static int mEmptyIdRes = R.layout.yc_layout_empty;
    @LayoutRes
    private static int mLoadingIdRes = R.layout.yc_layout_loading;
    @LayoutRes
    private static int mNotNetIdRes = R.layout.yc_layout_not_net;
    private final static String CONTAINER_TAG = "CONTAINER_TAG";
    private final static String LAYOUT_TAG = "LAYOUT_TAG";

    /**
     * 设置 没有数据 时显示的页面
     */
    public static void setEmptyIdRes(int emptyIdRes) {
        mEmptyIdRes = emptyIdRes;
    }

    /**
     * 设置 加载中 时显示的页面
     */
    public static void setLoadingIdRes(int loadingIdRes) {
        mLoadingIdRes = loadingIdRes;
    }

    /**
     * 设置 没有网络 时显示的页面
     */
    public static void setNotNetIdRes(int notNetIdRes) {
        mNotNetIdRes = notNetIdRes;
    }

    public static void showEmptyLayout(View originalView) {
        showLayout(originalView, mEmptyIdRes);
    }

    public static void showLoadingLayout(View originalView) {
        showLayout(originalView, mLoadingIdRes);
    }

    /**
     * 设置加载网络图片失败时显示的图片
     */
    public static void showNotNetLayout(View originalView) {
        showLayout(originalView, mNotNetIdRes);
    }

    /**
     * 恢复显示原来的View
     */
    public static void recovery(View originalView) {
        String containerTag = CONTAINER_TAG + originalView.getId();
        FrameLayout mContainer = null;
        if (originalView.getParent() instanceof ViewGroup) {
            ViewGroup viewParentGroup = (ViewGroup) originalView.getParent();
            //查找mContainer是否已经创建过
            if (containerTag.equals(viewParentGroup.getTag() + "")) {
                mContainer = (FrameLayout) originalView.getParent();
            } else {
                for (int i = 0; i < viewParentGroup.getChildCount(); i++) {
                    if (containerTag.equals(viewParentGroup.getChildAt(i).getTag() + "")) {
                        mContainer = (FrameLayout) viewParentGroup.getChildAt(i);
                        break;
                    }
                }
            }
            if (mContainer == null) {
                originalView.setVisibility(View.VISIBLE);
            } else {
                showAndHideView(mContainer, originalView);
            }
        }
    }

    /**
     * 隐藏原来的originalView 并显示releaseLayout
     *
     * @param originalView  原来的View
     * @param releaseLayout 替换显示的View
     */
    public static void showLayout(View originalView, @LayoutRes int releaseLayout) {
        String containerTag = CONTAINER_TAG + originalView.getId();
        FrameLayout mContainer = null;
        if (originalView.getParent() instanceof ViewGroup) {
            ViewGroup viewParentGroup = (ViewGroup) originalView.getParent();
            //查找mContainer是否已经创建过
            if (containerTag.equals(viewParentGroup.getTag() + "")) {
                mContainer = (FrameLayout) originalView.getParent();
            } else {
                for (int i = 0; i < viewParentGroup.getChildCount(); i++) {
                    if (containerTag.equals(viewParentGroup.getChildAt(i).getTag() + "")) {
                        mContainer = (FrameLayout) viewParentGroup.getChildAt(i);
                        break;
                    }
                }
            }
            if (mContainer == null) {
                //创建FrameLayout来存放switchView和Layout生成的View
                mContainer = new FrameLayout(viewParentGroup.getContext());
                mContainer.setTag(containerTag);//设置Tag用于标示
                mContainer.setLayoutParams(originalView.getLayoutParams());//将布局设置成switchView一样
                //从viewParentGroup移除switchView后，再将switchView添加mContainer里
                int index = viewParentGroup.indexOfChild(originalView);
                viewParentGroup.removeView(originalView);
                originalView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mContainer.addView(originalView);
                viewParentGroup.addView(mContainer, index);
            }
            View layoutView = null;
            String layoutViewTag = LAYOUT_TAG + releaseLayout;
            for (int i = 0; i < mContainer.getChildCount(); i++) {
                if (layoutViewTag.equals(mContainer.getChildAt(i).getTag() + "")) {
                    layoutView = mContainer.getChildAt(i);
                    break;
                }
            }
            if (layoutView == null) {
                layoutView = createView(viewParentGroup.getContext(), viewParentGroup, releaseLayout);
                layoutView.setTag(LAYOUT_TAG);
                mContainer.addView(layoutView);
            }
            showAndHideView(mContainer, layoutView);
        } else {
            Log.e("视图隐藏和显示", "该View的getParent()获取到的不是ViewGroup");
        }
    }

    /**
     * 根据@param layoutRes生成View
     */
    private static View createView(Context context, @Nullable ViewGroup root, @LayoutRes int layoutRes) {
        return LayoutInflater.from(context).inflate(layoutRes, root, false);
    }

    /**
     * 显示containerViewGroup里的showView,并隐藏containerViewGroup里除了showView的其他View
     *
     * @param containerViewGroup 父容器
     * @param showView           显示的View
     */
    private static void showAndHideView(ViewGroup containerViewGroup, View showView) {
        for (int i = 0; i < containerViewGroup.getChildCount(); i++) {
            if (containerViewGroup.indexOfChild(showView) == i) {
                containerViewGroup.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                containerViewGroup.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }
}
