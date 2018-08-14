package com.wafer.wafertest.network;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Api {
    private Context context;
    public Api(Context context){
        this.context = context;
    }

    RequestQueue queue = Volley.newRequestQueue(context);
    String url ="http://www.google.com";

    // Request a string response from the provided URL.
    StringRequest stringRequest = new StringRequest(DownloadManager.Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    mTextView.setText("Response is: "+ response.substring(0,500));
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mTextView.setText("That didn't work!");
        }
    });

// Add the request to the RequestQueue.
queue.add(stringRequest);
}
