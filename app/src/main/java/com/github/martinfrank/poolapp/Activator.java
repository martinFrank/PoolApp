package com.github.martinfrank.poolapp;

public class Activator {

    private String poolVolume;
    private String millilitersToAdd;

    public String getPoolVolume() {
        return poolVolume;
    }

    public void setPoolVolume(String poolVolume) {
        this.poolVolume = poolVolume;
    }

    public String getMillilitersToAdd() {
        return millilitersToAdd;
    }

    public void setMillilitersToAdd(String millilitersToAdd) {
        this.millilitersToAdd = millilitersToAdd;
    }

    @Override
    public String toString() {
        return "Activator{" +
                "poolVolume='" + poolVolume + '\'' +
                ", millilitersToAdd='" + millilitersToAdd + '\'' +
                '}';
    }
}
