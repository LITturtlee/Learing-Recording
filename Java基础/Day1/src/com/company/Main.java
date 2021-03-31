package com.company;

import java.io.Console;
import java.util.Arrays;

public class Main {
    static int i = 99;
    public static void main(String[] args) {
        int[] age = {1,2,3,4,5};
        int a;
        a = 10;
        i = 9;
        System.out.println(a);
        System.out.println(i);
        show(age);
    }
    public static void show(int ... a){
        System.out.println(Arrays.toString(a));
    }
}
