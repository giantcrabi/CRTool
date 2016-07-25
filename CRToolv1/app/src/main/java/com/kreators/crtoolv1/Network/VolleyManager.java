package com.kreators.crtoolv1.Network;

import android.content.Context;

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
    private final static String REQUEST_TAG = "CRToolVolley";
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

    public void createRequest(VolleyRequest volleyRequest) {
        JsonArrayRequest request = volleyRequest.generateRequest();
        request.setRetryPolicy(new DefaultRetryPolicy(
                DEFAULT_SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(REQUEST_TAG);
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void cancelPendingRequests(String tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
