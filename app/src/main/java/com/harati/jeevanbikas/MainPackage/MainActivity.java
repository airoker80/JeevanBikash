package com.harati.jeevanbikas.MainPackage;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.harati.jeevanbikas.Adapter.DashboardRecyclerViewAdapter;
import com.harati.jeevanbikas.CashDeposit.DepositDetailsFragment;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Login.LoginActivity;
import com.harati.jeevanbikas.ModelPackage.DashBoardModel;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;
import com.harati.jeevanbikas.Volley.RequestListener;
import com.harati.jeevanbikas.Volley.VolleyRequestHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    Spinner spinner;
    List<DashBoardModel> dashBoardModels = new ArrayList<DashBoardModel>();
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
        apiInterface= RetrofitClient.getApiService();
        setContentView(R.layout.activity_main);
        main_gone_rl = (RelativeLayout) findViewById(R.id.main_gone_rl);
        spinner = (Spinner) findViewById(R.id.spinner);
        TextView dashboardTitile = (TextView) findViewById(R.id.dashboardTitile);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
        R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        app_icon=(ImageView)findViewById(R.id.app_icon);
        logoutTxt=(CenturyGothicTextView)findViewById(R.id.logoutTxt);

        logoutTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                logoutInWeb();
//                sessionHandler.logoutUser();
                logout();
            }
        });

//        sessionHandler.getInfo();

        centuryGothic=Typeface.createFromAsset(MainActivity.this.getAssets(), "cg.ttf");
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
        dashBoardModels.add(new DashBoardModel(R.drawable.ic_setting, "Customization \n & Setting"));
        dashboard_icon_list.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        DashboardRecyclerViewAdapter dashboardRecyclerViewAdapter = new DashboardRecyclerViewAdapter(MainActivity.this, dashBoardModels);
        dashboard_icon_list.setAdapter(dashboardRecyclerViewAdapter);
    }

    void logout(){
        sessionHandler.showProgressDialog("Logging Out..");
        sessionHandler.logoutUser();
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
            retrofit2.Call<SuccesResponseModel> call = apiInterface.sendLogoutRequest(sessionHandler.getAgentToken(),
                    "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                    "application/json");
            call.enqueue(new Callback<SuccesResponseModel>() {
                @Override
                public void onResponse(Call<SuccesResponseModel> call, retrofit2.Response<SuccesResponseModel> response) {
                    sessionHandler.hideProgressDialog();
                    if (response.isSuccessful()){
                        sessionHandler.logoutUser();
                    }
                }

                @Override
                public void onFailure(Call<SuccesResponseModel> call, Throwable t) {
                    sessionHandler.hideProgressDialog();
                    sessionHandler.logoutUser();
                }
            });
        }

}
