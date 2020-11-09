package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    //设置一个字符串用来存放频道
    private static final String action="com.example.broadcast.I_LOVE_YOU";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //获取MainActivity中传来的intent中的广播，如果是则执行下面内容
        if (intent.getAction().equals(action)){
            Toast.makeText(context,"我喜欢你",Toast.LENGTH_LONG).show();
            Log.d("提示","我喜欢你哈！");
        }

    }
}