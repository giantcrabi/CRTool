package com.kreators.crtoolv1.Network;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import java.util.Map;

/**
 * Created by DELL on 26/06/2016.
 */
public class GetVolleyRequest extends VolleyRequest {

    public GetVolleyRequest(String url) {
        super(url);
    }

    @Override
    public JsonArrayRequest generateGetRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append(getUrl());
        if (hasParams()) {
            builder.append("/");
            for (Map.Entry<String, Object> entry : getParams().entrySet()) {
                builder.append(entry.getKey());
                builder.append("/");
                builder.append(entry.getValue());
                builder.append("/");
            }
        }
        return new JsonArrayRequest(Request.Method.GET, builder.toString(), null, GetVolleyRequest.this, GetVolleyRequest.this);
    }
}
