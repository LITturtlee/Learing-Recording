package com.wbw.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wbw
 * @Date: 2021/7/10 22:26
 */
public class Test03 {
    public static void main(String[] args) {
        ThreadSafeSubClass test = new ThreadSafeSubClass();
        test.method1(2000);

    }
}

class ThreadSafe {
    public final void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            //下面又开启一个线程，出现了多线程操作同一变量的情况
            method3(list);
        }
    }
    public void method2(ArrayList<String> list) {
        list.add("1");
    }
    public void method3(ArrayList<String> list) {
        list.remove(0);
    }
}
class ThreadSafeSubClass extends ThreadSafe{
    @Override
    public void method3(ArrayList<String> list) {
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}