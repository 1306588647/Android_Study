package com.example.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //声明开始按钮
    Button start_button;
    //声明停止按钮
    Button stop_button;
    //声明编辑框
    EditText editView;
    //声明handler
    Handler handler;
    //声明是否在倒计时布尔变量值
    boolean isWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //分别获取三个组件
        start_button = findViewById(R.id.start_button);
        stop_button = findViewById(R.id.stop_button);
        editView = findViewById(R.id.editview);



        //创建handler对象
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //为编辑框设置新的倒计时数字
                editView.setText(String.valueOf(msg.arg1));
            }
        };


        //为开始按钮设置点击事件监听器
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击开始按钮时，倒计时开始为真
                isWork = true;
                //获取编辑框中的倒计时，并转换为int型变量
                int number = Integer.parseInt(editView.getText().toString());
                //创建线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //循环将倒计时减一
                        for (int i = number-1; i>=0 ; i--) {
                            //如果倒计时正常工作
                            if (isWork) {
                                try {
                                    //休眠1s
                                    Thread.sleep(1000);
                                    //创建message对象用来为handler对象发送倒计时信息
                                    Message message = new Message();
                                    //将当前倒计时数字放到message中的arg1中
                                    message.arg1 = i;
                                    //立即发送消息
                                    handler.sendMessage(message);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }



                    }
                    //启动线程
                }).start();

            }
        });

        //为停止按钮设置点击事件监听器
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            //点击停止按钮后，倒计时是否正常工作为假，倒计时结束
            public void onClick(View v) {
                isWork = false;
            }
        });


    }
}