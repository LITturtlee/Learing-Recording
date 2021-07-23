package com.wbw.test;

import ch.qos.logback.core.joran.conditional.ThenAction;
import com.wbw.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: wbw
 * @Date: 2021/7/20 21:05
 */
@Slf4j
public class Test07 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            log.debug("尝试获取锁");
            try {
                lock.lockInterruptibly();
                try {
                    log.debug("获得锁");
                } finally{
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获得锁");
            }

        },"t1");
        log.debug("主线程先锁");
        lock.lock();
        t1.start();
        Sleeper.sleep(2);
        log.debug("打断t1");
        t1.interrupt();

    }
}
