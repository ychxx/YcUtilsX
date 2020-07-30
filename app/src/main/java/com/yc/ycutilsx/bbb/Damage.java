package com.yc.ycutilsx.bbb;

import androidx.annotation.IntDef;

import com.yc.yclibx.constant.YcActionTypeEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 伤害
 */
public class Damage {
    @IntDef({DamageType.PHYSICS, DamageType.ELEMENT, DamageType.SKILL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DamageType {
        int PHYSICS = 0; //物理
        int ELEMENT = 1;//元素
        int SKILL = 2;//技能伤害
    }

    @DamageType
    private int type;//0代表物理伤害，1代表元素
    private int value;//伤害值

    public Damage(@DamageType int type, int value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(@DamageType int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
