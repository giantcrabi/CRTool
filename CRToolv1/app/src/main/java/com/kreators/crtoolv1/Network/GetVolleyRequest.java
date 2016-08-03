package com.kreators.crtoolv1.Network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 26/06/2016.
 */
public class GetVolleyRequest extends VolleyRequest {

    private final String TAG = getClass().getCanonicalName();

    public GetVolleyRequest(String url) {
        super(url);
    }

    @Override
    public JsonArrayRequest generateGetRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append(getUrl());
        if (hasParams()) {
            builder.append("/");
            for (Map.Entry<String, Object> entry : getParameters().entrySet()) {
                builder.append(entry.getKey());
                builder.append("/");
                builder.append(entry.getValue());
                builder.append("/");
            }
        }
        return new JsonArrayRequest(Request.Method.GET, builder.toString(), null, GetVolleyRequest.this, GetVolleyRequest.this);
    }

    @Override
    public StringRequest generatePostRequest(final VolleyStringListener listener) {
        StringBuilder builder = new StringBuilder();
        builder.append(getUrl());
        return new StringRequest(Request.Method.POST, builder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(listener != null) {
                            listener.onSuccess(response);
                        } else {
                            Log.e(TAG, "onResponse: Listener is null");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(listener != null) {
                    String message = "Something wrong happened, please try again";

                    if (error instanceof TimeoutError) {
                        message = "Timeout exceeded";
                    } else if (error instanceof NoConnectionError) {
                        message = "No internet connection";
                    } else if (error instanceof ParseError) {
                        message = error.getMessage();
                    } else if (error instanceof ServerError) {
                        message = error.getMessage();
                    }

                    listener.onError(message);
                } else {
                    Log.e(TAG, "onErrorResponse: Listener is null");
                }
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (hasParams()) {
                    for (Map.Entry<String, Object> entry : getParameters().entrySet()) {
                        params.put(entry.getKey(), (String) entry.getValue());
                    }
                }
                return params;
            }
        };
    }


}
