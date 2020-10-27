package com.yc.yclibx.comment;

import com.yc.yclibx.YcManage;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class YcLoop2 implements YcOnDestroy {
    private ExecuteCondition mExecuteCondition;
    private int mPeriodTime = 200;//间隔时间
    private Disposable mDisposable;
    private FinishCondition mFinishCondition;
    private ExecuteCall mExecuteCall;
    private Scheduler mSchedulerUp = Schedulers.io();//上游
    private Scheduler mSchedulerDown = AndroidSchedulers.mainThread();//下游

    public YcLoop2(YcManage ycManage) {
        ycManage.add(this);
    }

    /**
     * 设置执行判断条件（true时执行，false时继续循环）
     */
    public YcLoop2 setExecuteCondition(ExecuteCondition executeCondition) {
        this.mExecuteCondition = executeCondition;
        return this;
    }

    /**
     * 设置判断结束条件（true或null时结束，false时继续循环）
     */
    public YcLoop2 setFinishCondition(FinishCondition finishCondition) {
        this.mFinishCondition = finishCondition;
        return this;
    }

    /**
     * 设置间隔时间
     */
    public YcLoop2 setPeriodTime(int periodTime) {
        this.mPeriodTime = periodTime;
        return this;
    }

    /**
     * 执行的回调
     */
    public YcLoop2 setExecuteCall(ExecuteCall executeCall) {
        this.mExecuteCall = executeCall;
        return this;
    }


    public YcLoop2 setSchedulerUp(Scheduler scheduler) {
        mSchedulerUp = scheduler;
        return this;
    }

    public YcLoop2 setSchedulerDown(Scheduler scheduler) {
        mSchedulerDown = scheduler;
        return this;
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
                .subscribeOn(mSchedulerUp)
                .observeOn(mSchedulerDown)//下游
                .subscribe(aLong -> {
                    if (mFinishCondition == null || mFinishCondition.isFinish()) {
                        mDisposable.dispose();
                    }
                    if (mExecuteCall != null) {
                        mExecuteCall.execute();
                    }
                });
    }


    @Override
    public void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public interface ExecuteCall {
        void execute();
    }

    public interface ExecuteCondition {
        boolean isExecute();
    }

    public interface FinishCondition {
        boolean isFinish();
    }
}
