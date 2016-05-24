package com.weather.yogurt.weatherapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.weather.yogurt.weatherapp.database.WeatherDB;
import com.weather.yogurt.weatherapp.model.Aqi;
import com.weather.yogurt.weatherapp.model.Basic;
import com.weather.yogurt.weatherapp.model.CityInformation;
import com.weather.yogurt.weatherapp.model.DailyForecast;
import com.weather.yogurt.weatherapp.model.HourlyForecast;
import com.weather.yogurt.weatherapp.model.Now;
import com.weather.yogurt.weatherapp.model.Suggestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * Created by Yogurt on 5/6/16.
 */
public class Utility {

    /*
        解析和处理服务器返回的城市数据
     */
    public synchronized static boolean handleXMLResponse(WeatherDB weatherDB,String response){
    if (!TextUtils.isEmpty(response)){
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(response));
            int eventType=xmlPullParser.getEventType();
            CityInformation information=new CityInformation();
            while (eventType!=xmlPullParser.END_DOCUMENT){

                String nodeName=xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if ("id".equals(nodeName)){
                            information.setCountryCode(xmlPullParser.nextText());
                        }else if ("town_pinyin".equals(nodeName)){
                            information.setCountryNamePinYin(xmlPullParser.nextText());
                        }else if ("town_chinese".equals(nodeName)){
                            information.setTownNameChinese(xmlPullParser.nextText());
                        }else if ("city".equals(nodeName)){
                            information.setCityName(xmlPullParser.nextText());
                        }else if ("province".equals(nodeName)){
                            information.setProvinceName(xmlPullParser.nextText());
                        }
                        break;
                    }

                    case XmlPullParser.END_TAG:{
                        if ("app".equals(nodeName)){
                           //存数据库
                            weatherDB.saveInformation(information);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType=xmlPullParser.next();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    return false;
}

    /*
        解析和处理服务器返回的天气状况数据
     */
    public static void handleWeatherConditionResponse(Context context,String JsonResponse) throws JSONException {
        JSONArray jsonArray=new JSONArray(JsonResponse.substring(JsonResponse.indexOf("["),JsonResponse.lastIndexOf("]")+1));
        JSONObject jsonObject=jsonArray.getJSONObject(0);
        String status=jsonObject.getString("status");
        if ("ok".equals(status)){
            Basic basic=new Basic();
            JSONObject basicObj=jsonObject.getJSONObject("basic");
            basic.setCity(basicObj.getString("city"));

            basic.setCnty(basicObj.getString("cnty"));
            basic.setId(basicObj.getString("id"));
            basic.setLat(basicObj.getString("lat"));//维度
            basic.setLon(basicObj.getString("lon"));//经度

            JSONObject update=basicObj.getJSONObject("update");
            basic.setUpdateLoc(update.getString("loc"));//当地时间
            basic.setUpdateUtc(update.getString("utc"));//UTC时间

            Now now=new Now();
            JSONObject nowObj=jsonObject.getJSONObject("now");//实况天气
            JSONObject condNow=nowObj.getJSONObject("cond");//天气情况

            now.setCondCode(condNow.getString("code"));//天气状况代码
            now.setCondTxt(condNow.getString("txt"));//天气状况描述
            now.setFl(nowObj.getString("fl"));//体感温度
            now.setHum(nowObj.getString("hum"));//相对湿度
            now.setPcpn(nowObj.getString("pcpn"));//降水量
            now.setPres(nowObj.getString("pres"));//气压
            now.setTmp(nowObj.getString("tmp"));//温度
            now.setVis(nowObj.getString("vis"));//能见度
            JSONObject windNow=nowObj.getJSONObject("wind");//风力风向
            now.setWindDeg(windNow.getString("deg"));//风向(360°)
            now.setWindDir(windNow.getString("dir"));//风向
            now.setWindSc(windNow.getString("sc"));//风力
            now.setWindSpd(windNow.getString("spd"));//风速

            Aqi aqi=new Aqi();
            JSONObject aqiObj=jsonObject.getJSONObject("aqi").getJSONObject("city");
            if (aqiObj.toString()!=null){
                if (aqiObj.has("aqi"))
                aqi.setAqi(aqiObj.getString("aqi"));
                if (aqiObj.has("co"))
                aqi.setCo(aqiObj.getString("co"));
                if (aqiObj.has("no2"))
                aqi.setNo2(aqiObj.getString("no2"));
                if (aqiObj.has("o3"))
                aqi.setO3(aqiObj.getString("o3"));
                if (aqiObj.has("pm10"))
                aqi.setPm10(aqiObj.getString("pm10"));
                if (aqiObj.has("pm25"))
                aqi.setPm25(aqiObj.getString("pm25"));
                if (aqiObj.has("qlty"))
                aqi.setQlty(aqiObj.getString("qlty"));
                if (aqiObj.has("so2"))
                aqi.setSo2(aqiObj.getString("so2"));
            }


            DailyForecast[] dailyForecast=new DailyForecast[7];
            JSONArray dailyForecastArr=jsonObject.getJSONArray("daily_forecast");
            for (int i=0;i<dailyForecastArr.length();i++){
                dailyForecast[i]=new DailyForecast();
                JSONObject dailyForecastObj=dailyForecastArr.getJSONObject(i);
                dailyForecast[i].setAstroSr(dailyForecastObj.getJSONObject("astro").getString("sr"));
                dailyForecast[i].setAstroSs(dailyForecastObj.getJSONObject("astro").getString("ss"));

                dailyForecast[i].setCondCodeD(dailyForecastObj.getJSONObject("cond").getString("code_d"));
                dailyForecast[i].setCondTxtD(dailyForecastObj.getJSONObject("cond").getString("txt_d"));
                dailyForecast[i].setCondCodeN(dailyForecastObj.getJSONObject("cond").getString("code_n"));
                dailyForecast[i].setCondTxtN(dailyForecastObj.getJSONObject("cond").getString("txt_n"));

                dailyForecast[i].setDate(dailyForecastObj.getString("date"));
                dailyForecast[i].setHum(dailyForecastObj.getString("hum"));
                dailyForecast[i].setPcpn(dailyForecastObj.getString("pcpn"));
                dailyForecast[i].setPop(dailyForecastObj.getString("pop"));
                dailyForecast[i].setPres(dailyForecastObj.getString("pres"));
                dailyForecast[i].setVis(dailyForecastObj.getString("vis"));

                dailyForecast[i].setTmpMax(dailyForecastObj.getJSONObject("tmp").getString("max"));
                dailyForecast[i].setTmpMin(dailyForecastObj.getJSONObject("tmp").getString("min"));

                dailyForecast[i].setWindDeg(dailyForecastObj.getJSONObject("wind").getString("deg"));
                dailyForecast[i].setWindDir(dailyForecastObj.getJSONObject("wind").getString("dir"));
                dailyForecast[i].setWindSc(dailyForecastObj.getJSONObject("wind").getString("sc"));
                dailyForecast[i].setWindSpd(dailyForecastObj.getJSONObject("wind").getString("spd"));
            }


            JSONArray hourlyForecastArr=jsonObject.getJSONArray("hourly_forecast");
            HourlyForecast[] hourlyForecasts=new HourlyForecast[hourlyForecastArr.length()];
            for (int i=0;i<hourlyForecastArr.length();i++){
                JSONObject hourlyForecastObj=hourlyForecastArr.getJSONObject(i);
                hourlyForecasts[i]=new HourlyForecast();
                hourlyForecasts[i].setDate(hourlyForecastObj.getString("date"));
                hourlyForecasts[i].setDate(hourlyForecastObj.getString("hum"));
                hourlyForecasts[i].setDate(hourlyForecastObj.getString("pop"));
                hourlyForecasts[i].setDate(hourlyForecastObj.getString("pres"));
                hourlyForecasts[i].setDate(hourlyForecastObj.getString("tmp"));

                hourlyForecasts[i].setWindDeg(hourlyForecastObj.getJSONObject("wind").getString("deg"));
                hourlyForecasts[i].setWindDir(hourlyForecastObj.getJSONObject("wind").getString("dir"));
                hourlyForecasts[i].setWindSc(hourlyForecastObj.getJSONObject("wind").getString("sc"));
                hourlyForecasts[i].setWindSpd(hourlyForecastObj.getJSONObject("wind").getString("spd"));
            }

            Suggestion suggestion=new Suggestion(); //生活指数
            JSONObject suggestionObj=jsonObject.getJSONObject("suggestion");
            //
            suggestion.setComfBrf(suggestionObj.getJSONObject("comf").getString("brf"));//舒适度指数
            suggestion.setComfTxt(suggestionObj.getJSONObject("comf").getString("txt"));

            suggestion.setCwBrf(suggestionObj.getJSONObject("cw").getString("brf"));//洗车指数
            suggestion.setCwTxt(suggestionObj.getJSONObject("cw").getString("txt"));

            suggestion.setDrsgBrf(suggestionObj.getJSONObject("drsg").getString("brf"));//穿衣指数
            suggestion.setDrsgTxt(suggestionObj.getJSONObject("drsg").getString("txt"));

            suggestion.setFluBrf(suggestionObj.getJSONObject("flu").getString("brf"));//感冒指数
            suggestion.setFluTxt(suggestionObj.getJSONObject("flu").getString("txt"));

            suggestion.setSportBrf(suggestionObj.getJSONObject("sport").getString("brf"));//运动指数
            suggestion.setSportTxt(suggestionObj.getJSONObject("sport").getString("txt"));

            suggestion.setTravBrf(suggestionObj.getJSONObject("trav").getString("brf"));
            suggestion.setTravTxt(suggestionObj.getJSONObject("trav").getString("txt"));//旅游指数

            suggestion.setUvBrf(suggestionObj.getJSONObject("uv").getString("brf"));//紫外线指数
            suggestion.setUvTxt(suggestionObj.getJSONObject("uv").getString("txt"));

            saveWeatherInfo(context,now,basic,aqi,dailyForecast,hourlyForecasts,suggestion);
        }else {
            Log.d("Utility","查询错误");
        }

    }

    /*
        将服务器返回的所有天气信息讯处到SharedPreference文件中
     */
    public static void saveWeatherInfo(Context context,Now now, Basic basic, Aqi aqi, DailyForecast[] dailyForecast, HourlyForecast[] hourlyForecasts, Suggestion suggestion){
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putBoolean("city_selected",true);
        editor.putString("now_cond_code",now.getCondCode());
        editor.putString("now_cond_txt",now.getCondTxt());
        editor.putString("now_fl",now.getFl());
        editor.putString("now_hum",now.getHum());
        editor.putString("now_pcpn",now.getPcpn());
        editor.putString("now_pres",now.getPres());
        editor.putString("now_tmp",now.getTmp());
        editor.putString("now_vis",now.getVis());
        editor.putString("now_wind_deg",now.getWindDeg());
        editor.putString("now_wind_dir",now.getWindDir());
        editor.putString("now_wind_sc",now.getWindSc());
        editor.putString("now_wind_spd",now.getWindSpd());

        editor.putString("basic_city",basic.getCity());

        editor.putString("basic_cnty",basic.getCnty());
        editor.putString("basic_id",basic.getId());
        editor.putString("basic_lat",basic.getLat());
        editor.putString("basic_lon",basic.getLon());
        editor.putString("basic_update_loc",basic.getUpdateLoc());
        editor.putString("basic_update_utc",basic.getUpdateUtc());

        editor.putString("aqi_aqi",aqi.getAqi());
        editor.putString("aqi_co",aqi.getCo());
        editor.putString("aqi_no2",aqi.getNo2());
        editor.putString("aqi_o3",aqi.getO3());
        editor.putString("aqi_pm10",aqi.getPm10());
        editor.putString("aqi_pm25",aqi.getPm25());
        editor.putString("aqi_qlty",aqi.getQlty());
        editor.putString("aqi_so2",aqi.getSo2());

        for (int i=0;i<7;i++){
            editor.putString("astro_sr_"+i,dailyForecast[i].getAstroSr());
            editor.putString("astro_ss_"+i,dailyForecast[i].getAstroSs());

            editor.putString("cond_code_d_"+i,dailyForecast[i].getCondCodeD());
            editor.putString("cond_txt_d_"+i,dailyForecast[i].getCondTxtD());

            editor.putString("cond_code_n_"+i,dailyForecast[i].getCondCodeN());
            editor.putString("cond_txt_n_"+i,dailyForecast[i].getCondTxtN());

            editor.putString("date_"+i,dailyForecast[i].getDate());
            editor.putString("hum_"+i,dailyForecast[i].getHum());
            editor.putString("pcpn_"+i,dailyForecast[i].getPcpn());
            editor.putString("pop_"+i,dailyForecast[i].getPop());
            editor.putString("pres_"+i,dailyForecast[i].getPres());
            editor.putString("vis_"+i,dailyForecast[i].getVis());

            editor.putString("tmp_max_"+i,dailyForecast[i].getTmpMax());
            editor.putString("tmp_min_"+i,dailyForecast[i].getTmpMin());

            editor.putString("wind_deg_"+i,dailyForecast[i].getWindDeg());
            editor.putString("wind_dir_"+i,dailyForecast[i].getWindDir());
            editor.putString("wind_sc_"+i,dailyForecast[i].getWindSc());
            editor.putString("wind_spd_"+i,dailyForecast[i].getWindSpd());
        }
            editor.putInt("hourly_length",hourlyForecasts.length);
        for (int i=0;i<hourlyForecasts.length;i++){
            editor.putString("today_date_"+i,hourlyForecasts[i].getDate());
            editor.putString("today_hum_"+i,hourlyForecasts[i].getHum());
            editor.putString("today_pop_"+i,hourlyForecasts[i].getPop());
            editor.putString("today_pres_"+i,hourlyForecasts[i].getPres());
            editor.putString("today_tmp_"+i,hourlyForecasts[i].getTmp());

            editor.putString("today_wind_deg_"+i,hourlyForecasts[i].getWindDeg());
            editor.putString("today_wind_dir_"+i,hourlyForecasts[i].getWindDir());
            editor.putString("today_wind_sc_"+i,hourlyForecasts[i].getWindSc());
            editor.putString("today_wind_spd_"+i,hourlyForecasts[i].getWindSpd());
        }

        editor.putString("suggestion_comf_brf",suggestion.getComfBrf());
        editor.putString("suggestion_comf_txt",suggestion.getComfTxt());

        editor.putString("suggestion_cw_brf",suggestion.getCwBrf());
        editor.putString("suggestion_cw_txt",suggestion.getCwTxt());

        editor.putString("suggestion_drsg_brf",suggestion.getDrsgBrf());
        editor.putString("suggestion_drsg_txt",suggestion.getDrsgTxt());

        editor.putString("suggestion_flu_brf",suggestion.getFluBrf());
        editor.putString("suggestion_flu_txt",suggestion.getFluTxt());

        editor.putString("suggestion_sport_brf",suggestion.getSportBrf());
        editor.putString("suggestion_sport_txt",suggestion.getSportTxt());

        editor.putString("suggestion_trav_brf",suggestion.getTravBrf());
        editor.putString("suggestion_trav_txt",suggestion.getTravTxt());

        editor.putString("suggestion_uv_brf",suggestion.getUvBrf());
        editor.putString("suggestion_uv_txt",suggestion.getUvTxt());

        editor.commit();
    }
}
