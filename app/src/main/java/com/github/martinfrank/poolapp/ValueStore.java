package com.github.martinfrank.poolapp;

import android.content.SharedPreferences;
import android.util.Log;

public class ValueStore {

    private static final String LOG_TAG = "com.github.martinfrank.poolapp.ValueStore";

    private final SharedPreferences sharedPreferences;
    private static final String HEIGHT = "com.github.martinfrank.poolapp.ValueStore.height";

    public ValueStore(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void storeHeight(String height) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d(LOG_TAG, "store String value="+height);
        editor.putString(HEIGHT, height);
        editor.apply();
    }


    public String loadHeight() {
        String value = sharedPreferences.getString(HEIGHT, "0");
        Log.d(LOG_TAG, "load String value="+value);
        return value;
    }

}
