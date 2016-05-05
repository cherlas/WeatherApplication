package com.weather.yogurt.weatherapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weather.yogurt.weatherapp.model.City;
import com.weather.yogurt.weatherapp.model.Country;
import com.weather.yogurt.weatherapp.model.Province;

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
        将province实例储存到数据库
     */
    public void saveProvince(Province province){
        if (province!=null){
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }

    /*
        从数据库中读取全国所有省份信息
     */
    public List<Province> loadProvinces(){
        List<Province> list=new ArrayList<>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Province province=new Province();
                province.setProvinceId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /*
        将City储存到数据库
     */
    public void saveCities(City city){
        if (city!=null){
            ContentValues values=new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            values.put("city_postcode",city.getCityPostCode());
            values.put("city_telAreaCode",city.getCityTelAreaCode());
            db.insert("City",null,values);
        }
    }

    /*
        查询数据库中某省下面的所有城市信息
     */
    public List<City> loadCities(int provinceId){
        List<City> list=new ArrayList<>();
        Cursor cursor=db.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                City city=new City();
                city.setCityId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                city.setCityPostCode(cursor.getString(cursor.getColumnIndex("city_postcode")));
                city.setCityTelAreaCode(cursor.getString(cursor.getColumnIndex("city_telAreaCode")));
               list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /*
        储存所有的县级城市(Country)到数据库
     */
    public void saveCuontries(Country country){
        if (country!=null){
            ContentValues values=new ContentValues();
            values.put("country_name",country.getCountryName());
            values.put("country_code",country.getCountryCode());
            values.put("city_id",country.getCityId());
            db.insert("Country",null,values);
        }
    }

    /*
        从数据库中读取某城市下所有的县信息
     */
    public List<Country> loadCountries(int cityId){
        List<Country> list=new ArrayList<>();
        Cursor cursor=db.query("Country",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                Country country=new Country();
                country.setCountryId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCityId(cityId);
                list.add(country);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
