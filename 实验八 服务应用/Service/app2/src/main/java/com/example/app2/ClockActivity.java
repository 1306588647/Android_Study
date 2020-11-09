package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ClockActivity extends AppCompatActivity {

    //声明三个控件
    static Button button;
    static EditText editText;
    static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //为三个空间进行绑定
        button=findViewById(R.id.button_1);
        editText=findViewById(R.id.edit_text);
        textView=findViewById(R.id.textview);

        //为按钮设置事件点击监听器
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取输入框中输入的时间格式为12:10:9
                String time = editText.getText().toString();

                //创建Intent对象
                Intent intent = new Intent(ClockActivity.this,ClockService.class);

                //将获取到的时间数据放进intent中
                intent.putExtra("timedata",time);

                //开启服务
                startService(intent);

                //当点击按钮后，将下方的文本框设置成倒计时中
                textView.setText("倒计时中");

                //实例化广播
                ClockActivity.MyReceiver myReceiver = new ClockActivity.MyReceiver();
                //设置过滤器
                IntentFilter filter = new IntentFilter("com.example.app2.ClockActivity.MyService");
                //动态注册广播
                registerReceiver(myReceiver,filter);
            }
        });



    }

    public static class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //接受广播，获取其中的数据
            String data = intent.getStringExtra("datetime");
            //如果倒计时结束了
            if (data.equals("0:0:0")){
                //将编辑框设成0:0:0倒计时结束
                editText.setText("0:0:0");
                //设置下方文本框倒计时结束
                textView.setText("时间到！");
            }
            else {
                //如果没有结束，则将传来的数据显示在编辑框中
                editText.setText(data);
            }

        }
    }




}