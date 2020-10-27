package com.yc.yclibx.release;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 特殊状态
 */
@IntDef({SpecialState.NET_ERROR, SpecialState.DATA_ERROR, SpecialState.DATA_EMPTY, SpecialState.UPDATE_PASSWORD_SUCCESS
        , SpecialState.CUSTOM_1, SpecialState.CUSTOM_2, SpecialState.CUSTOM_3})
@Retention(RetentionPolicy.SOURCE)
public @interface SpecialState {
    int NET_ERROR = 0;//网络异常
    int DATA_ERROR = 1;//数据错误
    int DATA_EMPTY = 2;//数据为空
    int UPDATE_PASSWORD_SUCCESS = 3;//修改密码成功
    int CUSTOM_1 = 4;//自定义1
    int CUSTOM_2 = 5;//自定义2
    int CUSTOM_3 = 6;//自定义3
}
