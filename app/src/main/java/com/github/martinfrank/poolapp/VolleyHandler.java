package com.github.martinfrank.poolapp;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        String url = "http://192.168.0.100:8100/pooldata/latest";
        JsonArrayRequest request = new JsonArrayRequest(url, response -> {
            Log.d(LOG_TAG, response.toString());
            try {
                List<PoolData> poolDataList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonobject = response.getJSONObject(i);
                    PoolData poolData = new PoolData();
                    poolData.setDate(jsonobject.getString("date"));
                    poolData.setVolume(jsonobject.getString("volume"));
                    poolData.setTemperature(jsonobject.getString("temperature"));
                    poolData.setPh(jsonobject.getString("ph"));
                    poolData.setPhChanger(jsonobject.getString("phChanger"));
                    poolData.setOxygen(jsonobject.getString("oxygen"));
                    poolData.setActivator(jsonobject.getString("activator"));
                    poolDataList.add(poolData);
                }
                Log.d(LOG_TAG, "poolDataList: " + poolDataList);
                restDataReceiver.updatePoolData(poolDataList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Log.d("error", error.toString());
            restDataReceiver.showToast("error loading pool data from server");

        });
        queue.add(request);
    }

    public void loadPhChangerTemplate(float volume, float ph) {
        Log.d(LOG_TAG, "loadPhChangerTemplate");
        String url = "http://192.168.0.100:8100/maths/phminus";

        // Make new json object and put params in it

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("currentPh", ph);
            jsonObject.put("poolVolume", volume);
        } catch (JSONException e) {
            //
        }
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            try {
                PhChange phChange = new PhChange();
                phChange.setCurrentPh(response.getString("currentPh"));
                phChange.setPoolVolume(response.getString("poolVolume"));
                phChange.setGramsToAdd(response.getString("gramsToAdd"));
                phChange.setPhChange(response.getString("phChange"));

                // on below line we are setting this string s to our text view.
                restDataReceiver.updatePhChange(phChange);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // method to handle errors.
            restDataReceiver.showToast("error loading template for ph");
        });
        // below line is to make
        // a json object request.
        queue.add(request);

    }

    public void loadOxygenTemplate(float volume) {
        Log.d(LOG_TAG, "loadOxygenTemplate");
        String url = "http://192.168.0.100:8100/maths/oxygen";

        // Make new json object and put params in it

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("poolVolume", volume);
        } catch (JSONException e) {
            //
        }
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            try {
                Oxygen oxygen = new Oxygen();
                oxygen.setPoolVolume(response.getString("poolVolume"));
                oxygen.setGramsToAdd(response.getString("gramsToAdd"));

                // on below line we are setting this string s to our text view.
                restDataReceiver.updateOxygen(oxygen);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // method to handle errors.
            restDataReceiver.showToast("error loading template for ph");
        });
        // below line is to make
        // a json object request.
        queue.add(request);

    }

    public void loadActivatorTemplate(float volume) {
        Log.d(LOG_TAG, "loadActivatorTemplate");
        String url = "http://192.168.0.100:8100/maths/activator";

        // Make new json object and put params in it

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("poolVolume", volume);
        } catch (JSONException e) {
            //
        }
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            try {
                Activator activator = new Activator();
                activator.setPoolVolume(response.getString("poolVolume"));
                activator.setMillilitersToAdd(response.getString("millilitersToAdd"));

                // on below line we are setting this string s to our text view.
                restDataReceiver.updateActivator(activator);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // method to handle errors.
            restDataReceiver.showToast("error loading template for ph");
        });
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void insertOrUpdate(PoolData poolData) {
        String url = "http://192.168.0.100:8100/pooldata/update";

        // Make new json object and put params in it

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("date", poolData.getDate());
            jsonObject.put("volume", poolData.getVolume());
            jsonObject.put("temperature", poolData.getTemperature());
            jsonObject.put("ph", poolData.getPh());
            jsonObject.put("phChanger", poolData.getPhChanger());
            jsonObject.put("oxygen", poolData.getOxygen());
            jsonObject.put("activator", poolData.getActivator());
        } catch (JSONException e) {
            //
        }
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            restDataReceiver.showToast("update success :-)");
        }, error -> {
            // method to handle errors.
            restDataReceiver.showToast("error updating pool data");
        });
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void delete(PoolData poolData) {
        String url = "http://192.168.0.100:8100/pooldata/delete";

        // Make new json object and put params in it

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("date", poolData.getDate());
            jsonObject.put("volume", poolData.getVolume());
            jsonObject.put("temperature", poolData.getTemperature());
            jsonObject.put("ph", poolData.getPh());
            jsonObject.put("phChanger", poolData.getPhChanger());
            jsonObject.put("oxygen", poolData.getOxygen());
            jsonObject.put("activator", poolData.getActivator());
        } catch (JSONException e) {
            //
        }
        Log.d(LOG_TAG, "json-object: " + jsonObject);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d(LOG_TAG, "result: " + response);
            restDataReceiver.showToast("delete success :-)");
        }, error -> {
            // method to handle errors.
            Log.d(LOG_TAG, "error: " + error);
            if (error.toString().startsWith("com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of")) {
                restDataReceiver.showToast("delete success :-)");
            } else {
                restDataReceiver.showToast("error deleting pool data");
            }
        });

        // below line is to make
        // a json object request.
        queue.add(request);
    }
}
