package com.yc.yclibx.comment;

import com.yc.yclibx.YcManage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 接口轮循器
 */
public class YcLoopTime implements YcOnDestroy {
    private YcLoop.ExecuteCondition mExecuteCondition;
    private int mPeriodTime = 200;//间隔时间
    private Disposable mDisposable;
    private YcLoop.ExecuteCall mExecuteCall;
    private boolean mIsLoop = false;//是否一直执行回调
    private AtomicLong mPreviousTime = new AtomicLong(-200L);//上次执行时间

    public YcLoopTime(YcManage ycManage) {
        ycManage.add(this);
    }

    public YcLoopTime setLoop(boolean loop) {
        mIsLoop = loop;
        return this;
    }

    /**
     * 设置执行判断条件（true时执行，false时继续循环）
     */
    public YcLoopTime setExecuteCondition(YcLoop.ExecuteCondition executeCondition) {
        this.mExecuteCondition = executeCondition;
        return this;
    }

    /**
     * 设置间隔时间
     */
    public YcLoopTime setPeriodTime(int periodTime) {
        this.mPeriodTime = periodTime;
        mPreviousTime.set(-periodTime);
        return this;
    }

    /**
     * 执行的回调
     */
    public YcLoopTime setExecuteCall(YcLoop.ExecuteCall executeCall) {
        this.mExecuteCall = executeCall;
        return this;
    }

    /**
     * 重置上次执行时间，即变为立刻执行(必须符合setExecuteCondition里设置的条件)
     */
    public void resetPeriodTime() {
        mPreviousTime.set(-mPeriodTime);
    }

    public void start() {
        if (mDisposable != null) {
            mDisposable.dispose();
            resetPeriodTime();
        }
        mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)//每秒执行一次
                .filter(aLong -> (aLong - mPreviousTime.get()) >= mPeriodTime)//过滤掉不足间隔时间
                .filter(aLong -> {//条件判断
                    if (mExecuteCondition == null) {
                        return true;
                    } else {
                        return mExecuteCondition.isExecute();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//切换到mainThread线程，防止报错
                .subscribe(aLong -> {
                    if (!mIsLoop) {
                        mDisposable.dispose();
                    }
                    mPreviousTime.set(aLong);//将此次时间设置为上次执行时间
                    if (mExecuteCall != null) {
                        try {
                            mExecuteCall.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null)
            mDisposable.dispose();
    }

    public interface ExecuteCall {
        void execute();
    }

    public interface ExecuteCondition {
        boolean isExecute();
    }
}
