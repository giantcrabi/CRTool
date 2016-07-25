package com.kreators.crtoolv1.Network;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 26/06/2016.
 */
public abstract class VolleyRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    private final String TAG = getClass().getCanonicalName();

    private String url;
    private Map<String, Object> params;
    private VolleyListener listener;

    public abstract JsonArrayRequest generateRequest();

    public VolleyRequest(String url, Object... args) {
        this.url = String.format(url, args);
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public boolean hasParams() {
        return params != null;
    }

    public void setListener(VolleyListener listener) {
        this.listener = listener;
    }

    public void putParams(String key, Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (listener != null) {
            String message = "Something wrong happened, please try again";

            if (error instanceof TimeoutError) {
                message = "Timeout exceeded";
            } else if (error instanceof NoConnectionError) {
                message = "No internet connection";
            } else if (error instanceof ParseError) {
                message = "Parse Error";
            } else if (error instanceof ServerError) {
                message = "Server Error";
            }

            listener.onError(this, message);
        } else {
            Log.e(TAG, "onErrorResponse: Listener is null");
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        if (listener != null) {
            listener.onSuccess(this, response);
        } else {
            Log.e(TAG, "onResponse: Listener is null");
        }
    }
}
