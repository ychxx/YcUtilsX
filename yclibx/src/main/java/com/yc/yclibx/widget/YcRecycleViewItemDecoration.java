package com.yc.yclibx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.yc.yclibx.R;
import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.comment.YcUI;

/**
 *
 */
public class YcRecycleViewItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace = YcUI.pxToDp(80);//item间的间距
    private int mLineColor = 0xFF009997;//0xFFE9F7FF;
    private boolean mIsHasColor = true;//是否有颜色
    private boolean mIsHasBorder = true;//边缘是否需要分割线；线性布局的上下/左右，表格布局的四周
    private Paint mPaint;

    public YcRecycleViewItemDecoration() {
        mPaint = new Paint();
        mPaint.setColor(mLineColor);
    }

    public void setSpace(int space) {
        this.mSpace = YcUI.pxToDp(space);
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
        mPaint.setColor(mLineColor);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (!mIsHasColor)
            return;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getChildCount();//返回 可见 的item数量
        if (layoutManager instanceof LinearLayoutManager) {//线性布局
//            ((LinearLayoutManager) layoutManager).getOrientation()
            // 遍历每个Item，分别获取它们的位置信息，然后再绘制对应的分割线
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            @RecyclerView.Orientation int orientation = linearLayoutManager.getOrientation();
            for (int i = 0; i < childCount; i++) {
                // 每个Item的位置
                drawLinear(parent.getChildAt(i), c, orientation);
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {//表格式布局
            // 遍历每个Item，分别获取它们的位置信息，然后再绘制对应的分割线
            for (int i = 0; i < childCount; i++) {
                // 每个Item的位置
                drawStaggeredGrid(parent.getChildAt(i), c);
            }
        }
    }

    private void drawLinear(View child, @NonNull Canvas c, @RecyclerView.Orientation int orientation) {
        if (orientation == RecyclerView.HORIZONTAL) {
            if (child.getLeft() - mSpace <= 0)
                c.drawRect(child.getLeft() - mSpace, 0, mSpace, child.getBottom(), mPaint);
            c.drawRect(child.getRight(), child.getTop(), child.getRight() + mSpace, child.getBottom(), mPaint);
        } else if (orientation == RecyclerView.VERTICAL) {
            if (child.getTop() - mSpace <= 0)
                c.drawRect(0, child.getTop() - mSpace, child.getRight(), mSpace, mPaint);
            c.drawRect(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom() + mSpace, mPaint);
        }
    }

    private void drawStaggeredGrid(View child, @NonNull Canvas c) {
        c.drawRect(child.getLeft() - mSpace, child.getTop() - mSpace, child.getRight() + mSpace, mSpace, mPaint);//上
        c.drawRect(child.getLeft() - mSpace, child.getBottom(), child.getRight() + mSpace, child.getBottom() + mSpace, mPaint);//下
        c.drawRect(child.getLeft() - mSpace, child.getTop() - mSpace, child.getLeft(), child.getBottom() + mSpace, mPaint);//左
        c.drawRect(child.getRight(), child.getTop() - mSpace, child.getRight() + mSpace, child.getBottom() + mSpace, mPaint);//右
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int position = parent.getChildAdapterPosition(view);
        if (layoutManager instanceof LinearLayoutManager) {//线性布局
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            @RecyclerView.Orientation int orientation = linearLayoutManager.getOrientation();
            if (linearLayoutManager.getItemCount() != 1) {//当RecyclerView里的数据只有一个时，不用分割线
                itemOffsetsLinear(outRect, orientation, position);
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {//表格式布局
            itemOffsetsStaggeredGrid(outRect, position, ((StaggeredGridLayoutManager) layoutManager).getSpanCount());
        }
    }

    private void itemOffsetsLinear(@NonNull Rect outRect, @RecyclerView.Orientation int orientation, int position) {
        if (mIsHasBorder) {
            if (position == 0) {
                if (orientation == RecyclerView.HORIZONTAL) {
                    outRect.left = mSpace;
                } else if (orientation == RecyclerView.VERTICAL) {
                    outRect.top = mSpace;
                }
            }
            if (orientation == RecyclerView.HORIZONTAL) {
                outRect.right = mSpace;
            } else if (orientation == RecyclerView.VERTICAL) {
                outRect.bottom = mSpace;
            }
        } else {
            if (position == 0) {
                return;
            }
            if (orientation == RecyclerView.HORIZONTAL) {
                outRect.left = mSpace;
            } else if (orientation == RecyclerView.VERTICAL) {
                outRect.top = mSpace;
            }
        }
    }


    private void itemOffsetsStaggeredGrid(@NonNull Rect outRect, int position, int verticalNum) {
        int itemReallySumSpace;
        if (mIsHasBorder) {
            //设置行间距
            if (position < verticalNum) {
                outRect.top = mSpace;
            }
            outRect.bottom = mSpace;
            itemReallySumSpace = (int) ((verticalNum + 1) * mSpace * 1.0 / verticalNum);
            //设置列间距
        } else {
            //设置行间距
            if (position >= verticalNum) {
                outRect.top = mSpace;
            }
            //设置列间距
            itemReallySumSpace = (int) ((verticalNum - 1) * mSpace * 1.0 / verticalNum);
        }
        if (itemReallySumSpace == 0)
            return;
        int left = mSpace;
        int right = itemReallySumSpace - left;
        int horizontalPosition = position % verticalNum;
        for (int i = 0; i < horizontalPosition; i++) {
            left = mSpace - right;
            right = itemReallySumSpace - left;
        }
        outRect.left = left;
        outRect.right = right;
    }

}
