package com.weather.yogurt.weatherapp.model;

/**
 * Created by Yogurt on 5/5/16.
 */
public class Province {
    private int provinceId;
    private String provinceName;
    private String provinceCode;

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }
}
