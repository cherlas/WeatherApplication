package com.weather.yogurt.weatherapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weather.yogurt.weatherapp.model.City;
import com.weather.yogurt.weatherapp.model.Country;
import com.weather.yogurt.weatherapp.model.Province;
import com.weather.yogurt.weatherapp.model.CityInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yogurt on 5/5/16.
 */
public class WeatherDB {
    /*
        数据库名
     */
    public static final String DB_NAME="Weather";
    /*
        数据库版本
     */
    private static final int VERSION=1;
    private static WeatherDB weatherDB;
    private SQLiteDatabase db;
    private WeatherDB(Context context){
        WeatherOpenHelper dpHelper=new WeatherOpenHelper(context,DB_NAME,null,VERSION);
        db=dpHelper.getWritableDatabase();
    }

    /*
        获取WeatherDB实例
     */
    public synchronized static WeatherDB getInstance(Context context){
        if (weatherDB==null){
            weatherDB=new WeatherDB(context);
        }
        return weatherDB;
    }

    /*
        将数据存储到数据库中
     */
    public void saveInformation(CityInformation information){
        if (information!=null){
            ContentValues values=new ContentValues();
            values.put("country_code",information.getCountryCode());
            values.put("country_name_pinyin",information.getCountryNamePinYin());
            values.put("country_name_chinese",information.getTownNameChinese());
            values.put("city_name",information.getCityName());
            values.put("province_name",information.getProvinceName());
            db.insert("CityInformation",null,values);
        }
    }

    /*
        从数据库中读取全国所有省份信息
     */
    public List<Province> loadProvinces(){
        List<Province> list=new ArrayList<>();
        Cursor cursor=db.query("CityInformation",new String[]{"province_name"},null,null,"province_name",null,"province_name");
        if (cursor.moveToFirst()){
            do {
                Province province=new Province();
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /*
        查询数据库中某省下面的所有城市信息
     */
    public List<City> loadCities(String provinceName){
        List<City> list=new ArrayList<>();
        Cursor cursor=db.query("CityInformation",new String[]{"city_name"},"province_name=?",new String[]{String.valueOf(provinceName)},"city_name",null,"city_name");
        if (cursor.moveToFirst()){
            do {
                City city=new City();
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceName(provinceName);
               list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /*
        从数据库中读取某城市下所有的县信息
     */
    public List<Country> loadCountries(String cityName){
        List<Country> list=new ArrayList<>();
        Cursor cursor=db.query("CityInformation",new String[]{"country_code","country_name_pinyin","country_name_chinese"},"city_name=?",new String[]{String.valueOf(cityName)},"country_name_chinese",null,"country_name_chinese");
        if (cursor.moveToFirst()){
            do {
                Country country=new Country();

                country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCountryNamePinYin(cursor.getString(cursor.getColumnIndex("country_name_pinyin")));
                country.setCountryNameChinese(cursor.getString(cursor.getColumnIndex("country_name_chinese")));
                list.add(country);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
