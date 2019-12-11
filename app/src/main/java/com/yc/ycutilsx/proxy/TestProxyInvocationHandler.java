package com.yc.ycutilsx.proxy;

import com.yc.yclibx.comment.YcLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public class TestProxyInvocationHandler implements InvocationHandler {
    private final ITestProxy testProxy;

    public TestProxyInvocationHandler(ITestProxy testProxy) {
        this.testProxy = testProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        YcLog.e("---------before-------");
        Object invoke = method.invoke(testProxy, args);
        YcLog.e("---------after-------");
        return invoke;
    }
}
