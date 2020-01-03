package com.yc.ycutilsx.kotlin;

import android.widget.PopupWindow;

import com.yc.yclibx.file.YcImgUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class TestJavaToKotlin {
    static {
        System.out.println("static");
    }

    private int mParam;
    private List<String> listTest = new ArrayList<String>();
    private List<String> listTest2 = Arrays.asList("33", "22");
    int a;

    private class TestConstructor {
        TestConstructor(String msg) {
            System.out.println(msg);
        }

        TestConstructor() {
            this("asda");
            System.out.println("asdasd");
        }
    }

    public TestJavaToKotlin(int param) {

        mParam = param;
        listTest.add("123");
        listTest.remove(1);
        listTest2.add("asd");
        int b = a > 1 ? a : 233;
        int c = b += 2;
    }

    List<String> list = Arrays.asList("asd", "asdasdas");

    private void test() {
        System.out.println(mParam + "");
        List<TestJavaToKotlin> t = testFun();
    }

    public static void main(String[] args) {
//        String a = "1";
//        KotlinOrder kotlinOrder = new KotlinOrder();
//        kotlinOrder.testFun();
    }

    public <T> List<T> testFun() {
        T t = null;
        ArrayList<T> tlist = (ArrayList<T>) Arrays.asList(t);
        return tlist;
    }

    interface Test {
        default void test() {
            System.out.println("asd");
        }
    }
}
