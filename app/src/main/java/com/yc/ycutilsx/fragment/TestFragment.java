package com.yc.ycutilsx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yc.yclibrary.base.YcLazyFragment;
import com.yc.yclibx.comment.YcLog;
import com.yc.ycutilsx.R;

/**
 *
 */
public class TestFragment extends YcLazyFragment {
    private String title = "";

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        YcLog.e("TestFragment1 onAttach:" + title + " id:" + getId());
    }

    @Override
    public void onDestroy() {
        YcLog.e("TestFragment1 onDestroy:" + title + " id:" + getId());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        YcLog.e("TestFragment1 onDetach:" + title + " id:" + getId());
        super.onDetach();
    }

    public static TestFragment newInstance(String msg) {
        TestFragment fragment = new TestFragment();
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
        YcLog.e("TestFragment1 setTitle" + " id:" + getId());
        textView.setText(title);
    }
}
