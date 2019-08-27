package com.yc.yclibx.toactivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import com.yc.yclibx.bean.YcForResultBean;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 *
 */

public class YcForResultFragment extends Fragment {
    private static final String TAG_FRAGMENT = "YcForResultFragment";//Fragment的标示
    private SparseArray<PublishSubject<YcForResultBean>> mSubjects = new SparseArray<>();

    public YcForResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//避免配置变换时（例如屏幕旋转）Fragment被销毁
    }

    public static YcForResultFragment getFragment(Activity activity) {
        YcForResultFragment fragment = (YcForResultFragment) activity.getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null) {
            fragment = new YcForResultFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG_FRAGMENT)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public Observable<YcForResultBean> put(final Intent intent) {
        PublishSubject<YcForResultBean> subject = PublishSubject.create();
        return subject.doOnSubscribe(disposable -> {//在onNext()前处理
            mSubjects.put(subject.hashCode(), subject);
            startActivityForResult(intent, subject.hashCode());
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        PublishSubject<YcForResultBean> subject = mSubjects.remove(requestCode);
        PublishSubject<YcForResultBean> subject = mSubjects.get(requestCode);
        if (subject != null) {
            subject.onNext(new YcForResultBean(resultCode, data));
            subject.onComplete();
        }
        mSubjects.remove(requestCode);
    }
}
