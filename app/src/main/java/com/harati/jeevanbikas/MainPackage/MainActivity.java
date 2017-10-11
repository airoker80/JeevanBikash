package com.harati.jeevanbikas.MainPackage;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.harati.jeevanbikas.Adapter.DashboardRecyclerViewAdapter;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.ModelPackage.DashBoardModel;
import com.harati.jeevanbikas.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    List<DashBoardModel> dashBoardModels = new ArrayList<DashBoardModel>();
    RecyclerView dashboard_icon_list;
    public static RelativeLayout main_gone_rl;
    public static Typeface centuryGothic;
    ImageView app_icon;
    CenturyGothicTextView logoutTxt;
//    public Typeface centuryGothic=Typeface.createFromAsset(getAssets(), "fonts/epimodem.ttf");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SessionHandler sessionHandler = new SessionHandler(MainActivity.this);
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
                sessionHandler.logoutUser();
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
}
