package com.github.martinfrank.poolapp;

import android.content.SharedPreferences;
import android.util.Log;

public class ValueStore {

    private static final String LOG_TAG = "com.github.martinfrank.poolapp.ValueStore";

    private final SharedPreferences sharedPreferences;
    private static final String HEIGHT = "com.github.martinfrank.poolapp.ValueStore.height";
    private static final String DIAMETER = "com.github.martinfrank.poolapp.ValueStore.diameter";

    public ValueStore(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void storeHeight(float height) {
        storeFloat(HEIGHT, height);
    }

    public void storeDiameter(float diameter) {
        storeFloat(DIAMETER, diameter);
    }

    public float loadHeight() {
        return restoreFloat(HEIGHT);
    }

    public float loadDiameter() {
        return restoreFloat(DIAMETER);
    }

    private void storeFloat(String identifier, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d(LOG_TAG, "store float value="+value);
        editor.putFloat(identifier, value);
        editor.apply();
    }

    private float restoreFloat(String identifier) {
        float value = sharedPreferences.getFloat(identifier, 0f);
        Log.d(LOG_TAG, "load float value="+value);
        return value;

    }
}
