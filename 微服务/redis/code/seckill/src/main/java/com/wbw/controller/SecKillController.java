package com.wbw.controller;

import com.wbw.util.JedisPoolUtil;
import com.wbw.util.SecKillRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Random;

/**
 * @Author: wbw
 * @Date: 2021/6/23 16:07
 */
@RestController
public class SecKillController {


//    JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();

    @PostMapping("/doseckill")
    public String doSecKill(@RequestParam String prodid) throws IOException {
        System.out.println(prodid);
        String userid = new Random().nextInt(50000) + "";
        boolean isSuccess = SecKillRedis.doSecKill(userid,prodid);
        return String.valueOf(isSuccess);
    }
}
