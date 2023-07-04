package com.github.martinfrank.poolapp.data;


public class PhChange {

    private float currentPh;
    private float poolVolume;
    private float gramsToAdd;
    private float phChange;

    public float getCurrentPh() {
        return currentPh;
    }

    public void setCurrentPh(float currentPh) {
        this.currentPh = currentPh;
    }

    public float getPoolVolume() {
        return poolVolume;
    }

    public void setPoolVolume(float poolVolume) {
        this.poolVolume = poolVolume;
    }

    public float getGramsToAdd() {
        return gramsToAdd;
    }

    public void setGramsToAdd(float gramsToAdd) {
        this.gramsToAdd = gramsToAdd;
    }

    public float getPhChange() {
        return phChange;
    }

    public void setPhChange(float phChange) {
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
