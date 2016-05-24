package com.weather.yogurt.weatherapp.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weather.yogurt.weatherapp.R;
import com.weather.yogurt.weatherapp.util.HttpCallbackListener;
import com.weather.yogurt.weatherapp.util.HttpUtil;
import com.weather.yogurt.weatherapp.util.Utility;

import org.json.JSONException;

/**
 * Created by Yogurt on 5/21/16.
 */
public class WeatherActivity extends Activity {

    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;//显示城市
    private TextView weatherConditionText;//天气大概状况
    private TextView weatherTemperatureText;
    private TextView weatherConditionDetailText,hourlyWeatherCondition;
    private HttpCallbackListener listener;
   // WeatherDB weatherDB=WeatherDB.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        weatherInfoLayout= (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText= (TextView) findViewById(R.id.city_name_title);
        weatherConditionText= (TextView) findViewById(R.id.weather_condition_text);
        weatherTemperatureText= (TextView) findViewById(R.id.weather_temperature_text);
        weatherConditionDetailText= (TextView) findViewById(R.id.weather_condition_detail_text);
        hourlyWeatherCondition= (TextView) findViewById(R.id.hourly_weather_condition);

        listener=new HttpCallbackListener() {
            @Override
            public void onFinish(String result) {
                try {
                    Utility.handleWeatherConditionResponse(WeatherActivity.this,result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        };
        HttpUtil.sendHttpRequest("http://apis.baidu.com/heweather/weather/free?city="+getIntent().getStringExtra("country_name_pinyin"),listener,null);
        showWeather();
    }

    /*
        从sharedPreferences里面取出天气信息,,显示到界面上
     */
    private void showWeather(){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("AAAAA",prefs.getString("basic_city",""));
        cityNameText.setText(prefs.getString("basic_city",""));
        weatherConditionText.setText(prefs.getString("now_cond_txt","")+","+prefs.getString("now_wind_dir","")+"  "+prefs.getString("now_wind_sc","")+"级");
        weatherTemperatureText.setText(prefs.getString("tmp_min_0","")+"° ~ "+prefs.getString("tmp_max_0","")+"°");
        StringBuilder upSb=new StringBuilder();
        StringBuilder downSb=new StringBuilder(" ");
        for (int i=0;i<prefs.getInt("hourly_length",0);i++){
            String up=prefs.getString("today_date_"+i,"").substring(prefs.getString("today_date_"+i,"").indexOf(" ")+1);
            upSb.append(up);
            downSb.append(prefs.getString("today_tmp_"+i,"")+"°");
            if (i!=prefs.getInt("hourly_length",0)-1){
                upSb.append("   ");
                downSb.append("    ");
            }
        }
        hourlyWeatherCondition.setText(upSb.append("\r\n").append(downSb));
        StringBuilder weatherDetail=new StringBuilder();
        for (int i=0;i<7;i++){
            weatherDetail.append(prefs.getString("date_"+i,"").substring(0)).append("  ");
            String weaD=prefs.getString("cond_txt_d_"+i,"");
            String weaN=prefs.getString("cond_txt_n_"+i,"");
            if (weaD.equals(weaN)){
                weatherDetail.append(weaD).append("  ");
            }else {
                weatherDetail.append(weaD).append("转").append(weaN).append("  ");
            }
            String maxTmp=prefs.getString("tmp_max_"+i,"");
            String minTmp=prefs.getString("tmp_min_"+i,"");
            weatherDetail.append(minTmp).append("° ~ ").append(maxTmp).append("°");
            weatherDetail.append("\r\n");
        }
        weatherDetail.append("舒适度指数:").append(prefs.getString("suggestion_comf_brf","")).append(";  ").append(prefs.getString("suggestion_comf_txt","")).append("\r\n");
        weatherDetail.append("洗车指数:").append(prefs.getString("suggestion_cw_brf","")).append(";  ").append(prefs.getString("suggestion_cw_txt","")).append("\r\n");
        weatherDetail.append("穿衣指数:").append(prefs.getString("suggestion_drsg_brf","")).append(";  ").append(prefs.getString("suggestion_drsg_txt","")).append("\r\n");
        weatherDetail.append("感冒指数:").append(prefs.getString("suggestion_flu_brf","")).append(";  ").append(prefs.getString("suggestion_flu_txt","")).append("\r\n");
        weatherDetail.append("运动指数:").append(prefs.getString("suggestion_sport_brf","")).append(";  ").append(prefs.getString("suggestion_sport_txt","")).append("\r\n");
        weatherDetail.append("旅游指数:").append(prefs.getString("suggestion_trav_brf","")).append(";  ").append(prefs.getString("suggestion_trav_txt","")).append("\r\n");
        weatherDetail.append("紫外线指数:").append(prefs.getString("suggestion_uv_brf","")).append(";  ").append(prefs.getString("suggestion_uv_txt","")).append("\r\n");

        weatherConditionDetailText.setText(weatherDetail);
    }

}
