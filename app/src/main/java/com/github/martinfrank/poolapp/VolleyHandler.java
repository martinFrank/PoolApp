package com.github.martinfrank.poolapp;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.martinfrank.poolapp.data.Activator;
import com.github.martinfrank.poolapp.data.Oxygen;
import com.github.martinfrank.poolapp.data.PhChange;
import com.github.martinfrank.poolapp.data.PoolData;
import org.json.JSONObject;

import java.util.List;

public class VolleyHandler {

    private static final String LOG_TAG = "com.github.martinfrank.poolapp.MainActivity";
    private final RequestQueue queue;
    private final RestDataReceiver restDataReceiver;

    public VolleyHandler(RequestQueue queue, RestDataReceiver restDataReceiver) {
        this.queue = queue;
        this.restDataReceiver = restDataReceiver;
    }

    public void loadPoolData() {
        String url = "http://192.168.0.100:8100/poolserver/api/pooldata/latest";
        JsonArrayRequest request = new JsonArrayRequest(url, response -> {
            Log.d(LOG_TAG, response.toString());
            List<PoolData> poolDataList = JsonUtil.createPoolDataList(response);
            Log.d(LOG_TAG, "poolDataList: " + poolDataList);
            restDataReceiver.updatePoolData(poolDataList);
        }, error -> {
            Log.d("error", error.toString());
            restDataReceiver.showToast("error loading pool data from server");
        });
        queue.add(request);
    }

    public void loadPhChangerTemplate(float volume, float ph) {
        Log.d(LOG_TAG, "loadPhChangerTemplate");
        String url = "http://192.168.0.100:8100/poolserver/api/maths/phminus";

        PhChange requestPhChange = new PhChange();
        requestPhChange.setPoolVolume(volume);
        requestPhChange.setCurrentPh(ph);
        JSONObject jsonObject = JsonUtil.createJsonObject(requestPhChange);
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            PhChange phChange = JsonUtil.createPhChange(response);
            restDataReceiver.updatePhChange(phChange);
        }, error -> restDataReceiver.showToast("error loading template for ph"));
        queue.add(request);

    }

    public void loadOxygenTemplate(float volume) {
        Log.d(LOG_TAG, "loadOxygenTemplate");
        String url = "http://192.168.0.100:8100/poolserver/api/maths/oxygen";

        Oxygen requestOxygen = new Oxygen();
        requestOxygen.setPoolVolume(Float.toString(volume));
        JSONObject jsonObject = JsonUtil.createJsonObject(requestOxygen);
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            Oxygen oxygen = JsonUtil.createOxygen(response);
            restDataReceiver.updateOxygen(oxygen);
        }, error -> restDataReceiver.showToast("error loading template for ph"));
        queue.add(request);

    }

    public void loadActivatorTemplate(float volume) {
        Log.d(LOG_TAG, "loadActivatorTemplate");
        String url = "http://192.168.0.100:8100/poolserver/api/maths/activator";

        Activator requestActivator = new Activator();
        requestActivator.setPoolVolume(Float.toString(volume));
        JSONObject jsonObject = JsonUtil.createJsonObject(requestActivator);
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            Activator activator = JsonUtil.createActivator(response);
            restDataReceiver.updateActivator(activator);
        }, error -> restDataReceiver.showToast("error loading template for ph"));
        queue.add(request);
    }

    public void insertOrUpdate(PoolData poolData) {
        String url = "http://192.168.0.100:8100/poolserver/api/pooldata/update";
        JSONObject jsonObject = JsonUtil.createJsonObject(poolData);
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            restDataReceiver.showToast("update success :-)");
        }, error -> restDataReceiver.showToast("error updating pool data"));
        queue.add(request);
    }

    public void delete(PoolData poolData) {
        String url = "http://192.168.0.100:8100/poolserver/api/pooldata/delete";
        JSONObject jsonObject = JsonUtil.createJsonObject(poolData);
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            restDataReceiver.showToast("delete success :-)");
        }, error -> {
            if (error.toString().startsWith("com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of")) {
                restDataReceiver.showToast("delete success :-)");
            } else {
                Log.d(LOG_TAG, "error: " + error);
                restDataReceiver.showToast("error deleting pool data");
            }
        });
        queue.add(request);
    }

}
