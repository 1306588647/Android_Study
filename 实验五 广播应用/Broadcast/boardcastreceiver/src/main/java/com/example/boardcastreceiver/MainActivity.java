package com.example.boardcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //设置一个字符串用来存储频道
    private static final String action="com.example.boardcastreceiver.I_LOVE_YOU";
    //在此处声明一个自定义Broadcast
    public  Broadcast broadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态注册广播接收者
        //实例化BroadcastReceiver子类Broadcast
        broadcast = new Broadcast();
        //设置接收广播的类型,只有持有相同的action的接受者才能接收此广播
        IntentFilter intentFilter = new IntentFilter(action);
        //动态注册
        registerReceiver(broadcast,intentFilter);



        Intent intent = new Intent();
        //设置广播标识
        intent.setAction(action);
        //设置传递内容
        intent.putExtra("extraKey","CustomValue");
        //发送广播
        sendBroadcast(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当Activity销毁时，销毁广播
        unregisterReceiver(broadcast);
    }
}