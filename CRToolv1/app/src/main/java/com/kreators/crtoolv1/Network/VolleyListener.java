package com.kreators.crtoolv1.Network;

import org.json.JSONArray;

/**
 * Created by DELL on 26/06/2016.
 */
public interface VolleyListener {
    void onSuccess(VolleyRequest request, JSONArray result);
    void onError(VolleyRequest request, String errorMessage);
}
