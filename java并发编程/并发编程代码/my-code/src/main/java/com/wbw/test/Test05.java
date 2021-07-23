package com.wbw.test;

import lombok.extern.slf4j.Slf4j;

import static com.wbw.util.Sleeper.sleep;

/**
 * @Author: wbw
 * @Date: 2021/7/20 16:14
 */
@Slf4j
public class Test05 {
    static int r = 0;
    public static void main(String[] args) throws InterruptedException {
        test1();
    }
    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(5);
            log.debug("结束");
            r = 10;
        });
        t1.start();
        t1.join();
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
}
