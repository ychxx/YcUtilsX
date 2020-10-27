package com.yc.yclibx.widget.tv;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 单行可以水平滑动的TextView
 */
public class SingleLineScrollTv extends AppCompatTextView {
    public SingleLineScrollTv(Context context) {
        this(context, null);
    }

    public SingleLineScrollTv(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleLineScrollTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        this.setLines(1);
        this.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.setHorizontallyScrolling(true); // 不让超出屏幕的文本自动换行，使用滚动条
    }
}
