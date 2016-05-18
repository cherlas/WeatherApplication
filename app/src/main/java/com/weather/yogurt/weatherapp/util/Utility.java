package com.weather.yogurt.weatherapp.util;

import android.text.TextUtils;

import com.weather.yogurt.weatherapp.database.WeatherDB;
import com.weather.yogurt.weatherapp.model.CityInformation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * Created by Yogurt on 5/6/16.
 */
public class Utility {

    /*
        解析和处理服务器返回的数据
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

}
