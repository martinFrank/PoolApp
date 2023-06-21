package com.github.martinfrank.poolapp;


import java.text.SimpleDateFormat;

public class PoolData {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String date;

    private String ph;

    private String phChanger;

    private String temperature;

    private String volume;

    private String oxygen;

    private String activator;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getPhChanger() {
        return phChanger;
    }

    public void setPhChanger(String phChanger) {
        this.phChanger = phChanger;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getOxygen() {
        return oxygen;
    }

    public void setOxygen(String oxygen) {
        this.oxygen = oxygen;
    }

    public String getActivator() {
        return activator;
    }

    public void setActivator(String activator) {
        this.activator = activator;
    }

    @Override
    public String toString() {
        return "PoolData{" +
                "date='" + date + '\'' +
                ", ph='" + ph + '\'' +
                ", phChanger='" + phChanger + '\'' +
                ", temperature='" + temperature + '\'' +
                ", volume='" + volume + '\'' +
                ", oxygen='" + oxygen + '\'' +
                ", activator='" + activator + '\'' +
                '}';
    }
}
