package com.yc.ycutilsx.bbb;

import com.yc.ycutilsx.bbb.role.BronyaZaychik;
import com.yc.ycutilsx.bbb.role.Seele;
import com.yc.ycutilsx.bbb.role.YaeSakura;

import java.util.List;

/**
 *
 */
public class Test {
    private static int seeleWin = 0;
    private static int BronyaZaychikWin = 0;
    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            BronyaZaychik bronyaZaychik = new BronyaZaychik();//板鸭
            Seele seele = new Seele();
            pk(seele, bronyaZaychik);
        }
        System.out.println("板鸭总胜场数："+BronyaZaychikWin+" 希儿总胜场数："+seeleWin);
    }

    private static void pk(Seele seele, BronyaZaychik bronyaZaychik) {
        int roundsNumber = 1;
        while (bronyaZaychik.getHealth() > 0 && seele.getHealth() > 0) {
            if (bronyaZaychik.getSpeed() > seele.getSpeed()) {
                bronyaZaychik.attackEnemy(roundsNumber, seele);
                if (seele.getHealth() <= 0) {
                    break;
                }
                seele.attackEnemy(roundsNumber, bronyaZaychik);
            } else {
                seele.attackEnemy(roundsNumber, bronyaZaychik);
                if (bronyaZaychik.getHealth() <= 0) {
                    break;
                }
                bronyaZaychik.attackEnemy(roundsNumber, seele);
            }
            roundsNumber++;
        }
        if (bronyaZaychik.getHealth() > 0) {
            seeleWin++;
            System.out.println(bronyaZaychik.getName() + "获胜");
        }
        if (seele.getHealth() > 0) {
            BronyaZaychikWin++;
            System.out.println(seele.getName() + "获胜");
        }
    }
}
