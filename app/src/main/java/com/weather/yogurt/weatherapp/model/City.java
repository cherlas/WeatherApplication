package com.weather.yogurt.weatherapp.model;

/**
 * Created by Yogurt on 5/5/16.
 */
public class City {
    private int cityId;
    private String cityName;
    private String cityCode;
    private int provinceId;
    private String cityPostCode;
    private String cityTelAreaCode;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityPostCode() {
        return cityPostCode;
    }

    public void setCityPostCode(String cityPostCode) {
        this.cityPostCode = cityPostCode;
    }

    public String getCityTelAreaCode() {
        return cityTelAreaCode;
    }

    public void setCityTelAreaCode(String cityTelAreaCode) {
        this.cityTelAreaCode = cityTelAreaCode;
    }

}
