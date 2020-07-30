package com.yc.ycutilsx.bbb;

import com.yc.yclibx.comment.YcRandom;

/**
 * 布洛妮娅 扎伊切克
 */
public class BronyaZaychik extends Attributes {
    private Skill mSkill1;//技能1
    private Skill mSkill2;//技能2

    public BronyaZaychik() {
        mName = "板鸭";
        mHealth = 100;
        mAttack = 21;
        mDefense = 10;
        mSpeed = 20;
        mSkill1 = new Skill() {//天使析构，攻击后25%的概率，造成12点伤害
            @Override
            public int restoreHealth() {
                return 0;
            }

            @Override
            public Damage damageCaused() {
                int ramdom = YcRandom.getInt(0, 100);//生成一个0到100的随机数
                if (ramdom <= 25) {
                    return new Damage(Damage.DamageType.SKILL, 12);
                } else {
                    return new Damage(Damage.DamageType.SKILL, 0);
                }
            }
        };
        mSkill2 = new Skill() {//摩托拜客哒!，每3回合，造成1~100的元素伤害
            @Override
            public int restoreHealth() {
                return 0;
            }

            @Override
            public Damage damageCaused() {
                int ramdom = YcRandom.getInt(1, 100);//生成一个1到100的随机数
                return new Damage(Damage.DamageType.SKILL, ramdom);
            }
        };
    }

    public void attackEnemy(int roundsNumber, Attributes enemyAttributes) {
        int loseEnemyHealthSkill1 = mSkill1.damageCaused().getValue();
        if (loseEnemyHealthSkill1 > 0) {
            enemyAttributes.loseHealth(loseEnemyHealthSkill1);
            print(roundsNumber, loseEnemyHealthSkill1, "天使析构造成", this, enemyAttributes);
        }
        if (roundsNumber % 3 == 0) {
            int loseEnemyHealthSkill2 = mSkill2.damageCaused().getValue();
            enemyAttributes.loseHealth(loseEnemyHealthSkill2);
            print(roundsNumber, loseEnemyHealthSkill2, "摩托拜客哒!造成", this, enemyAttributes);
        } else {
            int loseEnemyHealth = getAttack() - enemyAttributes.getDefense();
            enemyAttributes.loseHealth(loseEnemyHealth);
            print(roundsNumber, loseEnemyHealth, "平A造成", this, enemyAttributes);
        }
    }

    public void print(int roundsNumber, int loseEnemyHealth, String attackName, Attributes me, Attributes enemy) {
        System.out.println("第" + roundsNumber + "回合," + me.getName() + "发动" + attackName + loseEnemyHealth + ","
                + enemy.getName() + "还剩" + enemy.getHealth() + "血量");
    }
}
