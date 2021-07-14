package com.wbw.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 本地机是多核的，下面创建的线程分别在两个线程序列里，所以有可能下面执行是并行执行的。
 * @Author: wbw
 * @Date: 2021/7/6 21:56
 */
@Slf4j
public class Test1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        log.debug("MainThread running");
        //方式一
        Thread t = new Thread("t1"){
            @Override
            public void run() {
                log.debug("Thread running");
            }
        };
        t.start();
        //方式二
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.debug("Thread running");
            }
        };
        Thread t2 = new Thread(runnable,"t2");
        t2.start();
        //方式三
        Runnable runnable1 = () -> {
            log.debug("Thread running");
        };
        Thread t3 = new Thread(runnable1,"t3");
        t3.start();
        //方式四 FutureTask + Callable
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running");
//                Thread.sleep(1000);
                return 100;
            }
        });
        Thread t4 = new Thread(task,"t4");
        t4.start();
        Integer num = task.get();
        log.debug("{}",num);
    }
}
