package com.harati.jeevanbikas.ActivityLogPackage.dailyLogs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harati.jeevanbikas.Adapter.DashboardRecyclerViewAdapter;
import com.harati.jeevanbikas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 10/31/2017.
 */

public class DailyLogAdapter extends RecyclerView.Adapter<DailyLogAdapter.ViewHolder> {
    List<DailyLogModel> dailyLogModelList = new ArrayList<>();
    Context context;

    public DailyLogAdapter(List<DailyLogModel> dailyLogModelList, Context context) {
        this.dailyLogModelList = dailyLogModelList;
        this.context = context;
    }

    @Override
    public DailyLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_log_model,parent,false);
        DailyLogAdapter.ViewHolder viewHolder = new DailyLogAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DailyLogAdapter.ViewHolder holder, int position) {
        DailyLogModel dailyLogModel = dailyLogModelList.get(position);

        holder.dl_amount.setText(dailyLogModel.getAmount());
        holder.dl_time.setText(dailyLogModel.getTime());
        holder.dl_title.setText(dailyLogModel.getTitle());

    }

    @Override
    public int getItemCount() {
        return dailyLogModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dl_title,dl_time ,dl_amount;
        public ViewHolder(View itemView) {
            super(itemView);
            dl_title=(TextView)itemView.findViewById(R.id.dl_title);
            dl_time=(TextView)itemView.findViewById(R.id.dl_time);
            dl_amount=(TextView)itemView.findViewById(R.id.dl_amount);
        }
    }
}
