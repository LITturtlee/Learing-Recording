package com.wbw.redis_springboot.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: wbw
 * @Date: 2021/6/22 16:10
 */
@RestController
@RequestMapping("/redisTest")
public class RedisTestController {

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping
    public String testRedis(){
        //设置值到redis
        redisTemplate.opsForValue().set("springboot","hello");
        String springboot = (String) redisTemplate.opsForValue().get("springboot");
        return springboot;
    }
}
