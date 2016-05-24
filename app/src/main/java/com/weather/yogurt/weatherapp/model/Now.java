package com.weather.yogurt.weatherapp.model;

/**
 * Created by Yogurt on 5/23/16.
 */
public class Now {
    private String condCode;//天气状况--天气代码
    private String condTxt;//天气描述
    private String fl;//体感温度
    private String hum;//湿度(%)
    private String pcpn;//降雨量(mm)
    private String pres;//气压
    private String tem;//温度
    private String vis;//能见度(km)
    private String windDeg;//风向(角度 360°)
    private String windDir;//风向(方向)
    private String windSc;//风力等级
    private String windSpd;//风速(Kmph)

    public String getCondCode() {
        return condCode;
    }

    public void setCondCode(String condCode) {
        this.condCode = condCode;
    }

    public String getCondTxt() {
        return condTxt;
    }

    public void setCondTxt(String condTxt) {
        this.condTxt = condTxt;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tem;
    }

    public void setTmp(String tem) {
        this.tem = tem;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindSc() {
        return windSc;
    }

    public void setWindSc(String windSc) {
        this.windSc = windSc;
    }

    public String getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(String windSpd) {
        this.windSpd = windSpd;
    }

}
