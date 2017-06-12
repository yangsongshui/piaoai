package com.example.yangsong.piaoai.model;

/**
 * Created by yangsong on 2017/5/14.
 */
public class Facility {
    int type;
    String name;
    int tvoc;
    int CO2;
    int PM10;
    int electric;
    int humidity;
    int methanal;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTvoc() {
        return tvoc;
    }

    public void setTvoc(int tvoc) {
        this.tvoc = tvoc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCO2() {
        return CO2;
    }

    public void setCO2(int CO2) {
        this.CO2 = CO2;
    }

    public int getPM10() {
        return PM10;
    }

    public void setPM10(int PM10) {
        this.PM10 = PM10;
    }

    public int getElectric() {
        return electric;
    }

    public void setElectric(int electric) {
        this.electric = electric;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getMethanal() {
        return methanal;
    }

    public void setMethanal(int methanal) {
        this.methanal = methanal;
    }
}
