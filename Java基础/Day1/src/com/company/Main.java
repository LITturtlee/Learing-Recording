package com.company;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.Console;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;
//import java.lang.String;

public class Main {
    public static final int ABC = 99;
    public static final double PI = 3.14;
    final int y = 10;
    public static String str = "helo";
    public static void main(String[] args) {
        String str1 = "tepd";
        String str2 = "tepD";
        str1.replace("t","12412");
        System.out.println(str1.replace("t","g"));
        System.out.println(str1.replace("te","ab"));
//        String str3 = str1;
        String str3 = new String(str1);
        System.out.println(str1==str3);
        System.out.println(str1.compareTo(str2));
        System.out.println(str1);
        String str4 = "ab,cd,ef";
        String[] strs = str4.split(",");
        for(int i=0;i<strs.length;i++){
            System.out.println(strs[i]);
        }
//        boolean v = str1.equals(str2);
        boolean v = str1.equalsIgnoreCase(str2);
        System.out.println(v);
        str1 = "new str";
        System.out.println(str1);
        String s1 = "abc";
        String s3 = "bbb";
        String s2 = "abc";
        System.out.println(s1==s2);
        StringBuffer strb = new StringBuffer("test");
        System.out.println(strb);
        System.out.println(strb.capacity());
        strb.append("  yes"+"   helo");
        System.out.println(strb);
        System.out.println(strb.capacity());
        strb.setCharAt(0,'F');
        strb.reverse();
        System.out.println(strb);
        strb.replace(0,5,"ff");   //i:start  i1:end   replace(i:i1)to s
        System.out.println(strb);

    }
    public static void func(int temp){
        temp = 10;
        System.out.println(temp);
    }
    public static void show(int ... a){

        System.out.println(a.length);
        System.out.println(Arrays.toString(a));
    }
}