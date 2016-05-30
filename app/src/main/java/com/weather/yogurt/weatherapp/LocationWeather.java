package com.weather.yogurt.weatherapp;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Yogurt on 5/30/16.
 */
public class LocationWeather extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

    }
}
