package com.github.martinfrank.poolapp;

public class Oxygen {

    private String poolVolume;
    private String gramsToAdd;
    private String oxygenChange;

    public String getPoolVolume() {
        return poolVolume;
    }

    public void setPoolVolume(String poolVolume) {
        this.poolVolume = poolVolume;
    }

    public String getGramsToAdd() {
        return gramsToAdd;
    }

    public void setGramsToAdd(String gramsToAdd) {
        this.gramsToAdd = gramsToAdd;
    }

    public String getOxygenChange() {
        return oxygenChange;
    }

    public void setOxygenChange(String oxygenChange) {
        this.oxygenChange = oxygenChange;
    }

    @Override
    public String toString() {
        return "Oxygen{" +
                "poolVolume='" + poolVolume + '\'' +
                ", gramsToAdd='" + gramsToAdd + '\'' +
                ", oxygenChange='" + oxygenChange + '\'' +
                '}';
    }
}
