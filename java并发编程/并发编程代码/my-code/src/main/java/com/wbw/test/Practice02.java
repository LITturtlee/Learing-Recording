package com.wbw.test;

import com.wbw.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: wbw
 * @Date: 2021/7/12 14:49
 */
@Slf4j
public class Practice02 {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1000);
        TicketWindow ticketWindow = new TicketWindow(500);
        List<Thread> list = new ArrayList<>();
        // 用来存储买出去多少张票
        List<Integer> sellCount = new Vector<>();
        for (int i = 0; i < 5000; i++) {
            Thread t = new Thread(() -> {
                Sleeper.sleep(randomAmount(2));
                // 分析这里的竞态条件
                //买票
                int count = ticketWindow.sell(randomAmount(10));
                //统计买票数
                sellCount.add(count);
                latch.countDown();
            });
            //这给list没有被多个线程共用
            list.add(t);
            t.start();
        }
        //用来等所以线程走完
//        list.forEach((t) -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 买出去的票求和
        log.debug("selled count:{}",sellCount.stream().mapToInt(c -> c).sum());
        // 剩余票数
        log.debug("remainder count:{}", ticketWindow.getCount());
    }
    // Random 为线程安全
    static Random random = new Random();
    // 随机 1~5
    public static int randomAmount(int bound) {
        return random.nextInt(bound) + 1;
    }
}
class TicketWindow {
    private int count;
    public TicketWindow(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    //下面是临界区，所以要加上synchronized
    public synchronized int sell(int amount) {
        if (this.count >= amount) {
            this.count -= amount;
            return amount;
        } else {
            return 0;
        }
    }
}
