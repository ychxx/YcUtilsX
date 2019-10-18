package com.yc.yclibx.widget.spinner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yc.yclibx.comment.YcEmpty;

/**
 * 下拉框
 */
public class YcSpinnerView extends LinearLayout {
    private TextView mResultTv;
    private YcTriangleView mTriangleView;

    public YcSpinnerView(Context context) {
        super(context);
        init(context);
    }

    public YcSpinnerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YcSpinnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setGravity(Gravity.CENTER_VERTICAL);
        mResultTv = new TextView(context);
        mResultTv.setGravity(Gravity.CENTER);
        mResultTv.getPaint().setFakeBoldText(true);
        mResultTv.setTextColor(Color.BLACK);
        mResultTv.setLineSpacing(1, 1);
        mResultTv.setPadding(2, 2, 2, 2);
        ViewGroup.LayoutParams tvLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addView(mResultTv, tvLp);
        mTriangleView = new YcTriangleView(context);
        ViewGroup.LayoutParams triangleLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addView(mTriangleView, triangleLp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {//wrap_content. 需要计算
            Rect bounds = new Rect();
            //计算宽度 . 宽度和字的长度和字大小有关系 .所以画笔进行测量
            mResultTv.getPaint().getTextBounds(mResultTv.getText().toString(), 0, mResultTv.length(), bounds);
            heightSize = bounds.height();
        }
        setTriangleViewSize(heightSize);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setTriangleViewSize(int heightSize) {
        mTriangleView.setSize(heightSize, heightSize);
    }

    public void setSelectText(String msg) {
        mResultTv.setText(YcEmpty.getNoEmpty(msg));
    }
}
