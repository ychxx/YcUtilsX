package com.yc.ycutilsx.bbb.role;

import com.yc.yclibx.comment.YcRandom;
import com.yc.ycutilsx.bbb.Attributes;
import com.yc.ycutilsx.bbb.Damage;
import com.yc.ycutilsx.bbb.Skill;

/**
 * 樱莲组
 */
public class YaeSakura extends Attributes {
    private Skill mSkill1;//技能1
    private Skill mSkill2;//技能2

    public YaeSakura() {
        mName = "樱莲组";
        mHealth = 100;
        mAttack = 20;
        mDefense = 9;
        mSpeed = 18;
        mSkill1 = new Skill() {//每个回合，攻击前30%概率恢复25血
            @Override
            public int restoreHealth() {
                int ramdom = YcRandom.getInt(0, 100);//生成一个0到100的随机数
                if (ramdom <= 30) {
                    return 25;
                } else {
                    return 0;
                }
            }

            @Override
            public Damage damageCaused() {
                return new Damage(Damage.DamageType.PHYSICS, 0);
            }
        };
        mSkill2 = new Skill() {//每2回合，造成25的元素伤害
            @Override
            public int restoreHealth() {
                return 0;
            }

            @Override
            public Damage damageCaused() {
                return new Damage(Damage.DamageType.ELEMENT, 25);
            }
        };
    }

    public void attackEnemy(int roundsNumber, Attributes enemyAttributes) {
        int restoreHealth = mSkill1.restoreHealth();
        if (restoreHealth > 0) {
            restoreHealth(restoreHealth);
            print(roundsNumber, restoreHealth, "八重樱饭团恢复", this, this);
        }
        if (roundsNumber % 2 == 0) {
            int loseEnemyHealth = mSkill2.damageCaused().getValue();
            enemyAttributes.loseHealth(loseEnemyHealth);
            print(roundsNumber, loseEnemyHealth, "卡莲的饭团造成", this, enemyAttributes);
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
