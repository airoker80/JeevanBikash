package com.harati.jeevanbikas.VolleyPackage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.harati.jeevanbikas.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.VolleyPackage.RequestListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sameer on 9/1/2017.
 */

public class VolleyRequestHandler {
    Context context;

    public VolleyRequestHandler(Context context) {
        this.context = context;
    }


    public void makePostRequest(String URL, final JSONObject sendObj, final RequestListener requestListener) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        final StringRequest request = new StringRequest(Request.Method.POST, JeevanBikashConfig.REQUEST_URL+URL, response -> requestListener.onSuccess(response), error -> {
            Log.d("error", "asdasdasdas");
            if (error instanceof TimeoutError) {
                Toast.makeText(context, "Time Out Error", Toast.LENGTH_SHORT).show();
            }
            if (error instanceof NoConnectionError) {
                Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
            }
            if (error instanceof AuthFailureError) {
                Toast.makeText(context, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
            }
            if (error instanceof com.android.volley.NetworkError) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
            }
            if (error instanceof com.android.volley.ServerError) {
                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
            }
            if (error instanceof com.android.volley.ParseError) {
                Toast.makeText(context, "JSON Parse Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic dXNlcjpqQiQjYUJAMjA1NA==");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return sendObj.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

        };

        requestQueue.add(request);
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

    }


}


