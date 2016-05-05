package com.weather.yogurt.weatherapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yogurt on 5/5/16.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper{
    /*
        province 建表语句
     */
    public static final String CREATE_PROVINCE="create table Province ("
            +"id integer primary key autoincrement ,"
            +"province_name text,"
            +"province_code text)";
    /*
        city建表语句
     */
    public static final String CREATE_CITY="create table City ("
            +"id integer primary key autoincrement ,"
            +"city_name text,"
            +"city_code text,"
            +"province_id integer,"
            +"city_postcode Text,"
            +"city_telAreaCode text)";
    /*
        Country建表语句//.......县......
     */
    public static final String CREATE_COUNTRY="create table Country ("
            +"id integer primary key autoincrement ,"
            +"country_name text,"
            +"country_code text,"
            +"city_id integer)";

    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COUNTRY);
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
