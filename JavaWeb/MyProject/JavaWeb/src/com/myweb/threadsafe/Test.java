package com.myweb.threadsafe;

public class Test {
    public static void main(String[] args) {
        Account act = new Account("act-001",10000);
        Account act2 = new Account("act-001",10000);
        AccountThread t1 = new AccountThread(act,"t1");
        AccountThread t2 = new AccountThread(act2,"t2");
        System.out.println(t1.act==t2.act);
        t1.start();
        t2.start();

    }
}
