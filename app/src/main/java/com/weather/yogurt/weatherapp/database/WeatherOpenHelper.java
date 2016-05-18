package com.weather.yogurt.weatherapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yogurt on 5/5/16.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper{
    /*
       城市信息 建表语句
     */
    public static final String CREATE_CITYINFORMATIONTABLE="create table CityInformation ("
            +"id integer primary key autoincrement,"
            +"country_code text,"
            +"country_name_pinyin text,"
            +"country_name_chinese text,"
            +"city_name text,"
            +"province_name text)";


    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CITYINFORMATIONTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
