package com.example.application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class CreateNewsDB extends SQLiteOpenHelper {

    //定义一个字符串用来装入数据库表的建立语句
    public static final String CREATE_Table_SQL = "CREATE TABLE NewsTable(" +
            "nID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Title TEXT," +
            "Content TEXT," +
            "Source TEXT," +
            "time TEXT)";

    public CreateNewsDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        if (getDatabaseName()==name){
            context.deleteDatabase(name);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Table_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
