package com.wbw.demo01;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * jedis.close()，有点问题不知道为啥关不了
 * @Author: wbw
 * @Date: 2021/6/22 11:14
 */
public class PhoneCode {
    public static void main(String[] args) {
//        System.out.println(getCode());
        sendCode("18280350781");
//        输入code
//        String code = "805683";
//        verifyCode("18280350781",code);

    }

    /**
     * 验证码校验
     */
    public static void verifyCode(String phone,String code){
        Jedis jedis = new Jedis("47.99.82.240",6379);
        jedis.auth("970216");
        String redisCode = jedis.get("VerifyCode"+phone+":code");
        if(redisCode==null) {
            System.out.println("验证码过期");
        }
        if(code.equals(redisCode)){
            System.out.println("yes");
        }else {
            System.out.println("no");
        }
        jedis.close();
    }

    /**
     * 2 设置功能每天发送三次，验证码存在redis中，设置过期时间
     * @param phone
     * @param code
     */
    public static void sendCode(String phone){
        //连接redis
        Jedis jedis = new Jedis("47.99.82.240",6379);
        jedis.auth("970216");

        //设置key
        //计数key
        String countKey = "VerifyCode" + phone + ":count";
        //验证码key
        String codeKey = "VerifyCode" + phone + ":code";

        //每天只能发送3次
        String count = jedis.get(countKey);
        if(count==null){
            jedis.setex(countKey,24*60*60,"1");
        }else if(Integer.parseInt(count)<=2){
            jedis.incr(countKey);
        }else{
            System.out.println("今天已经发送3次了");
            jedis.close();
            jedis.set("k1","v1");
        }

        //发送的验码并存储
        String code = getCode();
        //发送
        System.out.println("发送代码："+code);
        //存储
        jedis.set("k3","v3");
        jedis.close();
        jedis.set("k2","v2");
        jedis.set(codeKey,code);
        jedis.expire(codeKey,60);
        jedis.close();

    }


    //生成6位验证码
    public static String getCode(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<6;i++){
            int rand = random.nextInt(10);
            sb.append(rand);
        }
        return sb.toString();
    }
}
