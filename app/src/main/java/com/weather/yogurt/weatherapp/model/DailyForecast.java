package com.weather.yogurt.weatherapp.model;

/**
 * Created by Yogurt on 5/23/16.
 */
public class DailyForecast {
    private String astroSr;//日出时间
    private String astroSs;//日落时间
    private String condCodeD;//白天代码
    private String condTxtD;//白天天气状况描述
    private String condCodeN;//夜间天气代码
    private String condTxtN;//夜间天气状况
    private String date;//预报日期
    private String hum;//相对湿度（%）
    private String pcpn;//降水量(mm)
    private String pop;//降水概率
    private String pres;//气压
    private String tmpMax;
    private String tmpMin;//最高最低温度;
    private String vis;//能见度(km)
    private String windDeg;//风向(角度 360°)
    private String windDir;//风向(方向)
    private String windSc;//风力等级
    private String windSpd;//风速(Kmph)

    public String getAstroSr() {
        return astroSr;
    }

    public void setAstroSr(String astroSr) {
        this.astroSr = astroSr;
    }

    public String getAstroSs() {
        return astroSs;
    }

    public void setAstroSs(String astroSs) {
        this.astroSs = astroSs;
    }

    public String getCondCodeD() {
        return condCodeD;
    }

    public void setCondCodeD(String condCodeD) {
        this.condCodeD = condCodeD;
    }

    public String getCondTxtD() {
        return condTxtD;
    }

    public void setCondTxtD(String condTxtD) {
        this.condTxtD = condTxtD;
    }

    public String getCondCodeN() {
        return condCodeN;
    }

    public void setCondCodeN(String condCodeN) {
        this.condCodeN = condCodeN;
    }

    public String getCondTxtN() {
        return condTxtN;
    }

    public void setCondTxtN(String condTxtN) {
        this.condTxtN = condTxtN;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmpMax() {
        return tmpMax;
    }

    public void setTmpMax(String tmpMax) {
        this.tmpMax = tmpMax;
    }

    public String getTmpMin() {
        return tmpMin;
    }

    public void setTmpMin(String tmpMin) {
        this.tmpMin = tmpMin;
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
