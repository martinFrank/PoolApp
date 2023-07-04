package com.github.martinfrank.poolapp;

import com.github.martinfrank.poolapp.data.Activator;
import com.github.martinfrank.poolapp.data.Oxygen;
import com.github.martinfrank.poolapp.data.PhChange;
import com.github.martinfrank.poolapp.data.PoolData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {

    public static JSONObject createJsonObject(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

    public static Activator createActivator(JSONObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.toString(), Activator.class);
    }

    public static Oxygen createOxygen(JSONObject response) {
        Gson gson = new Gson();
        return gson.fromJson(response.toString(), Oxygen.class);
    }

    public static PhChange createPhChange(JSONObject response) {
        Gson gson = new Gson();
        return gson.fromJson(response.toString(), PhChange.class);
    }

    public static List<PoolData> createPoolDataList(JSONArray response) {
        Type listType = new TypeToken<List<PoolData>>() {}.getType();
        return new Gson().fromJson(response.toString(), listType);
    }
}
