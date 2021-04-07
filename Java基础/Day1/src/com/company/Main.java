package com.company;

import java.io.Console;
import java.util.Arrays;

public class Main {
    public static final int ABC = 99;
    public static final double PI = 3.14;
    final int y = 10;  
    public static void main(String[] args) {
        int[] age = {1,2,3,4,5};
        int a;
        a = 10;
        System.out.println(a);
        System.out.println(ABC);
        show(age);
    }
    public static void show(int ... a){
        System.out.println(a.length);
        System.out.println(Arrays.toString(a));
    }
}