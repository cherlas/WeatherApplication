package com.weather.yogurt.weatherapp.util;

/**
 * Created by Yogurt on 5/6/16.
 */
public interface HttpCallbackListener {
    void onFinish(String result);
    void onError(Exception e);
}
