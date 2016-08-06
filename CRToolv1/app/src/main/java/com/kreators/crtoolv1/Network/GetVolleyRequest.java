package com.kreators.crtoolv1.Network;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
            for (Map.Entry<String, String> entry : getParameters().entrySet()) {
                builder.append(entry.getKey());
                builder.append("/");
                builder.append(entry.getValue());
                builder.append("/");
            }
        }
        return new JsonArrayRequest(Request.Method.GET, builder.toString(), null, GetVolleyRequest.this, GetVolleyRequest.this);
    }

    @Override
    public JsonArrayRequest generatePostRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append(getUrl());
        return new JsonArrayRequest(Request.Method.POST, builder.toString(), null, GetVolleyRequest.this, GetVolleyRequest.this)
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                Map<String, String> params = getParameters();
                if (params != null && params.size() > 0) {
                    JSONObject jsonObject = new JSONObject(params);
                    String requestBody = jsonObject.toString();
                    try {
                        return requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                requestBody, "utf-8");
                        return null;
                    }
                }
                return null;
            }
        };
    }

}
