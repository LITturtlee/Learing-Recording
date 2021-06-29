package com.myweb.threadsafe;

public class AccountThread extends Thread{
    public Account act;

    public AccountThread() {
    }

    public AccountThread(Account act, String name){
        this.act = act;
        setName(name);
    }

    /**
     * 执行取款
     */
    @Override
    public void run() {
        double money = 5000;
        act.withdraw(money);
        System.out.println("线程："+Thread.currentThread().getName()+",账户"+act.getActno()+"取款成功，余额: "+act.getBalance());
    }
}
