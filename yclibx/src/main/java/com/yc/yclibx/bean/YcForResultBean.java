package com.yc.yclibx.bean;

import android.content.Intent;

/**
 *  返回的数据
 */

public class YcForResultBean {
    private int mResultCode;
    private Intent mData;
    private boolean mIsSuccess;
    public YcForResultBean(int resultCode, Intent data) {
        this.mResultCode = resultCode;
        this.mData = data;
    }
    public YcForResultBean(int resultCode, Intent data, boolean isSuccess) {
        this.mResultCode = resultCode;
        this.mData = data;
    }
    public int getResultCode() {
        return mResultCode;
    }

    public void setResultCode(int resultCode) {
        this.mResultCode = resultCode;
    }

    public Intent getData() {
        return mData;
    }

    public void setData(Intent mData) {
        this.mData = mData;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.mIsSuccess = isSuccess;
    }
}
