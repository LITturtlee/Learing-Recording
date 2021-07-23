package com.wbw.test;

import com.wbw.util.Downloader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: wbw
 * @Date: 2021/7/21 22:02
 */
@Slf4j
public class Test08 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
// 4 个生产者线程, 下载任务
        for (int i = 0; i < 4; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    log.debug("download...");
                    List<String> response = Downloader.download();
                    log.debug("try put message({})", id);
                    messageQueue.put(new Message(id, response));
                    log.debug("complete putting");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "生产者" + i).start();
        }
// 1 个消费者线程, 处理结果
        new Thread(() -> {
            while (true) {
                Message message = messageQueue.take();
                List<String> response = (List<String>) message.getMessage();
                log.debug("take message({}): [{}] lines", message.getId(), response.size());
            }
        }, "消费者").start();
                }
    }

class Message {
    private int id;
    private Object message;
    public Message(int id, Object message) {
        this.id = id;
        this.message = message;
    }
    public int getId() {
        return id;
    }
    public Object getMessage() {
        return message;
    }
}
@Slf4j
class MessageQueue {
    private LinkedList<Message> queue;
    private int capacity;
    public MessageQueue(int capacity) {
        this.capacity = capacity;
        queue = new LinkedList<>();
    }
    public Message take() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                log.debug("没货了, wait");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = queue.removeFirst();
            queue.notifyAll();
            return message;
        }
    }
    public void put(Message message) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                log.debug("库存已达上限, wait");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(message);
            queue.notifyAll();
        }
    }
}