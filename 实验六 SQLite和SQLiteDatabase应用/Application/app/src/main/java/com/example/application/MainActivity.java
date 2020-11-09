package com.example.application;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //设置New类数组，一会儿用来存放News对象
    public static News[] news;

    //声明一个ArrayList数组用来存放已经看过的新闻ID
    ArrayList<String> idArrayList;

    //声明一个字符串用来将上面arraylist中的值在转换为字符串并且存储到数据库中
    String idString;


    //定义CreateDB，用于与数据库连接
    CreateNewsDB createNewsDB;

    //声明三个字符串数组用来存放标题、来源、时间信息
    String[] titlearray;
    String[] sourcearray;
    String[] timearray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //对上述arrayList数组进行实例化
        idArrayList=new ArrayList<>();

        //获取sp对象，data表示文件名，MODE_PRIVATE表示文件操作模式
        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();


        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向


        //创建数据库
        createNewsDB = new CreateNewsDB(this, "News.db", null, 1);
        //创建可写入数据库对象db
        SQLiteDatabase db = createNewsDB.getWritableDatabase();

        //插入数据
        insertData(db, "海绵宝宝快乐的一天",
                "海绵宝宝和派大星打架，派大星：你这个黄色块块！海绵宝宝：你这个粉红尖尖！",
                "来源：新华网",
                "2020/10/15");

        insertData(db, "派大星星开心的一天",
                "派大星：他真是可怕了，一看到他我就恶心！那双大牛眼睛、方身体、两颗大门牙，还有那个愚蠢的领带！真是太可怕了！ 海绵宝宝：呃…… 派大星：但是这些在你身上就很好看~",
                "来源：新华网",
                "2020/10/15");

        insertData(db, "蟹老板有钱的一天",
                "派大星：他真是可怕了，一看到他我就恶心！那双大牛眼睛、方身体、两颗大门牙，还有那个愚蠢的领带！真是太可怕了！ 海绵宝宝：呃…… 派大星：但是这些在你身上就很好看~",
                "来源：新华网",
                "2020/10/15");

        insertData(db, "小仙女快乐的一天",
                "海绵宝宝和派大星打架，派大星：你这个黄色块块！海绵宝宝：你这个粉红尖尖！",
                "来源：新华网",
                "2020/10/15");

        //获取数据库表中有多少条数据
        Cursor cursor = createNewsDB.getReadableDatabase().query("NewsTable",
                null, null, null, null, null, null);
        int dataCount = cursor.getCount();


        //定义dataCount个News对象，用来存放信息。
        news = new News[dataCount];

        //调用此方法，为适配器所需要的数组赋值，并且第对news对象数组赋值
        setArrayContent();

        //利用适配器来将信息显示到布局文件上
        //建立List集合，集合中存储Map对象
        List<Map<String, Object>> listitem = new ArrayList<>();
        for (int i = 0; i < titlearray.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", titlearray[i]);
            map.put("source", sourcearray[i]);
            map.put("date", timearray[i]);
            listitem.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, listitem, R.layout.news_template,
                new String[]{"title", "source", "date"}, new int[]{R.id.title, R.id.source, R.id.date});
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);


        //为listview建立事件监听器，当点击每一个item时触发
        listView.setOnItemClickListener((parent, view, position, id) -> {

            //将被点击的新闻id序号存到idArrayList数组中
            idArrayList.add(String.valueOf(id));

            //将 idArrayList转换成字符串
            idString = String.valueOf(idArrayList);

            //将此字符串写入到SharedPreferences文件中
            editor.putString("newsID",idString);
            //提交
            editor.commit();

            //从SharedPreferences获取存储的id字符串
            String getString = sp.getString("newsID",idString);

            //由于从arraylist转换成字符串会带上左右两边的括号，此方法用于删除两边的括号
            getString = getString.substring(1,getString.length()-1);

            //删除字符串中多余的空格
            getString = getString.replace(" ","");

            //将字符串以逗号间隔分成独立的id
            String[] split = getString.split(",");
            for (int i = 0; i < split.length; i++) {
                //获取被点击的新闻id
                int atposition = Integer.parseInt(split[i]);

                //获取相对布局
                RelativeLayout view1 = (RelativeLayout) listView.getChildAt(atposition);

                //获取相对布局中的第一个标题View
                TextView childAt = (TextView) view1.getChildAt(0);

                //将标题设置成灰色
                childAt.setTextColor(Color.rgb(192, 192, 192));
            }




            //如果设备是竖屏显示
            if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
                //建立Intent对象，第一个参数是当前页面，第二个参数就是需要跳转的页面
                Intent intent = new Intent(MainActivity.this, DetailNews.class);

                //调用intent的putExtra方法，第一个参数是取一个别名，第二个参数就是将第一个新闻对象写入
                intent.putExtra("news", news[(int) id]);
                startActivity(intent);
            }
            //如果设备是竖屏显示
            else {
                //由于右边碎片没有内容显示，所以要将右边碎片实例化对象，动态添加到布局中
                RightFragment rightFragment = new RightFragment();

                //获取FragmentManager实例
                FragmentManager fm = getSupportFragmentManager();

                //获取FragmentTransaction实例
                FragmentTransaction beginTransaction = fm.beginTransaction();

                //利用Bundle对象来传递news[0]这个新闻对象
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", news[(int) id]);
                rightFragment.setArguments(bundle);

                //用rightFragment这个碎片替换其中的内容，第一个参数是装当前碎片的容器，第二个就是需要显示的布局对象
                beginTransaction.replace(R.id.rightframe, rightFragment);
                beginTransaction.commit();
            }
        });
    }


    //使用一个函数，将数据插入到数据库对应的表中
    public void insertData(SQLiteDatabase sqLiteDatabase, String title, String content, String source, String time) {
        ContentValues values = new ContentValues();
        values.put("Title", title);
        values.put("Content", content);
        values.put("Source", source);
        values.put("time", time);
        sqLiteDatabase.insert("NewsTable", null, values);
    }






    //设置数组信息
    public void setArrayContent() {
        ArrayList<String> arrayListTitle = new ArrayList<>();
        ArrayList<String> arrayListSource = new ArrayList<>();
        ArrayList<String> arrayListTime = new ArrayList<>();
        Cursor cursor = createNewsDB.getReadableDatabase().query("NewsTable",
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            arrayListTitle.add(cursor.getString(1));
            arrayListSource.add(cursor.getString(3));
            arrayListTime.add(cursor.getString(4));
            News tempnews = new News();
            tempnews.setId(cursor.getInt(0));
            tempnews.setTitle(cursor.getString(1));
            tempnews.setContent(cursor.getString(2));
            tempnews.setSource(cursor.getString(3));
            tempnews.setTime(cursor.getString(4));
            news[cursor.getPosition()] = tempnews;

        }
        titlearray = arrayListTitle.toArray(new String[arrayListTitle.size()]);
        sourcearray = arrayListSource.toArray(new String[arrayListSource.size()]);
        timearray = arrayListTime.toArray(new String[arrayListTime.size()]);
    }


    //当退出应用时，关闭数据库连接
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当数据库不为空时，关闭数据库连接
        if (createNewsDB != null) {
            createNewsDB.close();
        }
    }
}