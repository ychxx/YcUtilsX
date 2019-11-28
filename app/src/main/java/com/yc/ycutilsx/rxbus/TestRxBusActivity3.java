package com.yc.ycutilsx.rxbus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.comment.YcRxBus;
import com.yc.ycutilsx.R;

import io.reactivex.disposables.Disposable;

/**
 *
 */
public class TestRxBusActivity3 extends AppCompatActivity {
    private TextView dataTv;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_bus_activity2);
        dataTv = findViewById(R.id.rxBusActivity2DataTv);
        findViewById(R.id.rxBusActivity2RegisterTv).setOnClickListener(v -> {
            if (disposable != null) {
                disposable.dispose();
            }
            disposable = YcRxBus.newInstance().registerStickyUnsafe(TestRxRusBean.class).subscribe(testRxRusBean -> {
                YcLog.e("getActivity() = " + getActivity().toString());
                YcLog.e("Thread.currentThread().getId() = " + Thread.currentThread().getId());
                YcLog.e("getMainLooper().getThread().getId() = " + getMainLooper().getThread().getId());
                YcLog.e("getTaskId() = " + getTaskId());
                if (getActivity() != null) {
                    YcLog.e("非空1");
                    dataTv.setText(testRxRusBean.getDate());
                } else {
                    dataTv.setText(testRxRusBean.getDate());
                    YcLog.e("为空1");
                }
            });
        });
        findViewById(R.id.rxBusActivity2SendDataTv).setOnClickListener(v -> {
            TestRxRusBean testRxRusBean = new TestRxRusBean();
            testRxRusBean.setDate("Activity数据");
            YcRxBus.newInstance().post(testRxRusBean);
            finish();
        });
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        if (disposable != null)
            disposable.dispose();
        super.onDestroy();
    }
}
