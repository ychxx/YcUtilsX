package com.yc.ycutilsx.bbb;

/**
 * 基础属性
 */
public abstract class Attributes {
    protected int mHealth = 100;//生命值
    protected int mAttack;//攻击
    protected int mDefense;//防御
    protected int mSpeed;//速度
    protected String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    /**
     * 获取当前
     */
    public int getHealth() {
        return mHealth;
    }

    /**
     * 扣除生命值
     */
    public void loseHealth(int lose) {
        mHealth -= lose;
        if (mHealth < 0) {
            mHealth = 0;
        }
    }

    /**
     * 恢复生命值
     *
     * @param restore
     */
    public void restoreHealth(int restore) {
        mHealth += restore;
        if (mHealth > 100) {
            mHealth = 100;
        }
    }


    public int getAttack() {
        return mAttack;
    }

    public void setAttack(int mAttack) {
        this.mAttack = mAttack;
    }

    public int getDefense() {
        return mDefense;
    }

    public void setDefense(int mDefense) {
        this.mDefense = mDefense;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int mSpeed) {
        this.mSpeed = mSpeed;
    }
}
