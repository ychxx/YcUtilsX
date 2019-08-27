package com.yc.yclibx.toactivity;

import android.app.Activity;
import android.content.Intent;

import com.yc.yclibx.bean.YcForResultBean;

import io.reactivex.Observable;

/**
 *  带返回参数的跳转
 */

public class YcForResult {
    private YcForResultFragment mFragment;
    public YcForResult(Activity activity) {
        mFragment = YcForResultFragment.getFragment(activity);
    }
    public Observable<YcForResultBean> start(Intent intent) {
        return mFragment.put(intent);
    }
}
