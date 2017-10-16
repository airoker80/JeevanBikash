package com.harati.jeevanbikas.Volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Login.LoginActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sameer on 9/1/2017.
 */

public class VolleyRequestHandler {
    Context context;
    SessionHandler sessionHandler;
    String session_token;
    public VolleyRequestHandler(Context context) {
        this.context = context;
    }
    private long mRequestStartTime;

    public void makePostRequest(final String URL, final JSONObject sendObj, final RequestListener requestListener) {
        try {
            sessionHandler = new SessionHandler(context);
            session_token = sessionHandler.getAgentToken();
        }catch (Exception e){
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        mRequestStartTime = System.currentTimeMillis();
        final StringRequest request = new StringRequest(Request.Method.POST, JeevanBikashConfig.REQUEST_URL+URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                Log.e("url",JeevanBikashConfig.REQUEST_URL+URL);
                Log.e("sendobj",sendObj.toString());
                Log.e("time","taken"+String.valueOf(totalRequestTime));
                requestListener.onSuccess(response);


            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                Log.e("time","taken"+String.valueOf(totalRequestTime));
                Log.e("url",JeevanBikashConfig.REQUEST_URL+URL);
                Log.e("sendobj",sendObj.toString());

                requestListener.onFailure(String.valueOf(error));
                Log.d("error", "asdasdasdas");
                if (error instanceof TimeoutError) {
                    Toast.makeText(context, "Time Out Error", Toast.LENGTH_SHORT).show();
                }
                if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
                }
                if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show();
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

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
//                Log.d("token-----", "---" +session_token);
                headers.put("Content-Type","application/json");
//                headers.put("X-Authorization","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBMDA1MDAwMSIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTUwNzcyNDc0MDU1MiwiZXhwIjoxNTA4MzI5NTQwfQ.IHfG2LjPt2oo2Cu1pwhzYJ4TsqRpYkC8BXx0Ldz5-_oYxwMClDcba-r3gO4Fgy9WmV5Kbb5-25UfPSy2i5sRzg");
                if (context instanceof LoginActivity){
                    headers.put("Authorization", "Basic dXNlcjpqQiQjYUJAMjA1NA==");
                }else {
                    headers.put("X-Authorization",session_token);
                    headers.put("Authorization", "Basic dXNlcjpqQiQjYUJAMjA1NA==");
                }
                Log.d("header-----", "---" +headers.toString());
                return headers;
            }

            @Override
            public byte[] getBody() {
                return sendObj.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {

                return "application/json";
//                return "application/x-www-form-urlencoded";
            }

        };

        requestQueue.add(request);
        /*request.setRetryPolicy(new RetryPolicy() {
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
        });*/

    }

    public void makeLogoutRequest(final String URL, final RequestListener requestListener) {
        try {
            sessionHandler = new SessionHandler(context);
            session_token = sessionHandler.getAgentToken();
        }catch (Exception e){
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        final StringRequest request = new StringRequest(Request.Method.GET, JeevanBikashConfig.REQUEST_URL+URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("url",JeevanBikashConfig.REQUEST_URL+URL);
                requestListener.onSuccess(response);

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("url",JeevanBikashConfig.REQUEST_URL+URL);
                requestListener.onFailure(String.valueOf(error));
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
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("X-Authorization",session_token);
                headers.put("Authorization", "Basic dXNlcjpqQiQjYUJAMjA1NA==");
                Log.d("header-----", "---" +headers.toString());
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(request);
    }
}


