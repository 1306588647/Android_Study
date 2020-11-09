package com.example.application;


import java.io.Serializable;

//News类实现Serializable接口，因为Intent要传对象，所以需要实现该接口
public class News  implements Serializable {

    public String title;        //设置新闻标题字符串变量
    public String content;      //设置新闻内容字符串变量
    public String source;       //设置新闻来源字符串变量
    public String time;         //设置新闻时间字符串变量
    public int id;              //设置新闻具体id变量，方便后续查看30s后，新闻标题编程灰色


    //    具体的set和get方法
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
