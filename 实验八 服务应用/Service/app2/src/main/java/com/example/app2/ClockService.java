package com.example.app2;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class ClockService extends Service {

    //声明字符变量，用于存储编辑框传来的字符串
    String data = "";
    //分别声明小时，分钟和秒
    int hour;
    int minute;
    int second;


    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //建立一个进程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取编辑框传来的时间数据
                data = intent.getStringExtra("timedata");
                //用：分割出来小时，分钟，秒数
                String[] split = data.split(":");
                //分别赋值给对应的变量
                hour = Integer.parseInt(split[0]);
                minute = Integer.parseInt(split[1]);
                second = Integer.parseInt(split[2]);
                //new时间对象，并调用构造方法
                Time time = new Time(hour,minute,second);
                //循环发送数据，每循环一次，倒计时秒数减1
                while (time.countDown()){
                    try {
                        //每执行一次，进程休眠1s
                        Thread.sleep(1000);
                        //每执行一次，将新的时间合起来组成datatime变量
                        String datatime = time.getHour()+":"+time.getMinute()+":"+time.getSecond();
                        //声明Intent对象
                        Intent intent1 = new Intent();
                        //将新的时间装进来
                        intent1.putExtra("datetime",datatime);

                        //设置频道
                        intent1.setAction("com.example.app2.ClockActivity.MyService");

                        //发送广播
                        sendBroadcast(intent1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
            //开启线程
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}