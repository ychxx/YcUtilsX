package com.yc.yclibx.comment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 *
 */
public class YcLoop {
    private ExecuteCondition mExecuteCondition;
    private int mPeriodTime = 200;//间隔时间
    private Disposable mDisposable;
    private ExecuteCall mExecuteCall;

    public YcLoop() {
    }

    /**
     * 设置执行判断条件（true时执行，false时继续循环）
     */
    public void setExecuteCondition(ExecuteCondition executeCondition) {
        this.mExecuteCondition = executeCondition;
    }

    /**
     * 设置间隔时间
     */
    public void setPeriodTime(int periodTime) {
        this.mPeriodTime = periodTime;
    }

    /**
     * 执行的回调
     */
    public void setExecuteCall(ExecuteCall executeCall) {
        this.mExecuteCall = executeCall;
    }

    public void start() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mDisposable = Observable.interval(0, mPeriodTime, TimeUnit.MILLISECONDS)
                .filter(aLong -> {
                    if (mExecuteCondition == null) {
                        return true;
                    } else {
                        return mExecuteCondition.isExecute();
                    }
                })
                .subscribe(aLong -> {
                    mDisposable.dispose();
                    if (mExecuteCall != null) {
                        mExecuteCall.execute();
                    }
                });
    }

    public interface ExecuteCall {
        void execute();
    }

    public interface ExecuteCondition {
        boolean isExecute();
    }
}
