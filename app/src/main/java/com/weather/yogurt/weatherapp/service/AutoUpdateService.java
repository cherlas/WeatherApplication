package com.weather.yogurt.weatherapp.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.weather.yogurt.weatherapp.database.WeatherDB;
import com.weather.yogurt.weatherapp.util.HttpCallbackListener;
import com.weather.yogurt.weatherapp.util.HttpUtil;
import com.weather.yogurt.weatherapp.util.Utility;

/**
 * Created by Yogurt on 5/30/16.
 */
public class AutoUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=6*1000;//6S毫秒数

        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
       // Log.d("currentTime",triggerAtTime+"");
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }

    /*
        更新天气
     */
    private void updateWeather(){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String countryName=prefs.getString("basic_city",null);
        String address="http://apis.baidu.com/heweather/weather/free?city="+countryName;
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String result) {
                try {
                    boolean flag=Utility.handleWeatherConditionResponse(AutoUpdateService.this,result);
                    Log.d("AutoUpdateService",String.valueOf(flag));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        }, WeatherDB.getInstance(this));
    }
}
