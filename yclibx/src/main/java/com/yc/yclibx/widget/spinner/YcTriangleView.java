package com.yc.yclibx.widget.spinner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 *  圆角三角形
 */
public class YcTriangleView extends View {
    private int mWith = 10;
    private int mHeight = 10;
    Paint mPaint;

    public YcTriangleView(Context context) {
        super(context);
        init();
    }

    public YcTriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YcTriangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画个圆角三角形
        Path path = new Path();
        int pandding = mWith/10;
        int radius = mWith/10;
        float x1, x2, x3, x4, x5, x6, y1, y2, y3, y4, y5, y6;
        x1 = (float) (pandding + Math.sqrt(2) * radius);
        y1 = pandding;
        x2 = mWith - (float) (pandding + Math.sqrt(2) * radius);
        y2 = pandding;
        x3 = mWith - pandding;
        y3 = (float) (pandding + Math.sqrt(2) * radius);
        x4 = mWith / 2.0f + radius;
        y4 = mHeight - (float) (pandding + Math.sqrt(2) * radius);
        x5 = mWith / 2.0f - radius;
        y5 = mHeight - (float) (pandding + Math.sqrt(2) * radius);
        x6 = pandding;
        y6 = (float) (pandding + Math.sqrt(2) * radius);
        path.moveTo(x1, y1);// 此点为多边形的起点
        path.lineTo(x2, y2);
        path.quadTo(mWith - pandding, pandding, x3, y3);
        path.lineTo(x4, y4);
        path.quadTo(mWith / 2, mHeight - pandding, x5, y5);
        path.lineTo(x6, y6);
        path.quadTo(pandding, pandding, x1, y1);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(mWith, mHeight);
        setMeasuredDimension(mWith, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWith = w;
        invalidate();
    }

    public void setSize(int with, int height) {
        mWith = with;
        mHeight = height;
    }
}
