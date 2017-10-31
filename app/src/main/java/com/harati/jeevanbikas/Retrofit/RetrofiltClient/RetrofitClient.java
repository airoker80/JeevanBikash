package com.harati.jeevanbikas.Retrofit.RetrofiltClient;



import com.harati.jeevanbikas.Retrofit.Interceptor.HeaderInterceptor;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 8/9/2017.
 */

public class RetrofitClient {
    private static final String BASE_URL = "http://103.1.94.77:8087/agentbank/";
    private static final String APP_URL = "api/v1/";
    private static final String ROOT_URL = BASE_URL + APP_URL;

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
// add your other interceptors …
// add logging as last interceptor
        httpClient.addInterceptor(logging);
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
//                .client(httpClient.build())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiInterface getApiService() {
        return getRetrofitInstance().create(ApiInterface.class);
    }
}
