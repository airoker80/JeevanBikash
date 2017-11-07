package com.harati.jeevanbikas.Retrofit.Interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sameer on 10/17/2017.
 */

public class HeaderInterceptor
        implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain)
            throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("appid", "hello")
                .addHeader("deviceplatform", "android")
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}