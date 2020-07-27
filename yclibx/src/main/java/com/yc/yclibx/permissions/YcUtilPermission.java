package com.yc.yclibx.permissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yc.yclibx.exception.YcException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 *
 */

public class YcUtilPermission {
    /**
     * 日历权限
     */
    public static final String[] PERMISSION_CALENDAR = {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};
    /**
     * 相机权限
     */
    public static final String[] PERMISSION_CAMERA = {Manifest.permission.CAMERA};
    /**
     * 联系人权限
     */
    public static final String[] PERMISSION_CONTACTS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS};
    /**
     * 位置权限
     */
    public static final String[] PERMISSION_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    /**
     * 麦克风权限
     */
    public static final String[] PERMISSION_MICROPHONE = {Manifest.permission.RECORD_AUDIO};
    /**
     * 手机权限
     */
    public static final String[] PERMISSION_PHONE = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
    /**
     * 传感器权限
     */
    public static final String[] PERMISSION_SENSORS = {Manifest.permission.BODY_SENSORS};
    /**
     * 短信权限
     */
    public static final String[] PERMISSION_SMS = {Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS};
    /**
     * 存储卡权限
     */
    public static final String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 蓝牙权限
     */
    public static final String[] PERMISSION_BLT = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private YcUtilPermission() {
    }

    private WeakReference<AppCompatActivity> mActivity;
    private List<String> mRequestPermissions = new ArrayList<>();
    private List<String> mFailPermissions = new ArrayList<>();
    private List<String> mSuccessPermissions = new ArrayList<>();
    private List<String> mNeverAskAgainPermissions = new ArrayList<>();
    private SuccessCall mSuccessCall;
    private FailCall mFailCall;
    private NeverAskAgainCall mNeverAskAgainCall;
    private SuccessCall2 mSuccessCall2;
    private FailCall2 mFailCall2;
    private NeverAskAgainCall2 mNeverAskAgainCall2;

    public static YcUtilPermission newInstance(AppCompatActivity activity) {
        YcUtilPermission ycUtilPermission = new YcUtilPermission();
        ycUtilPermission.mActivity = new WeakReference<AppCompatActivity>(activity);
        return ycUtilPermission;
    }

    public YcUtilPermission addPermissions(String permissions) {
        mRequestPermissions.add(permissions);
        return this;
    }

    public YcUtilPermission addPermissions(String[] permissions) {
        mRequestPermissions.addAll(Arrays.asList(permissions));
        return this;
    }

    public YcUtilPermission setSuccessCall(SuccessCall successCall) {
        mSuccessCall = successCall;
        return this;
    }

    public YcUtilPermission setSuccessCall2(SuccessCall2 successCall2) {
        mSuccessCall2 = successCall2;
        return this;
    }

    public YcUtilPermission setFailCall(FailCall failCall) {
        mFailCall = failCall;
        return this;
    }

    public YcUtilPermission setFailCall2(FailCall2 failCall2) {
        mFailCall2 = failCall2;
        return this;
    }

    public YcUtilPermission setNeverAskAgainCall(NeverAskAgainCall neverAskAgainCall) {
        mNeverAskAgainCall = neverAskAgainCall;
        return this;
    }

    public YcUtilPermission setNeverAskAgainCall2(NeverAskAgainCall2 neverAskAgainCall2) {
        mNeverAskAgainCall2 = neverAskAgainCall2;
        return this;
    }

    /**
     * 当有一个权限，没有时返回false
     */
    public boolean isHasPermission(String[] permissions) {
        for (String p : permissions) {
            if (!isHasPermission(p)) {
                return false;
            }
        }
        return true;
    }

    public boolean isHasPermission(String permission) {
        return new RxPermissions(mActivity.get()).isGranted(permission);
    }

    @SuppressLint("CheckResult")
    public void start() {
        RxPermissions rxPermissions = new RxPermissions(mActivity.get());
        rxPermissions.requestEach(mRequestPermissions.toArray(new String[0]))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.i("YcUtilPermission", "" + permission.name + "用户已经同意该权限");
                            mSuccessPermissions.add(permission.name);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.i("YcUtilPermission", "" + permission.name + "用户拒绝了该权限，没有选中『不再询问』");
                            mFailPermissions.add(permission.name);
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                            Log.i("YcUtilPermission", "" + permission.name + "用户拒绝了该权限，并且选中『不再询问』");
                            mNeverAskAgainPermissions.add(permission.name);
                        }
                        int failSize = mFailPermissions.size();
                        int successSize = mSuccessPermissions.size();
                        int requestSize = mRequestPermissions.size();
                        int neverAskAgain = mNeverAskAgainPermissions.size();
                        if (neverAskAgain + failSize + successSize >= requestSize) {
                            if (neverAskAgain > 0) {
                                if (mNeverAskAgainCall != null) {
                                    mNeverAskAgainCall.onCall();
                                }
                                if (mNeverAskAgainCall2 != null) {
                                    mNeverAskAgainCall2.onCall(mNeverAskAgainPermissions);
                                }
                            } else if (failSize > 0) {
                                if (mFailCall != null) {
                                    mFailCall.onCall();
                                }
                                if (mFailCall2 != null) {
                                    mFailCall2.onCall(mFailPermissions);
                                }
                            } else {
                                if (mSuccessCall != null) {
                                    mSuccessCall.onCall();
                                }
                                if (mSuccessCall2 != null) {
                                    mSuccessCall2.onCall(mSuccessPermissions);
                                }
                            }
                        }
                    }
                });
    }

    public interface SuccessCall {
        void onCall();
    }

    public interface SuccessCall2 {
        void onCall(List<String> permissionSuccess);
    }

    public interface FailCall2 {
        void onCall(List<String> permissionFail);
    }

    public interface FailCall {
        void onCall();
    }

    public interface NeverAskAgainCall2 {
        void onCall(List<String> permissionNeverAskAgain);
    }

    public interface NeverAskAgainCall {
        void onCall();
    }
}

