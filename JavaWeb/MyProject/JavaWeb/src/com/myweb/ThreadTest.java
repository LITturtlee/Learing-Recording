package com.myweb;

public class ThreadTest {
    public static void main(String[] args) {
        MyThread mt = new MyThread();
        mt.start();
//        mt.run();
//        for (int i=0;i<1000;i++){
//            System.out.println("主线程->"+i);
//        }
        System.out.println(Thread.currentThread().getName()+Thread.currentThread().getPriority());
    }
}
class  MyThread extends Thread{
    @Override
    public void run() {
//        for(int i=0;i<1000;i++){
//            System.out.println("线程->"+i);
//        }
        System.out.println(Thread.currentThread().getName()+Thread.currentThread().getPriority());
    }
}
