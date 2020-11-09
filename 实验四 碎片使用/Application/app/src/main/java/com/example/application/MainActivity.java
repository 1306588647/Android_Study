package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //设置两个New类数组，一会儿用来存放News对象
    public static News[] news = new News[2];
    //设置两个long型变量，用来存放详细新闻页面进入和退出的时间
    public static long stopTime1, stopTime2;
    //设置id号，用来存放浏览的是第几个新闻
    public static int id;
//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向


        //首先获取第一个新闻RelativeLayout布局的id号，将其转化为对象
        RelativeLayout news1 = findViewById(R.id.news1);
        //调用监听器，后面代码会自动生成
        news1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取点击后，系统当前时间
                stopTime1 = System.currentTimeMillis();

                //为news[0]对象设置具体新闻内容
                setNewsContent1();

                //获取第一条新闻
                id = news[0].getId();


                //如果设备是竖屏显示
                if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
                    //建立Intent对象，第一个参数是当前页面，第二个参数就是需要跳转的页面
                    Intent intent = new Intent(MainActivity.this, DetailNews.class);

                    //调用intent的putExtra方法，第一个参数是取一个别名，第二个参数就是将第一个新闻对象写入
                    intent.putExtra("news", news[0]);
                    startActivity(intent);
                }

                //如果设备是竖屏显示
                else {
                    //由于右边碎片没有内容显示，所以要将右边碎片实例化对象，动态添加到布局中
                    RightFragment rightFragment = new RightFragment();

                    //获取FragmentManager实例
                    FragmentManager fm =getSupportFragmentManager();

                    //获取FragmentTransaction实例
                    FragmentTransaction beginTransaction = fm.beginTransaction();

                    //利用Bundle对象来传递news[0]这个新闻对象
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news",news[0]);
                    rightFragment.setArguments(bundle);

                    //用rightFragment这个碎片替换其中的内容，第一个参数是装当前碎片的容器，第二个就是需要显示的布局对象
                    beginTransaction.replace(R.id.rightframe,rightFragment);


                    beginTransaction.commit();
                }



            }
        });



        //这是第二个新闻，和上面第一个新闻差不多

        //首先获取第二个新闻RelativeLayout布局的id号，将其转化为对象
        RelativeLayout news2 = findViewById(R.id.news2);
        //调用监听器，后面代码会自动生成
        news2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTime1 = System.currentTimeMillis();
                setNewsContent2();
                id = news[1].getId();
                if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(MainActivity.this, DetailNews.class);
                    //调用intent的putExtra方法，第一个参数是取一个别名，第二个参数就是将第一个新闻对象
                    intent.putExtra("news", news[1]);
                    startActivity(intent);
                }
                else {
                    RightFragment rightFragment = new RightFragment();
                    FragmentManager fm =getSupportFragmentManager();
                    FragmentTransaction beginTransaction = fm.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news",news[1]);
                    rightFragment.setArguments(bundle);
                    beginTransaction.replace(R.id.rightframe,rightFragment);
                    beginTransaction.commit();
                }

            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();

        //记录返回到新闻列表时候的时间
        stopTime2 = System.currentTimeMillis();

        //由于使用毫秒，所以要除以1000，当两者时间差大于30秒时执行下面操作
        if ((stopTime2 - stopTime1) / 1000 > 30) {
            //如果是第一条新闻
            if (id == 1) {
                //获取第一条新闻的title   id号转化为对象
                TextView title = findViewById(R.id.listtitle1);
                //调用对象设置字体颜色的方法，设置为灰色
                title.setTextColor(Color.GRAY);
            }
            ////如果是第二条新闻
            else if (id == 2) {
                //获取第二条新闻的title   id号转化为对象
                TextView title = findViewById(R.id.listtitle2);
                //调用对象设置字体颜色的方法，设置为灰色
                title.setTextColor(Color.GRAY);
            }

        }
    }



    //这个方法用来设置第一个新闻具体内容
    public void setNewsContent1() {
        //new一个News对象，放在news[]的第一个数组news[0]中
        news[0] = new News();
        //在第一个新闻对象对象中，分别设置标题，内容，来源，时间和新闻id号
        news[0].setTitle("海绵宝宝快乐的一天");
        news[0].setContent("海绵宝宝和派大星打架，派大星：你这个黄色块块！海绵宝宝：你这个粉红尖尖！");
        news[0].setSource("来源：新华网");
        news[0].setTime("2020/10/15");
        news[0].setId(1);

    }


    //这个方法用来设置第二个新闻具体内容
    public  void setNewsContent2() {
        //new第二个News对象，放在news[]的第二个数组news[1]中
        news[1] = new News();
        //在第二个新闻对象对象中，分别设置标题，内容，来源，时间和新闻id号
        news[1].setTitle("派大星星开心的一天");
        news[1].setContent("派大星：他真是可怕了，一看到他我就恶心！那双大牛眼睛、方身体、两颗大门牙，还有那个愚蠢的领带！真是太可怕了！ 海绵宝宝：呃…… 派大星：但是这些在你身上就很好看~");
        news[1].setSource("来源：新华网");
        news[1].setTime("2020/10/15");
        news[1].setId(2);

    }
}