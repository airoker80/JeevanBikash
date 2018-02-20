package com.harati.jeevanbikas.PabyByOnlineService.Adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.harati.jeevanbikas.PabyByOnlineService.Model.ServiceType;
import com.harati.jeevanbikas.PabyByOnlineService.ViewHolder.ChildRecycleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 1/15/2018.
 */

public class JBChildRecyclerViewAdapter extends RecyclerView.Adapter<JBChildRecyclerViewAdapter.ViewHolder> {
    Context context;
    List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
    LayoutInflater inflater;
    LinearLayout rechargeFormLayout;
    int childLayout ;
    CoordinatorLayout coordinatorLayout;
    String serviceCatagoryId;
    String servTypeId;
    Boolean ifClick=false;
    int parentPosition;


    public JBChildRecyclerViewAdapter(Context context, List<ServiceType> serviceTypeList,
                                   LinearLayout rechargeFormLayout, int childLayout
            , CoordinatorLayout coordinatorLayout, String serviceCatagoryId, String servTypeId, int parentPosition) {

        this.context = context;
        this.rechargeFormLayout = rechargeFormLayout;
        this.coordinatorLayout = coordinatorLayout;
        this.serviceTypeList = serviceTypeList;
        this.servTypeId = servTypeId;
        this.childLayout = childLayout;
        this.parentPosition = parentPosition;
        this.serviceCatagoryId = serviceCatagoryId;
        inflater = LayoutInflater.from(context);

        if(!servTypeId.isEmpty()){

            List<ServiceType> serviceTypeListTemp = new ArrayList<ServiceType>();
            for(ServiceType serviceType:serviceTypeList){
                if(serviceType.getService_type_id().equals(servTypeId)){
                    serviceTypeListTemp.add(serviceType);
                }
            }
            for(ServiceType serviceType:serviceTypeList){
                if(!(serviceType.getService_type_id().equals(servTypeId))){
                    serviceTypeListTemp.add(serviceType);
                }
            }
            this.serviceTypeList = serviceTypeListTemp;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(childLayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return serviceTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
