package com.yc.yclibx.release;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.yclibx.R;

/**
 * 替换布局
 */
public class YcReleaseLayoutUtils {

    protected final static String CONTAINER_TAG = "YC_CONTAINER_TAG";

    public static void replace(Activity activity, View releaseView) {
        replace(activity.findViewById(android.R.id.content), releaseView);
    }

    public static void replace(Activity activity, @LayoutRes int releaseLayoutRes) {
        replace(activity.findViewById(android.R.id.content), createView(activity, releaseLayoutRes));
    }

    public static void replace(View originalView, @LayoutRes int layoutRes) {
        replace(originalView, createView(originalView.getContext(), layoutRes));
    }

    /**
     * 隐藏原来的originalView 并显示releaseLayout
     *
     * @param originalView 原来的View
     * @param releaseView  替换显示的View
     */
    public static void replace(View originalView, View releaseView) {
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
                mContainer = new FrameLayout(originalView.getContext());
                mContainer.setTag(containerTag);//设置Tag用于标示
                ViewGroup.LayoutParams originalViewLayoutParams = originalView.getLayoutParams();
                if (originalView instanceof RecyclerView) {
                    mContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                } else {
                    mContainer.setLayoutParams(originalViewLayoutParams);//将布局设置成switchView一样
                }

                //从viewParentGroup移除switchView后，再将switchView添加mContainer里
                int index = viewParentGroup.indexOfChild(originalView);
                viewParentGroup.removeView(originalView);
                originalView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mContainer.addView(originalView);
                viewParentGroup.addView(mContainer, index);
            }
            if (mContainer.indexOfChild(releaseView) == -1) {
                mContainer.addView(releaseView);
            }
            showAndHideView(mContainer, releaseView);
        } else {
            Log.e("视图隐藏和显示", "该View的getParent()获取到的不是ViewGroup");
        }
    }

    public static void recovery(Activity activity) {
        recovery(activity.findViewById(android.R.id.content));
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
     * 显示containerViewGroup里的showView,并隐藏containerViewGroup里除了showView的其他View
     *
     * @param containerViewGroup 父容器
     * @param showView           显示的View
     */
    public static void showAndHideView(ViewGroup containerViewGroup, View showView) {
        for (int i = 0; i < containerViewGroup.getChildCount(); i++) {
            if (containerViewGroup.indexOfChild(showView) == i) {
                containerViewGroup.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                containerViewGroup.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    public static View createView(Context context, @LayoutRes int layoutRes) {
        return LayoutInflater.from(context).inflate(layoutRes, null, false);
    }

}