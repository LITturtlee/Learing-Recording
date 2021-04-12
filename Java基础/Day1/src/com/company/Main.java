package com.company;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.Console;
import java.util.Arrays;
import java.util.Scanner;
//import java.lang.String;

public class Main {
    public static final int ABC = 99;
    public static final double PI = 3.14;
    final int y = 10;
    public static String str = "helo";
    public static void main(String[] args) {
       String myStr = new String("hello world");
       String number1 = "5412";
       String number2 = "123.22";
       double num3 = 12.6;
       int num1 = Integer.parseInt(number1);
       System.out.println(num1);
       int num2 = Integer.valueOf(number1).intValue();
       System.out.println(num2);
       System.out.println("123124"+"asdasd");
       System.out.println(number1+"asd");
       String number3 = String.valueOf(num3);

//        Integer.valueOf(number2).floatValue();
    }
    public static void show(int ... a){

        System.out.println(a.length);
        System.out.println(Arrays.toString(a));
    }
}