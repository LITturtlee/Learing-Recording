package com.myweb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    public static void main(String[] args) {
        Timer t = new Timer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstTime = null;
        try {
            firstTime = sdf.parse("2021-05-05 23:08:20");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //间隔时间又是毫秒
        t.schedule(new LogTimerTask(),firstTime,1000*2);

    }
}
class LogTimerTask extends TimerTask{

    @Override
    public void run() {
        Date nowtime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(nowtime);
        System.out.println(time);
    }
}
