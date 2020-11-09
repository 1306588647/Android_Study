package com.example.app2;

//自定义时间类
public class Time {
    //小时
    private int hour;

    //分钟
    private int minute;

    //秒钟
    private int second;


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    //构造方法
    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }


    //该方法用于倒计时
    public boolean countDown(){
        if (getSecond()>0){
            setSecond(getSecond()-1);
        }
        else{
            if (getMinute()>0){
                setMinute(getMinute()-1);
                setSecond(60);
            }
            else {
                if (getHour()>0){
                    setHour(getHour()-1);
                    setMinute(60);
                }
                else {
                    return false;
                }
            }
            setSecond(getSecond()-1);
        }
        return true;
    }
}
