package com.kreators.crtoolv1.Network;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.TimeUnit;

/**
 * Created by DELL on 26/06/2016.
 */
public class VolleyManager {
    private final static int DEFAULT_SOCKET_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(3);
    private final static String DEFAULT_TAG = "CRToolVolley";
    private final String TAG = getClass().getCanonicalName();

    private static VolleyManager instance = null;
    private RequestQueue requestQueue;

    private VolleyManager(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    public static VolleyManager getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyManager(context);
        }
        return instance;
    }

    public void createRequest(VolleyRequest volleyRequest, String tag) {
        JsonArrayRequest request = null;
        if(tag.equals("GET")) {
            request = volleyRequest.generateGetRequest();
        } else if(tag.equals("POST")) {
            request = volleyRequest.generatePostRequest();
        }

        if(request != null){
            request.setRetryPolicy(new DefaultRetryPolicy(
                    30000, //30 seconds for request timeout
                    0, //no retry
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.setTag(tag);
            request.setShouldCache(false);
            requestQueue.add(request);
            Log.d("","");
        }
    }

    public void cancelPendingRequests(String tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
