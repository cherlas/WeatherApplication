package com.weather.yogurt.weatherapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weather.yogurt.weatherapp.R;
import com.weather.yogurt.weatherapp.service.AutoUpdateService;
import com.weather.yogurt.weatherapp.util.HttpCallbackListener;
import com.weather.yogurt.weatherapp.util.HttpUtil;
import com.weather.yogurt.weatherapp.util.Utility;

import org.json.JSONException;

/**
 * Created by Yogurt on 5/21/16.
 */
public class WeatherActivity extends Activity implements View.OnClickListener{

    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;//显示城市
    private TextView weatherConditionText;//天气大概状况
    private TextView weatherTemperatureText;
    private TextView weatherConditionDetailText,hourlyWeatherCondition;
    private Button switchCity,refresh;
    private HttpCallbackListener listener;
    private ProgressDialog progressDialog;
   // WeatherDB weatherDB=WeatherDB.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);



        weatherInfoLayout= (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText= (TextView) findViewById(R.id.city_name_title);
        weatherConditionText= (TextView) findViewById(R.id.weather_condition_text);
        weatherTemperatureText= (TextView) findViewById(R.id.weather_temperature_text);
        weatherConditionDetailText= (TextView) findViewById(R.id.weather_condition_detail_text);
        hourlyWeatherCondition= (TextView) findViewById(R.id.hourly_weather_condition);
        switchCity= (Button) findViewById(R.id.switch_city);
        refresh= (Button) findViewById(R.id.refresh);

        requestAddShow(prefs.getString("basic_city",""));


        switchCity.setOnClickListener(this);
        refresh.setOnClickListener(this);

    }

    /*
        从sharedPreferences里面取出天气信息,显示到界面上
     */
    private void showWeather(){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        //Log.d("AAAAA",prefs.getString("basic_city",""));
        cityNameText.setText(prefs.getString("basic_city",""));
        weatherConditionText.setText(prefs.getString("now_cond_txt","")+","+prefs.getString("now_wind_dir","")+"  "+prefs.getString("now_wind_sc","")+"级");
        weatherTemperatureText.setText(prefs.getString("tmp_min_0","")+"° ~ "+prefs.getString("tmp_max_0","")+"°");
        StringBuilder hourlySb=new StringBuilder();

        for (int i=0;i<prefs.getInt("hourly_length",0);i++){
            String up=prefs.getString("today_date_"+i,"").substring(prefs.getString("today_date_"+i,"").indexOf(" "));
           // Log.d("todaydate",prefs.getString("today_date_"+i,""));
            hourlySb.append(up.substring(0,3)).append("时").append(":");
            hourlySb.append(prefs.getString("today_tmp_"+i,"")).append("°").append("    ");
        }
        hourlyWeatherCondition.setText(hourlySb);
        StringBuilder weatherDetail=new StringBuilder();
        for (int i=0;i<7;i++){
            StringBuilder sb=new StringBuilder();
            //Log.d("dateI",prefs.getString("date_"+i,""));
            sb.append(prefs.getString("date_"+i,"").substring(5)).append("           ");
            String weaD=prefs.getString("cond_txt_d_"+i,"");
            String weaN=prefs.getString("cond_txt_n_"+i,"");
            StringBuilder cond=new StringBuilder();
            if (weaD.equals(weaN)){
                cond.append(weaD);
            }else {
                cond.append(weaD).append("转").append(weaN);
            }
            int len=cond.length();
            for (int j=0;j<=7-len;j++) {
                cond.append("    ");
            }
            sb.append(cond);
            String maxTmp=prefs.getString("tmp_max_"+i,"");
            String minTmp=prefs.getString("tmp_min_"+i,"");
            sb.append(minTmp).append("° ~ ").append(maxTmp).append("°");
            sb.append("\r\n");
            weatherDetail.append(sb);
        }

        weatherDetail.append("\r\n");

        weatherDetail.append("舒适度指数:").append(prefs.getString("suggestion_comf_brf","")).append(";  ").append(prefs.getString("suggestion_comf_txt","")).append("\r\n\r\n");
        weatherDetail.append("洗车指数:").append(prefs.getString("suggestion_cw_brf","")).append(";  ").append(prefs.getString("suggestion_cw_txt","")).append("\r\n\r\n");
        weatherDetail.append("穿衣指数:").append(prefs.getString("suggestion_drsg_brf","")).append(";  ").append(prefs.getString("suggestion_drsg_txt","")).append("\r\n\r\n");
        weatherDetail.append("感冒指数:").append(prefs.getString("suggestion_flu_brf","")).append(";  ").append(prefs.getString("suggestion_flu_txt","")).append("\r\n\r\n");
        weatherDetail.append("运动指数:").append(prefs.getString("suggestion_sport_brf","")).append(";  ").append(prefs.getString("suggestion_sport_txt","")).append("\r\n\r\n");
        weatherDetail.append("旅游指数:").append(prefs.getString("suggestion_trav_brf","")).append(";  ").append(prefs.getString("suggestion_trav_txt","")).append("\r\n\r\n");
        weatherDetail.append("紫外线指数:").append(prefs.getString("suggestion_uv_brf","")).append(";  ").append(prefs.getString("suggestion_uv_txt","")).append("\r\n\r\n");

        weatherConditionDetailText.setText(weatherDetail);
        Intent intent=new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_city:
                Intent intent=new Intent(WeatherActivity.this,ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity",true);
                startActivityForResult(intent,1);
                break;
            case R.id.refresh:
                SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
               // Log.d("basic_city",prefs.getString("basic_city",""));
                requestAddShow(prefs.getString("basic_city",""));
                Log.d("refresh","Successful");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (resultCode){
            case RESULT_OK:
                if (requestCode==1){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showProgressDialog();
                        }
                    });
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestAddShow(data.getStringExtra("country_name_pinyin"));
                        }
                    }).start();
                }
                break;
            default:
                 break;
        }
    }

    private void requestAddShow(String countryName){
//        String basicCity=PreferenceManager.getDefaultSharedPreferences(this).getString("basic_city","");
//        if (!countryName.equals(basicCity)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgressDialog();
                }
            });
//        }

        listener=new HttpCallbackListener() {
            @Override
            public void onFinish(String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //closeProgressDialog();
                    }
                });
                try {
                    boolean flag=Utility.handleWeatherConditionResponse(WeatherActivity.this,result);
                    if (flag){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                                showWeather();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        };
        HttpUtil.sendHttpRequest("http://apis.baidu.com/heweather/weather/free?city="+countryName,listener,null);
    }

    /*
       显示进度对话框
    */
    private void showProgressDialog() {

        if (progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
