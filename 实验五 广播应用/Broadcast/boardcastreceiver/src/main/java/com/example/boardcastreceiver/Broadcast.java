package com.example.boardcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Broadcast extends BroadcastReceiver {
    //设置一个字符串用来存储频道
    public static final String action = "com.example.boardcastreceiver.I_LOVE_YOU";
    @Override
    public void onReceive(Context context, Intent intent) {
        //如果intent传来的频道和动态注册广播接收者的频道相同时，打印日志内容
        if (intent.getAction().equals(action)){
            Log.d("消息",intent.getStringExtra("extraKey"));
        }
    }
}
