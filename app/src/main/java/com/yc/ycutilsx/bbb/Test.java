package com.yc.ycutilsx.bbb;

/**
 *
 */
public class Test {

    public static void main(String[] args) {
        BronyaZaychik bronyaZaychik = new BronyaZaychik();//板鸭
        YaeSakura yaeSakura = new YaeSakura();//饭团组
        int roundsNumber = 1;
        while (bronyaZaychik.getHealth() > 0 && yaeSakura.getHealth() > 0) {
            if (bronyaZaychik.getSpeed() > yaeSakura.getSpeed()) {
                bronyaZaychik.attackEnemy(roundsNumber, yaeSakura);
                if (yaeSakura.getHealth() <= 0) {
                    break;
                }
                yaeSakura.attackEnemy(roundsNumber, bronyaZaychik);
            } else {
                yaeSakura.attackEnemy(roundsNumber, bronyaZaychik);
                if (bronyaZaychik.getHealth() <= 0) {
                    break;
                }
                bronyaZaychik.attackEnemy(roundsNumber, yaeSakura);
            }
            roundsNumber++;
        }
        if (bronyaZaychik.getHealth() > 0) {
            System.out.println(bronyaZaychik.getName() + "获胜");
        }
        if (yaeSakura.getHealth() > 0) {
            System.out.println(yaeSakura.getName() + "获胜");
        }
    }
}
