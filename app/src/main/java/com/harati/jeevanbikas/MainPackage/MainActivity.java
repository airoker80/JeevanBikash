package com.harati.jeevanbikas.MainPackage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.harati.jeevanbikas.Adapter.DashboardRecyclerViewAdapter;
import com.harati.jeevanbikas.AgentDashboard.AgentDashboardActivity;
import com.harati.jeevanbikas.BaseActivity;
import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.ModelPackage.DashBoardModel;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.ResetPassword.InitialResetPassword;
import com.harati.jeevanbikas.ResetPassword.ResetPassword;
import com.harati.jeevanbikas.ResetPin.InitialResetPinActivity;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {
    PackageInfo pInfo = null;
    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;
    Spinner spinner;
    List<DashBoardModel> dashBoardModels = new ArrayList<>();
    RecyclerView dashboard_icon_list;
    public static RelativeLayout main_gone_rl;
    public static Typeface centuryGothic;
    ImageView app_icon;
    CenturyGothicTextView logoutTxt;
    SessionHandler sessionHandler;


//    public Typeface centuryGothic=Typeface.createFromAsset(getAssets(), "fonts/epimodem.ttf");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         sessionHandler = new SessionHandler(MainActivity.this);
         apiSessionHandler = new ApiSessionHandler(MainActivity.this);
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
//        setContentView(R.layout.activity_main);

/*        Observable.just(photo)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                }).subscribe();*/



        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        drawer.addView(contentView, 0);

        String photo = getIntent().getStringExtra("userphoto");
//

        Log.v("photo",sessionHandler.getAgentPhoto());
        super.setNav(sessionHandler.getAgentPhoto(),sessionHandler.getAgentCode(),sessionHandler.getUsername(),sessionHandler.getBranchOffice(),sessionHandler.getAgentBalance(),sessionHandler.getAGENT_Odlimit());
        nav_username.setText(sessionHandler.getUsername());

        main_gone_rl = (RelativeLayout) findViewById(R.id.main_gone_rl);
        spinner = (Spinner) findViewById(R.id.spinner);
        TextView dashboardTitile = (TextView) findViewById(R.id.dashboardTitile);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
        R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        app_icon=(ImageView)findViewById(R.id.app_icon);
        logoutTxt=(CenturyGothicTextView)findViewById(R.id.logoutTxt);

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
/*            requestParams.put("installedAppVersionName", pInfo.versionName+"");
            requestParams.put("installedAppVersionCode", pInfo.versionCode+"");
            requestParams.put("applicationName", getPackageName()+"");*/

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        logoutTxt.setOnClickListener(v -> logout());

        sessionHandler.getInfo();

        centuryGothic=Typeface.createFromAsset(MainActivity.this.getAssets(), "cg.ttf");
        if (getPackageName().equals("com.harati.BLB")){
            dashboardTitile.setText("BLB "+"( Version :"+String.valueOf(pInfo.versionName)+")");
        }else {
            dashboardTitile.setText("JEEVAN BIKAS SAMAJ "+"( Version :"+String.valueOf(pInfo.versionName)+")");
        }
        dashboardTitile.setTypeface(centuryGothic);

        dashboard_icon_list = (RecyclerView) findViewById(R.id.dashboard_icon_list);
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_balance_enquiry, "Balance \n Inquiry"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_cash_withdrawl, "Cash \n Withdrawal"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_cash_deposit, "Cash \n Deposit"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_fund_transfer, "Funds \n Transfer"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_loan_demand, "Loan \n Demand"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_new_enrollment, "New Member \n Enrollment"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_mobile_topup, "Mobile Recharge \n & Topup"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_bill, "Bill \n Payment"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_utility, "Utility \n Payment"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_profile, "Agent \n Dashboard"));
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_passage_of_time, "Activity \n Log"));
//        dashBoardModels.add(new DashBoardModel(R.drawable.ic_setting, "Customization \n & Setting"));
        dashboard_icon_list.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        DashboardRecyclerViewAdapter dashboardRecyclerViewAdapter = new DashboardRecyclerViewAdapter(MainActivity.this, dashBoardModels);
        dashboard_icon_list.setAdapter(dashboardRecyclerViewAdapter);

        try {
            if (!getIntent().getStringExtra("msg").equals("x")){
                if (sessionHandler.changePasswordReqd()&sessionHandler.changePinReqd()){
                    Intent intent =  new Intent(this, AgentDashboardActivity.class);
                    intent.putExtra("snackmsg","fromMA");
                    startActivity(intent);
                }else if (sessionHandler.changePinReqd()){
                    Intent intent =  new Intent(this, InitialResetPinActivity.class);
                    intent.putExtra("snackmsg","fromMA");
                    startActivity(intent);
                }else if (sessionHandler.changePasswordReqd()){
                    Intent intent =  new Intent(this, ResetPassword.class);
                    intent.putExtra("snackmsg","fromMA");
                    startActivity(intent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void logout(){

//        sessionHandler.logoutUser();
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        io.reactivex.Observable<SuccesResponseModel> call = apiInterface.sendLogoutRequest(sessionHandler.getAgentToken(),
                    "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                    "application/json",apiSessionHandler.getAgentCode());
            /*call.enqueue(new Callback<SuccesResponseModel>() {
                @Override
                public void onResponse(Call<SuccesResponseModel> call, retrofit2.Response<SuccesResponseModel> response) {
                    sessionHandler.hideProgressDialog();
                    if (response.isSuccessful()){
                        sessionHandler.logoutUser();
                    }else {
                        sessionHandler.logoutUser();
                    }
                }

                @Override
                public void onFailure(Call<SuccesResponseModel> call, Throwable t) {
                    sessionHandler.hideProgressDialog();
                    sessionHandler.logoutUser();
                }
            });*/
            call.subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<SuccesResponseModel>() {
                      @Override
                      public void onSubscribe(Disposable d) {
                          sessionHandler.showProgressDialog("Logging Out..");
                      }

                      @Override
                      public void onNext(SuccesResponseModel value) {
                          Log.v("logout",value.getMessage()+value.getStatus());
                      }

                      @Override
                      public void onError(Throwable e) {
                          sessionHandler.hideProgressDialog();
                          sessionHandler.logoutUser();
                      }

                      @Override
                      public void onComplete() {
                          sessionHandler.hideProgressDialog();
                          sessionHandler.logoutUser();
                          finish();
                      }
                  });
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}





