package com.harati.jeevanbikas.Retrofit.RetrofiltClient;


import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sameer on 8/9/2017.
 */

public class LoginRetrofitClient {
    private static final String ROOT_URL = "http://192.168.1.138:8080/pms/api/v1/androidApp/";
//    private static final String ROOT_URL = "http://192.168.1.163:8080/pms/api/v1/androidApp/";

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiInterface getLoginApiService() {
        return getRetrofitInstance().create(ApiInterface.class);
    }
}
