package com.myweb.threadsafe;

public class Account {
    private String actno;
    private double balance;

    public Account(String actno, double balance) {
        this.actno = actno;
        this.balance = balance;
    }

    public void setActno(String actno) {
        this.actno = actno;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getActno() {
        return actno;
    }

    public double getBalance() {
        return balance;
    }

    //取款
    public void withdraw(double money){
//        double before = getBalance();
//        double after = before - money;
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        setBalance(after);
        synchronized (this){
            double before = getBalance();
                double after = before - money;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setBalance(after);
        }
    }
}
