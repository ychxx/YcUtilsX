package com.yc.yclibx.release;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.yclibx.R;
import com.yc.yclibx.comment.YcLog;

/**
 *
 */
public abstract class YcSpecialRecycleView extends FrameLayout implements ISpecialState {
    protected Context mContext;
    protected RecyclerView mRecyclerView;
    protected View mReleaseView;

    protected boolean isReleaseViewShow = false;
    @SpecialState
    protected int mSpecialState;

    public YcSpecialRecycleView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public YcSpecialRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YcSpecialRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        mContext = context;
        mRecyclerView = new RecyclerView(context) {
            @Override
            public void setVisibility(int visibility) {
                isReleaseViewShow = visibility != View.VISIBLE;
                super.setVisibility(visibility);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        this.addView(mRecyclerView);
    }

    /**
     * 创建一个替换用的布局
     */
    public abstract void createReleaseView();

    @Override
    public void setSpecialState(int specialState) {
        mSpecialState = specialState;
    }

    /**
     * 显示
     */
    @Override
    public synchronized void show() {
        if (isReleaseViewShow)
            return;
        isReleaseViewShow = true;
        if (mReleaseView == null) {
            createReleaseView();
            this.addView(mReleaseView);
        }
        onUpdate(mSpecialState);
        mRecyclerView.setVisibility(View.GONE);
        mReleaseView.setVisibility(View.VISIBLE);
    }

    protected abstract void onUpdate(int specialState);

    /**
     * 恢复
     */
    @Override
    public synchronized void recovery() {
        if (mReleaseView != null) {
            mReleaseView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void smoothScrollToPosition(int position) {
        if (position >= 0) {
            mRecyclerView.smoothScrollToPosition(position);
        }
    }

    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }
}
