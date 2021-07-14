package com.wbw.test;

import com.wbw.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wbw
 * @Date: 2021/7/9 16:41
 */
@Slf4j
public class Practice01 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            Sleeper.sleep(1);
            log.debug("烧开水");
            Sleeper.sleep(5);
        },"老王");

        Thread t2 = new Thread(()->{
            log.debug("洗茶壶");
            Sleeper.sleep(1);
            log.debug("洗茶杯");
            Sleeper.sleep(2);
            log.debug("拿茶叶");
            Sleeper.sleep(1);
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("泡茶");
            },"小王");
        t1.start();
        t2.start();
    }
}
