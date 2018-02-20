package com.harati.jeevanbikas.PabyByOnlineService.Adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.harati.jeevanbikas.PabyByOnlineService.Model.ServiceCatagoryDetails;
import com.harati.jeevanbikas.PabyByOnlineService.ViewHolder.ParentRecycleViewHolder;
import com.harati.jeevanbikas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Sameer on 1/15/2018.
 */

public class JBParentRVAdapter extends RecyclerView.Adapter<JBParentRVAdapter.ViewHolder> {
    Context context;
    List<ServiceCatagoryDetails> serviceCategoryList =
            new ArrayList<ServiceCatagoryDetails>();
    LayoutInflater inflater;
    int childLayout;
    CoordinatorLayout coordinatorLayout;
    String servTypeId ="";

    public JBParentRVAdapter(Context context, List<ServiceCatagoryDetails> serviceCategoryList, int childLayout, CoordinatorLayout coordinatorLayout, String servTypeId) {
        this.context = context;
        this.serviceCategoryList = serviceCategoryList;
        this.inflater = LayoutInflater.from(context);
        this.childLayout = childLayout;
        this.coordinatorLayout = coordinatorLayout;
        this.servTypeId = servTypeId;
    }

    @Override
    public JBParentRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.parent_recycleview_layout, parent, false);
        ViewHolder viewHolder= new ViewHolder(itemView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(JBParentRVAdapter.ViewHolder holder, int position) {
        ServiceCatagoryDetails serviceCatagoryDetails = serviceCategoryList.get(position);
        holder.serCatTxt.setText(serviceCatagoryDetails.getServiceCategoryName());

        Picasso.with(context)
                .load(serviceCatagoryDetails.getServiceCatImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.parentImage);
        Log.i("idddd====", serviceCatagoryDetails.getServiceCategoryId() + serviceCatagoryDetails.getServiceCategoryName() );

        holder.childRecycleViewAdapter = new ChildRecycleViewAdapter(context,serviceCatagoryDetails.getServiceTypeArrayList()
                ,holder.rechargeFormLayout, childLayout,coordinatorLayout,serviceCatagoryDetails.getServiceCategoryId(),servTypeId,position);
        Log.i("idddd====", serviceCatagoryDetails.getServiceCategoryId() + serviceCatagoryDetails.getServiceCategoryName() );
        holder.childRecycleView.setAdapter(holder.childRecycleViewAdapter);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("msgss", "parentImage click");
                holder.showHideRechargeServices(holder.ifClick);

                //  parentName.setVisibility(View.GONE);
            }
        });

        if(!servTypeId.isEmpty()){
            holder.showHideRechargeServices(false);
        }
    }

    @Override
    public int getItemCount() {
        return serviceCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView parentImage;
        RecyclerView childRecycleView;
        LinearLayout rechargeFormLayout;
        LinearLayout serCatLayout;
        ChildRecycleViewAdapter childRecycleViewAdapter;

        Context context;
        TextView serCatTxt;
        Boolean ifClick=false;
        View holder;
        public ViewHolder(View itemView) {
            super(itemView);
            holder=itemView;

            parentImage = (ImageView) itemView.findViewById(R.id.parentImage);
            childRecycleView = (RecyclerView) itemView.findViewById(R.id.childRecycleView);
            rechargeFormLayout = (LinearLayout) itemView.findViewById(R.id.rechargeFormLayout);
            serCatLayout = (LinearLayout) itemView.findViewById(R.id.serCatLayout);
            serCatTxt = (TextView) itemView.findViewById(R.id.serCatTxt);

            context = itemView.getContext();

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            childRecycleView.setHasFixedSize(true);
            childRecycleView.setLayoutManager(layoutManager);
            childRecycleView.setVisibility(View.GONE);
        }

        public void showHideRechargeServices(Boolean showHide){
            if (showHide) {

                Log.i("msgss", "ifClick true");
                serCatTxt.setVisibility(View.VISIBLE);
                childRecycleView.setVisibility(View.GONE);
                rechargeFormLayout.setVisibility(View.GONE);
                ifClick = false;

            }else {

                Log.i("msgss", "ifClick false");
                serCatTxt.setVisibility(View.GONE);
                childRecycleView.setVisibility(View.VISIBLE);
                rechargeFormLayout.setVisibility(View.GONE);
                ifClick = true;
            }

        }
    }
}
