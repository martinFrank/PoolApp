package com.github.martinfrank.poolapp;


public class PhChange {

    private String currentPh;
    private String poolVolume;
    private String gramsToAdd;
    private String phChange;

    public String getCurrentPh() {
        return currentPh;
    }

    public void setCurrentPh(String currentPh) {
        this.currentPh = currentPh;
    }

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

    public String getPhChange() {
        return phChange;
    }

    public void setPhChange(String phChange) {
        this.phChange = phChange;
    }

    @Override
    public String toString() {
        return "PhChange{" +
                "currentPh=" + currentPh +
                ", poolVolume=" + poolVolume +
                ", gramsToAdd=" + gramsToAdd +
                ", phChange=" + phChange +
                '}';
    }
}
