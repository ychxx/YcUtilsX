package com.yc.ycutilsx.fragment;

import android.widget.TextView;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.yc.yclibrary.base.YcLazyFragment;
import com.yc.yclibx.comment.YcLog;
import com.yc.ycutilsx.R;

/**
 *
 */
public class TestFragment2 extends YcLazyFragment {
    private String title = "";

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        YcLog.e("TestFragment2 onAttach:" + title + " id:"+ getId());
    }

    @Override
    public void onDestroy() {
        YcLog.e("TestFragment2 onDestroy:" + title + " id:"+ getId());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        YcLog.e("TestFragment2 onDetach:" + title + " id:"+ getId());
        super.onDetach();
    }

    public static TestFragment2 newInstance(String msg) {
        TestFragment2 fragment = new TestFragment2();
        fragment.title = msg;
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.test_fragment;
    }

    TextView textView;

    @Override
    public void initView() {
        textView = getView().findViewById(R.id.testFragmentTv);
        textView.setText(title);
    }

    public void setTitle(String title) {
        YcLog.e("TestFragment2 setTitle " +" id:"+ getId());
        textView.setText(title);
    }
}
