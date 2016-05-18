package com.weather.yogurt.weatherapp.model;

/**
 * Created by Yogurt on 5/5/16.
 */
public class Country {
    private String countryCode;
    private String countryNamePinYin;
    private String countryNameChinese;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryNamePinYin(String countryNamePinYin) {
        this.countryNamePinYin = countryNamePinYin;
    }

    public String getCountryNamePinYin() {
        return countryNamePinYin;
    }

    public void setCountryNameChinese(String countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
    }

    public String getCountryNameChinese() {
        return countryNameChinese;
    }
}

