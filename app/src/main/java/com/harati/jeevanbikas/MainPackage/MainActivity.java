package com.harati.jeevanbikas.MainPackage;

import android.graphics.Typeface;
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
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Login.LoginActivity;
import com.harati.jeevanbikas.ModelPackage.DashBoardModel;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Volley.RequestListener;
import com.harati.jeevanbikas.Volley.VolleyRequestHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
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

    public void logoutInWeb(){
        VolleyRequestHandler volleyRequestHandler = new VolleyRequestHandler(MainActivity.this);
        volleyRequestHandler.makeLogoutRequest("logout?serialno=12348", new RequestListener() {
            @Override
            public void onSuccess(String response) {
                sessionHandler.logoutUser();
            }

            @Override
            public void onFailure(String response) {
                sessionHandler.logoutUser();
            }
        });
        sessionHandler.logoutUser();
    }

    void logout(){
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        final StringRequest request = new StringRequest(Request.Method.GET, JeevanBikashConfig.REQUEST_URL+"logout?serialno=12346", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.e("sendobj",sendObj.toString());
                sessionHandler.logoutUser();

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e("sendobj",sendObj.toString());
                sessionHandler.logoutUser();
                Log.d("error", "asdasdasdas");
                if (error instanceof TimeoutError) {
                    Toast.makeText(MainActivity.this, "Time Out Error", Toast.LENGTH_SHORT).show();
                }
                if (error instanceof NoConnectionError) {
                    Toast.makeText(MainActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
                }
                if (error instanceof AuthFailureError) {
                    Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
                if (error instanceof com.android.volley.NetworkError) {
                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
                if (error instanceof com.android.volley.ServerError) {
                    Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
                if (error instanceof com.android.volley.ParseError) {
                    Toast.makeText(MainActivity.this, "JSON Parse Error", Toast.LENGTH_SHORT).show();
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

                    headers.put("X-Authorization",sessionHandler.getAgentToken());
                    headers.put("Authorization", "Basic dXNlcjpqQiQjYUJAMjA1NA==");

                Log.d("header-----", "---" +headers.toString());
                return headers;
            }

            @Override
            public String getBodyContentType() {

                return "application/json";
//                return "application/x-www-form-urlencoded";
            }

        };
        requestQueue.add(request);

    }
}
