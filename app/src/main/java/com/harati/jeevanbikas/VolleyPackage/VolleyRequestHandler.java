package com.harati.jeevanbikas.VolleyPackage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.harati.jeevanbikas.Helper.AutoCompleteHelper.AutoCompleteModel;
import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.JeevanBikashConfig.JeevanBikashConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sameer on 9/1/2017.
 */

public class VolleyRequestHandler {
    Context context;

    public VolleyRequestHandler(Context context) {
        this.context = context;
    }

    String jsonRespose;


    public String makePostRequest(String URL, final List<HelperListModelClass> helperListModelClasses) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        jsonRespose=response;
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        jsonRespose=error.toString();
                    }
                }
        )


        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                for (int i =0;i<helperListModelClasses.size();i++){
                    String key = helperListModelClasses.get(0).getKey();
                    String value = helperListModelClasses.get(0).getValue();
                    map.put(key,value);
                }
                return map;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return  jsonRespose;
    }
}
