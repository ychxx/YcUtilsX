package com.yc.yclibx.release;

import android.view.View;

import androidx.annotation.DrawableRes;

/**
 *
 */
public interface ISpecialState {
    /**
     * 设置类型
     *
     * @param specialState
     */
    void setSpecialState(@SpecialState int specialState);

    /**
     * 显示
     */
    void show();

    /**
     * 恢复
     */
    void recovery();

    /**
     * 设置标题栏(默认标题栏为GONE)
     */
    default void setTitle(String title) {
    }

    /**
     * 设置内容
     */
    default void setContent(String content) {
    }

    /**
     * 设置内容上方的图片
     */
    default void setContentImg(@DrawableRes int imgRes) {
    }

    /**
     * 设置标题栏左侧点击事件(默认标题栏为GONE)
     */
    default void setTitleOnClickLeft(View.OnClickListener clickLeft) {
    }

    /**
     * 设置特殊布局里的按钮点击事件(默认为隐藏)
     */
    default void setSpecialLayoutOnClick(View.OnClickListener click) {
    }

    /**
     * 设置特殊布局里的按钮名（为空时隐藏按钮）
     */
    default void setSpecialLayoutClickText(String clickText) {
    }
}
