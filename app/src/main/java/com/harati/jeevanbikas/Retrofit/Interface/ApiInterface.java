package com.harati.jeevanbikas.Retrofit.Interface;



import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoginModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.OTPmodel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.WithDrawlResponse;

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


/**
 * Created by User on 8/9/2017.
 */

public interface ApiInterface {
    @Headers("{Authorization:Basic dXNlcjpqQiQjYUJAMjA1NA== ,Content-Type:application/json}")
    @POST("agent/login?serialno=12346")
    Call<LoginModel> authenticate(@Body RequestBody body);


//    @Headers("{Authorization:Basic dXNlcjpqQiQjYUJAMjA1NA== ,Content-Type:application/json}")
    @POST("agent/requestotp?serialno=12346")
    Call<String> sendRetrofitOtprequest(@Body RequestBody body,
                                        @Header("X-Authorization") String xAuth,
                                        @Header("Authorization") String Authorization,
                                        @Header("Content-Type") String contentType);
    @GET("agent/logout?serialno=12346")
    Call<String> sendLogoutRequest(@Header("X-Authorization") String xAuth,
                                   @Header("Authorization") String Authorization,
                                   @Header("Content-Type") String contentType);
//    Call<String> sendRetrofitOtprequest(@Body RequestBody body, @Header("X-Authorization") String xAuth);

    @POST("agent/passwordreset?serialno=12346")
    Call<String> sendResetRequest(@Body RequestBody body,
                                        @Header("X-Authorization") String xAuth,
                                        @Header("Authorization") String Authorization,
                                        @Header("Content-Type") String contentType);


    @POST("member/balance?serialno=12346")
    Call<String> sendBalanceRequest(@Body RequestBody body,
                                    @Header("X-Authorization") String xAuth,
                                    @Header("Authorization") String Authorization,
                                    @Header("Content-Type") String contentType);

    @POST("member/deposit?serialno=12346")
    Call<String> sendDepositRequest(@Body RequestBody body,
                                    @Header("X-Authorization") String xAuth,
                                    @Header("Authorization") String Authorization,
                                    @Header("Content-Type") String contentType);

    @POST("member/withdraw?serialno=12346")
    Call<WithDrawlResponse> sendWithdrawRequest(@Body RequestBody body,
                                                @Header("X-Authorization") String xAuth,
                                                @Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType);

//    @GET("member/search?serialno=12346&mobileno=M0670001")
    @GET("member/search?serialno=12346")
    Call<SearchModel> sendMemberSearchRequest(@Query("mobileno") String mobileno,
                                              @Header("X-Authorization") String xAuth,
                                              @Header("Authorization") String Authorization,
                                              @Header("Content-Type") String contentType);



}
