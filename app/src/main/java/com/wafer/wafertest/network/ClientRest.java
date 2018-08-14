package com.wafer.wafertest.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class RestClient {
    private final RequestQueue queue;

    public RestClient(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }


    public JsonObjectRequest createJsonRequest(final int method, final String url,
                                               final JSONObject params,
                                               final Response.Listener<JSONObject> onResponse,
                                               final Response.ErrorListener onError) {
        JsonObjectRequest request = new JsonObjectRequest(method, url, params, onResponse, onError);
        request.setShouldCache(false);
        return request;
    }


    public JsonRequest<JSONObject> getMessages() {
        HashMap<String, String> params = new HashMap<String, String>();
        JsonRequest<JSONObject> request = createParamRequest(Request.Method.GET, "/api/database/messages", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.v("RestClient", jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("RestClient", volleyError.getMessage());
            }
        });
        queue.add(request);
        return request;
    }

    public static abstract class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                try {
                    String responseBody = new String(volleyError.networkResponse.data, "utf-8");
                    JSONObject jsonObject = new JSONObject(responseBody);
                    onJsonResult(volleyError, jsonObject);
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException error) {
                    error.printStackTrace();
                }
            }
            onNetworkError(volleyError);
        }

        public abstract void onNetworkError(VolleyError error);

        public abstract void onJsonResult(VolleyError error, JSONObject errorResult);
    }

    public static class ErrorToater extends ErrorListener {
        Context context;

        public ErrorToater(Context context) {
            this.context = context;
        }

        @Override
        public void onNetworkError(VolleyError error) {
            showToast(error.getLocalizedMessage());
        }

        @Override
        public void onJsonResult(VolleyError error, JSONObject errorResult) {
            String message = error.getLocalizedMessage();
            try {
                message = errorResult.getString("message");
            } catch (JSONException e) {
            }
            showToast(message);
        }

        protected void showToast(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}

