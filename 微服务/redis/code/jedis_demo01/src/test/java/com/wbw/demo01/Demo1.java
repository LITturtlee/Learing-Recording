package com.wbw.demo01;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @Author: wbw
 * @Date: 2021/6/21 22:05
 */
public class Demo1 {

    @Test
    public void demo1(){
        Jedis jedis = new Jedis("47.99.82.240",6379);
        jedis.auth("970216");

        String s = jedis.ping();
        System.out.println(s);
    }

    @Test
    public void demo2(){
        Jedis jedis = new Jedis("47.99.82.240",6379);
        jedis.auth("970216");

        jedis.set("name","lucy");

        String name = jedis.get("name");
        System.out.println(name);

        jedis.mset("k1","v1","k2","v2");
        List<String> mget = jedis.mget("k1","k2");
        System.out.println(mget);

        Set<String> keys = jedis.keys("*");
        for(String key:keys){
            System.out.println(key);
        }

    }
}
