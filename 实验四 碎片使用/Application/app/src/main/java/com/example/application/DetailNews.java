package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        //获取主页面Intent传来的news对象
        News news = (News) getIntent().getSerializableExtra("news");


        //获取详情界面的title   id并且转化为对象
        TextView title = findViewById(R.id.title);
        //使用setText方法设置标题内容，标题内容在该对象中的title变量中，使用getTitle方法获得具体标题
        title.setText(news.getTitle());


        //获取详情界面的source   id并且转化为对象
        TextView source = findViewById(R.id.source);
        //使用setText方法设置来源内容，来源内容在该对象中的source变量中，使用getSource方法获得具体来源
        source.setText(news.getSource());


        //获取详情界面的time   id并且转化为对象
        TextView time = findViewById(R.id.date);
        //使用setText方法设置时间内容，时间内容在该对象中的time变量中，使用getTime方法获得具体时间
        time.setText(news.getTime());


        //获取详情界面的content   id并且转化为对象
        TextView content = findViewById(R.id.content);
        //使用setText方法设置具体内容，具体内容在该对象中的content变量中，使用getContent方法获得具体内容
        content.setText(news.getContent());
    }
}