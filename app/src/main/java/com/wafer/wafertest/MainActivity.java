package com.wafer.wafertest;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.wafer.wafertest.model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onStart(){
        super.onStart();
        try {
            showDialog();
            getCountry();
        }
        catch(JSONException e){
            hideDialog();
            Toast.makeText(getBaseContext(), R.string.error, Toast.LENGTH_LONG).show();
        }
    }

    public void showDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    public void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void getCountry() throws JSONException{
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Request jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://restcountries.eu/rest/v2/all",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        List<Country> countryArray = new ArrayList<Country>();
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject country = response.getJSONObject(i);

                                // Get the current country (json object) data
                                String name = country.getString("name");

                                JSONArray currencies = country.getJSONArray("currencies");
                                String currency = currencies.getJSONObject(0).getString("name");

                                JSONArray languages = country.getJSONArray("languages");
                                String language = languages.getJSONObject(0).getString("name");

                                // Add country to list
                                countryArray.add(new Country(name, currency, language));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        hideDialog();
                        mRecyclerView.setAdapter(null);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        hideDialog();
                        Toast.makeText(getBaseContext(), R.string.error, Toast.LENGTH_LONG).show();
                    }
                }

        );
        requestQueue.add(jsonArrayRequest);

    }
}
