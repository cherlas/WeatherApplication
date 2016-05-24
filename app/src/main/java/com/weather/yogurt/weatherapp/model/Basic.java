package com.weather.yogurt.weatherapp.model;

/**
 * Created by Yogurt on 5/23/16.
 */
public class Basic {
    private String city;
    private String cnty;
    private String id;
    private String lat;//维度
    private String lon;//经度
    private String updateLoc;
    private String updateUtc;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getUpdateLoc() {
        return updateLoc;
    }

    public void setUpdateLoc(String updateLoc) {
        this.updateLoc = updateLoc;
    }

    public String getUpdateUtc() {
        return updateUtc;
    }

    public void setUpdateUtc(String updateUtc) {
        this.updateUtc = updateUtc;
    }
}
