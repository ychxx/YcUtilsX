package com.yc.ycutilsx.proxy;

import com.yc.yclibx.comment.YcLog;

/**
 *
 */
public class TestProxy implements ITestProxy {

    @Override
    public void test(String msg) {
        YcLog.e("TestProxy1:" + msg);
    }

    @Override
    public void test2(String msg) {
        YcLog.e("TestProxy2:" + msg);
    }
}
