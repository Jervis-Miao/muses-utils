/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils;

/**
 * @author miaoqiang
 * @date 2020/8/12.
 */
public class TestString {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
    }

    public static void test1() {
        String a = "1";
        String b = new String("1");
        System.out.println(a == b);
    }

    public static void test2() {
        String a = new String("2");
        String s = "1";
    }

    public static void test3() {
        String a = new String("3");
        String b = new String("33");

        String c = a + b;
        String d = "333";

        System.out.println(c == d);
    }

    public static void test4() {
        String a = "4";
        String b = "44";

        String c = a + b;
        String d = "444";

        System.out.println(c == d);
    }

    public static void test5() {
        final String a = "5";
        final String b = "55";

        String c = a + b;
        String d = "555";

        System.out.println(c == d);
    }

    public static void test6() {
        final String a = new String("6");
        final String b = new String("66");

        String c = a + b;
        String d = "666";

        System.out.println(c == d);
    }

}
