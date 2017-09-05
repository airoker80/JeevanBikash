package com.harati.jeevanbikas.RechargeTopup.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.RechargeTopup.Model.MobileTopupModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 9/4/2017.
 */

public class MobileRechargeAdapter extends RecyclerView.Adapter<MobileRechargeAdapter.ViewHolder> {
    Context context ;
    List<MobileTopupModel> topupModelList = new ArrayList<MobileTopupModel>();

    public MobileRechargeAdapter(Context context, List<MobileTopupModel> topupModelList) {
        this.context = context;
        this.topupModelList = topupModelList;
    }

    @Override
    public MobileRechargeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topup_model,parent,false);
        MobileRechargeAdapter.ViewHolder viewHolder = new MobileRechargeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MobileRechargeAdapter.ViewHolder holder, int position) {
        MobileTopupModel mobileTopupModel = topupModelList.get(position);
        holder.topup_icon_id.setImageResource(mobileTopupModel.getImageId());
        holder.topup_text_id.setText(mobileTopupModel.getTopupName());

    }

    @Override
    public int getItemCount() {
        return topupModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView topup_icon_id;
        TextView topup_text_id;
        public ViewHolder(View itemView) {
            super(itemView);
            topup_icon_id = (ImageView)itemView.findViewById(R.id.topup_icon_id);
            topup_text_id = (TextView) itemView.findViewById(R.id.topup_text_id);
        }
    }
}
