package com.harati.jeevanbikas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.jeevanbikas.ActivityLogPackage.AgentLogActivity;
import com.harati.jeevanbikas.AgentDashboard.AgentDashboardActivity;
import com.harati.jeevanbikas.BalanceEnquiry.BalanceEnquiryActivity;
import com.harati.jeevanbikas.CashDeposit.CashDepositActivity;
import com.harati.jeevanbikas.CashWithDrawl.CashWithDrawlActivity;
import com.harati.jeevanbikas.Enrollment.EnrollmentActivity;
import com.harati.jeevanbikas.FundTransfer.FundTransferActivity;
import com.harati.jeevanbikas.KycPackage.KycActivity;
import com.harati.jeevanbikas.LoanDemand.LoanDemandActivity;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.ModelPackage.DashBoardModel;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.RechargeTopup.RechargeTopup;
import com.harati.jeevanbikas.UtilityPackage.UtilityPayment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 8/28/2017.
 */

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter<DashboardRecyclerViewAdapter.ViewHolder> {
    Context context;
    List<DashBoardModel> dashBoardModelList = new ArrayList<>();

    public DashboardRecyclerViewAdapter(Context context, List<DashBoardModel> dashBoardModelList) {
        this.context = context;
        this.dashBoardModelList = dashBoardModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_model,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DashBoardModel dashBoardModel = dashBoardModelList.get(position);
        holder.dashboard_text_id.setText(dashBoardModel.getDashboard_icon_name());
        holder.dashboard_text_id.setTypeface(MainActivity.centuryGothic);
        holder.dashboard_icon_id.setImageResource(dashBoardModel.getDashboard_icon_id());
        final AppCompatActivity activity=(AppCompatActivity) context;
        holder.itemView.setOnClickListener(v -> {
            if (dashBoardModel.getDashboard_icon_name().equals("Balance \n Inquiry")){
                context.startActivity(new Intent(context,BalanceEnquiryActivity.class));
                ((AppCompatActivity) context).isDestroyed();
            }
            if (dashBoardModel.getDashboard_icon_name().equals("Cash \n Withdrawal")){
                    context.startActivity(new Intent(context,CashWithDrawlActivity.class));
                    ((AppCompatActivity) context).isDestroyed();
            }
            if (dashBoardModel.getDashboard_icon_name().equals("Cash \n Deposit")){
                    context.startActivity(new Intent(context,CashDepositActivity.class));
                    ((AppCompatActivity) context).isDestroyed();
            }
            if (dashBoardModel.getDashboard_icon_name().equals("Funds \n Transfer")){
                    context.startActivity(new Intent(context,FundTransferActivity.class));
                    ((AppCompatActivity) context).isDestroyed();
            }
            if (dashBoardModel.getDashboard_icon_name().equals("New Member \n Enrollment")){
                    context.startActivity(new Intent(context,EnrollmentActivity.class));
                    ((AppCompatActivity) context).isDestroyed();
            }
            if (dashBoardModel.getDashboard_icon_name().equals("Loan \n Demand")){
                    context.startActivity(new Intent(context,LoanDemandActivity.class));
                    ((AppCompatActivity) context).isDestroyed();
            }
            if (dashBoardModel.getDashboard_icon_name().equals("Mobile Recharge \n & Topup")){
                    context.startActivity(new Intent(context,RechargeTopup.class));
                    ((AppCompatActivity) context).isDestroyed();
            }
            if (dashBoardModel.getDashboard_icon_name().equals("Utility \n Payment")){
                    context.startActivity(new Intent(context,UtilityPayment.class));
                    ((AppCompatActivity) context).isDestroyed();
            }
            if (dashBoardModel.getDashboard_icon_name().equals("Customization \n & Setting")){
                    context.startActivity(new Intent(context,KycActivity.class));
            }
            if (dashBoardModel.getDashboard_icon_name().equals("Agent \n Dashboard")){
                    context.startActivity(new Intent(context,AgentDashboardActivity.class));
                    ((AppCompatActivity) context).isDestroyed();
            }
            if (dashBoardModel.getDashboard_icon_name().equals("Activity \n Log")){
                    context.startActivity(new Intent(context,AgentLogActivity.class));
                    ((AppCompatActivity) context).isDestroyed();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dashBoardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dashboard_icon_id;
        TextView dashboard_text_id;
        public ViewHolder(View itemView) {
            super(itemView);
            dashboard_icon_id = (ImageView)itemView.findViewById(R.id.dashboard_icon_id);
            dashboard_text_id = (TextView) itemView.findViewById(R.id.dashboard_text_id);
        }
    }
}
