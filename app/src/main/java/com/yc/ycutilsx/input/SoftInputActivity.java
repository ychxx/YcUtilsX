package com.yc.ycutilsx.input;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.yclibx.comment.YcLog;
import com.yc.ycutilsx.R;
import com.yc.ycutilsx.input.InputMethodHolder;
import com.yc.ycutilsx.input.OnInputMethodListener;

/**
 * 输入法相关测试
 */
public class SoftInputActivity extends YcAppCompatActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.soft_input_main;
    }

    ScrollView nestedScrollView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        nestedScrollView = findViewById(R.id.softNSV);
        EditText edt = findViewById(R.id.softEdt);
        nestedScrollView.fullScroll(View.FOCUS_DOWN);
        findViewById(R.id.softBtn).setOnClickListener(v -> {
            nestedScrollView.post(new Runnable() {
                @Override
                public void run() {
                    nestedScrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        });
        //输入法的显示和隐藏
        OnInputMethodListener onInputMethodListener = new OnInputMethodListener() {
            @Override
            public void onShow(boolean result) {
                YcLog.e("显示输入框");
                nestedScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nestedScrollView.fullScroll(View.FOCUS_DOWN);
                    }
                }, 100);
            }

            @Override
            public void onHide(boolean result) {

            }
        };
        InputMethodHolder.registerListener(onInputMethodListener);
    }
}
