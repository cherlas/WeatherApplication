package com.weather.yogurt.weatherapp.model;

/**
 * Created by Yogurt on 5/17/16.
 */
public class CityInformation {

    private String countryCode;
    private String countryNamePinYin;
    private String townNameChinese;
    private String cityName;
    private String provinceName;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryNamePinYin() {
        return countryNamePinYin;
    }

    public void setCountryNamePinYin(String countryNamePinYin) {
        this.countryNamePinYin = countryNamePinYin;
    }

    public String getTownNameChinese() {
        return townNameChinese;
    }

    public void setTownNameChinese(String townNameChinese) {
        this.townNameChinese = townNameChinese;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }



}
