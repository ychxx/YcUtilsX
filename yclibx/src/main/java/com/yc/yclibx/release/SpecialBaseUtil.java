package com.yc.yclibx.release;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.yc.yclibx.R;
import com.yc.yclibx.comment.YcEmpty;
import com.yc.yclibx.comment.YcLog;

/**
 *
 */
public abstract class SpecialBaseUtil implements ISpecialState {

    protected View mOriginalView;
    protected View mReleaseView;
    protected Activity mActivity;
    protected boolean isReleaseViewShow = false;
    @SpecialState
    protected int mSpecialState = SpecialState.NET_ERROR;

    public SpecialBaseUtil(Activity context, View originalView) {
        mOriginalView = originalView;
        mActivity = context;
    }

    public SpecialBaseUtil(Activity activity) {
        mActivity = activity;

    }


    @Override
    public void setSpecialState(int specialState) {
        mSpecialState = specialState;
    }

    @Override
    public synchronized void show() {
        if (isReleaseViewShow)
            return;
        isReleaseViewShow = true;
        createReleaseView();

        onUpdate(mSpecialState);
        if (mOriginalView == null) {
            mOriginalView = mActivity.findViewById(android.R.id.content);
        }
        YcReleaseLayoutUtils.replace(mOriginalView, mReleaseView);
    }

    protected abstract void createReleaseView();

    protected abstract void onUpdate(int specialState);

    @Override
    public synchronized void recovery() {
        if (!isReleaseViewShow)
            return;
        isReleaseViewShow = false;
        YcReleaseLayoutUtils.recovery(mOriginalView);
    }
}
