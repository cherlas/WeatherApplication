package com.weather.yogurt.weatherapp.util;

import android.os.Handler;
import android.os.Message;

import com.weather.yogurt.weatherapp.database.WeatherDB;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yogurt on 5/6/16.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener,final WeatherDB weatherDB){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==1){
                    Utility.handleXMLResponse(weatherDB,msg.obj.toString());
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try {
                    URL url=new URL(address);
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setRequestMethod("GET");

                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String line;
                    final StringBuilder response=new StringBuilder();
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    Message msg=new Message();
                    msg.what=1;
                    msg.obj=response;
                    handler.sendMessage(msg);
                    if (listener!=null){
                        //回调onFinish方法
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if (listener!=null){
                        //回调onError方法
                        listener.onError(e);
                    }
                }finally {
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}