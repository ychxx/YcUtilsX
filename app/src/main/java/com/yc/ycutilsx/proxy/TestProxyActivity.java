package com.yc.ycutilsx.proxy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.lang.reflect.Proxy;

/**
 *
 */
public class TestProxyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Button button = new Button(this);
        button.setText("点击");
        button.setOnClickListener(v -> {
            ITestProxy car = new TestProxy();
            ITestProxy testProxy = (ITestProxy) Proxy.newProxyInstance(car.getClass().getClassLoader(), TestProxy.class.getInterfaces(), new TestProxyInvocationHandler(car));
            testProxy.test("11111");
//            testProxy.test2("22222222");
        });
        linearLayout.addView(button);
        setContentView(linearLayout);
    }
}
