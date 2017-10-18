package com.harati.jeevanbikas.Retrofit.Interface;



import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoginModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.OTPmodel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

/**
 * Created by User on 8/9/2017.
 */

public interface ApiInterface {
    @Headers("{Authorization:Basic dXNlcjpqQiQjYUJAMjA1NA== ,Content-Type:application/json}")
    @POST("login?serialno=12346")
    Call<LoginModel> authenticate(@Body RequestBody body);


//    @Headers("{Authorization:Basic dXNlcjpqQiQjYUJAMjA1NA== ,Content-Type:application/json}")
    @POST("requestotp?serialno=12346")
    Call<String> sendRetrofitOtprequest(@Body RequestBody body,
                                        @Header("X-Authorization") String xAuth,
                                        @Header("Authorization") String Authorization,
                                        @Header("Content-Type") String contentType);
//    Call<String> sendRetrofitOtprequest(@Body RequestBody body, @Header("X-Authorization") String xAuth);

    @POST("passwordreset?serialno=12346")
    Call<String> sendResetRequest(@Body RequestBody body,
                                        @Header("X-Authorization") String xAuth,
                                        @Header("Authorization") String Authorization,
                                        @Header("Content-Type") String contentType);
}
