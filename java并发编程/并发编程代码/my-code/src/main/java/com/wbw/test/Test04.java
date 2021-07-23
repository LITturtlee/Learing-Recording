package com.wbw.test;

import com.wbw.util.Downloader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: wbw
 * @Date: 2021/7/20 14:58
 */
@Slf4j
public class Test04 {

    //线程1等待线程2
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject(1);
        new Thread(()->{
            log.debug("等待结果");
            Object obj = guardedObject.get();
            log.debug("结果的大小{}",((List<String>)obj).size());
        },"t1").start();

        new Thread(()->{
            log.debug("执行下载");
            List<String> download = null;
            try {
                download = Downloader.download();
            } catch (IOException e) {
                e.printStackTrace();
            }
            guardedObject.complete(download);
        },"t2").start();
    }

}


//中间件集合
class Boxes{
    private static Map<Integer,GuardedObject> boxes = new HashMap<>();

    private static int id = 1;

    private static synchronized int generateId(){
        return id++;
    }
    public static GuardedObject createGuardedObject(){
        GuardedObject go = new GuardedObject(generateId());
        boxes.put(go.getId(),go);
        return go;
    }
    public static Set<Integer> getIds(){
        return boxes.keySet();
    }

}

//这玩意就相当于一个消息中间件，上面俩线程一个消费一个生产
class GuardedObject{
    private Object response;

    private int id;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object get(){
        synchronized (this){
            while (response==null){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    //产生结果
    public void complete(Object response){
        synchronized (this){
            this.response=response;
            this.notify();
        }
    }
}
