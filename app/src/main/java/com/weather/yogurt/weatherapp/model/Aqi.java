package com.weather.yogurt.weatherapp.model;

/**
 * Created by Yogurt on 5/23/16.
 * 空气质量
 */
public class Aqi {
    private String aqi;//空气质量指数
    private String co;//一氧化碳1小时平均值(ug/m³)
    private String no2;//二氧化氮1小时平均值(ug/m³)
    private String o3;//臭氧1小时平均值(ug/m³)
    private String pm10;//PM10 1小时平均值(ug/m³)
    private String pm25;//PM2.5 1小时平均值(ug/m³)
    private String qlty;//空气质量类别
    private String so2;//二氧化硫1小时平均值(ug/m³)

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

}
