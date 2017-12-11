package com.harati.jeevanbikas.Retrofit.Interface;


import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.CastModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoanDetailsModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoginModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.OTPmodel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SystemApiResponseModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.TransferModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.WithDrawlResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Observable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by User on 8/9/2017.
 */

public interface ApiInterface {

    @Headers("{Authorization:Basic dXNlcjpqQiQjYUJAMjA1NA== ,Content-Type:application/json}")
    @POST
    Call<LoginModel> authenticate(@Body RequestBody body, @Url String url, @Query("serialno") String serialno);


    @POST("systemapi")
    Call<List<SystemApiResponseModel>> callSystem(@Body RequestBody body, @Query("serialno") String serialno);

    @POST("systemapi")
    Call<ResponseBody> callSystemApi(@Body RequestBody body, @Query("serialno") String serialno);


    @POST
    Call<OTPmodel> sendRetrofitOtprequest(@Url String url,
                                          @Body RequestBody body,
                                          @Header("X-Authorization") String xAuth,
                                          @Header("Authorization") String Authorization,
                                          @Header("Content-Type") String contentType,
                                          @Query("serialno") String serialno);

    @POST
    Call<SuccesResponseModel> sendEnrollmentRequest(@Url String url,
                                                    @Body RequestBody body,
                                                    @Header("X-Authorization") String xAuth,
                                                    @Header("Authorization") String Authorization,
                                                    @Header("Content-Type") String contentType,
                                                    @Query("serialno") String serialno);

    //    @GET("agent/logout")
    @GET("agent/logout")
    io.reactivex.Observable<SuccesResponseModel> sendLogoutRequest(@Header("X-Authorization") String xAuth,
                                                @Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType,
                                                @Query("serialno") String serialno);
//    Call<String> sendRetrofitOtprequest(@Body RequestBody body, @Header("X-Authorization") String xAuth);

    @POST
    Call<String> sendResetRequest(@Url String url,
                                  @Body RequestBody body,
                                  @Header("X-Authorization") String xAuth,
                                  @Header("Authorization") String Authorization,
                                  @Header("Content-Type") String contentType,
                                  @Query("serialno") String serialno);


    @POST
    Call<SuccesResponseModel> sendBalanceRequest(@Url String url,
                                                 @Body RequestBody body,
                                                 @Header("X-Authorization") String xAuth,
                                                 @Header("Authorization") String Authorization,
                                                 @Header("Content-Type") String contentType,
                                                 @Query("serialno") String serialno);

    @POST
    Call<TransferModel> sendDepositRequest(@Url String url,
                                           @Body RequestBody body,
                                           @Header("X-Authorization") String xAuth,
                                           @Header("Authorization") String Authorization,
                                           @Header("Content-Type") String contentType,
                                           @Query("serialno") String serialno);

    @POST
    Call<TransferModel> sendFundTransferRequest(@Url String url,
                                                @Body RequestBody body,
                                                @Header("X-Authorization") String xAuth,
                                                @Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType,
                                                @Query("serialno") String serialno
    );

    @POST
    Call<SuccesResponseModel> sendPostLoanDemand(@Url String url,
                                                 @Body RequestBody body,
                                                 @Header("X-Authorization") String xAuth,
                                                 @Header("Authorization") String Authorization,
                                                 @Header("Content-Type") String contentType,
                                                 @Query("serialno") String serialno);

    @POST
    Call<WithDrawlResponse> sendWithdrawRequest(@Url String url,
                                                @Body RequestBody body,
                                                @Header("X-Authorization") String xAuth,
                                                @Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType,
                                                @Query("serialno") String serialno);

        /*@GET("member/search?serialno=12346&mobileno=M0670001")*/
    @GET
    Call<SearchModel> sendMemberSearchRequest(@Url String url,
                                              @Query("mobileno") String mobileno,
                                              @Header("X-Authorization") String xAuth,
                                              @Header("Authorization") String Authorization,
                                              @Header("Content-Type") String contentType,
                                              @Query("serialno") String serialno);


    //    @GET("caste")
    @GET
    Call<List<CastModel>> getCasteList(@Url String url,
//                                   @Query("mobileno") String mobileno,
                                       @Header("X-Authorization") String xAuth,
                                       @Header("Authorization") String Authorization,
                                       @Header("Content-Type") String contentType,
                                       @Query("serialno") String serialno);

    @GET
    io.reactivex.Observable<List<LoanDetailsModel>> getLoanTypeList(@Url String url,
                                                                    @Header("X-Authorization") String xAuth,
                                                                    @Header("Authorization") String Authorization,
                                                                    @Header("Content-Type") String contentType,
                                                                    @Query("serialno") String serialno);



    @POST
    Call<WithDrawlResponse> sendCashOtpRequest(@Url String url,
                                                @Body RequestBody body,
                                                @Header("X-Authorization") String xAuth,
                                                @Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType,
                                                @Query("serialno") String serialno);
    @POST
    Call<WithDrawlResponse> sendWithdrawOtpRequest(@Url String url,
                                                @Body RequestBody body,
                                                @Header("X-Authorization") String xAuth,
                                                @Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType,
                                                @Query("serialno") String serialno);

    @POST
    Call<WithDrawlResponse> sendFtOtpRequest(@Url String url,
                                                   @Body RequestBody body,
                                                   @Header("X-Authorization") String xAuth,
                                                   @Header("Authorization") String Authorization,
                                                   @Header("Content-Type") String contentType,
                                                   @Query("serialno") String serialno);

}
