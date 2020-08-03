package com.yc.ycutilsx.bbb.role;

import com.yc.yclibx.comment.YcRandom;
import com.yc.ycutilsx.bbb.Attributes;
import com.yc.ycutilsx.bbb.Damage;
import com.yc.ycutilsx.bbb.Skill;

/**
 * 布洛妮娅 扎伊切克
 */
public class Seele extends Attributes {
    private boolean isBlack = false;//默认白希儿

    public Seele() {
        mName = "希儿";
        mHealth = 100;
        mAttack = 23;
        mDefense = 13;
        mSpeed = 26;
    }

    public void attackEnemy(int roundsNumber, Attributes enemyAttributes) {
        isBlack = !isBlack;
        if (isBlack) {
            mAttack += 10;
            mDefense -= 5;
            System.out.println("第" + roundsNumber + "回合," + mName + "切换成黑希儿攻击力+10，防御-5");
        } else {
            mAttack -= 10;
            mDefense += 5;
            int restore = YcRandom.getInt(1, 15);
            restoreHealth(restore);
            System.out.println("第" + roundsNumber + "回合," + mName + "切换成白希儿攻击力-10，防御+5,恢复" + restore + "血量,"
                    + mName + "还剩" + mHealth + "血量");
        }
        int loseEnemyHealth = getAttack() - enemyAttributes.getDefense();
        enemyAttributes.loseHealth(loseEnemyHealth);
        print(roundsNumber, loseEnemyHealth, "平A造成", this, enemyAttributes);
    }

    public void print(int roundsNumber, int loseEnemyHealth, String attackName, Attributes me, Attributes enemy) {
        System.out.println("第" + roundsNumber + "回合," + me.getName() + "发动" + attackName + loseEnemyHealth + ","
                + enemy.getName() + "还剩" + enemy.getHealth() + "血量");
    }
}
