package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent();
//       固定写法，安卓8.0以上需要这样写，后面的字符串跟上自己广播接收者的名字
        if (Build.VERSION.SDK_INT >= 26) {
            intent.setComponent(new ComponentName(getPackageName(), getPackageName() + ".MyReceiver"));
        }
        //发送广播，频道为字符串内容
        intent.setAction("com.example.broadcast.I_LOVE_YOU");
        sendBroadcast(intent);
    }
}