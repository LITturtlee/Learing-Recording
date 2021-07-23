package com.wbw.test;

import lombok.extern.slf4j.Slf4j;
import com.wbw.util.Sleeper;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: wbw
 * @Date: 2021/7/20 19:37
 */
@Slf4j
public class Test06 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            Sleeper.sleep(1);
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        },"t1");
        t1.start();
        Sleeper.sleep(2);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
