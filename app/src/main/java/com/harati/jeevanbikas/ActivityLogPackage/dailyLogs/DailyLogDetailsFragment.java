package com.harati.jeevanbikas.ActivityLogPackage.dailyLogs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harati.jeevanbikas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyLogDetailsFragment extends Fragment {
    RecyclerView dailyLogList;
    List<DailyLogModel> dailyLogModelList = new ArrayList<>();


    public DailyLogDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_daily_log_details, container, false);
        dailyLogList=(RecyclerView)view.findViewById(R.id.dailyLogList);
        dailyLogModelList.add(new DailyLogModel("10:00","Daily Log","1000.00"));
        dailyLogModelList.add(new DailyLogModel("10:00","Daily Log","2000.00"));
        dailyLogModelList.add(new DailyLogModel("10:00","Daily Log","3000.00"));
        dailyLogModelList.add(new DailyLogModel("10:00","Daily Log","4000.00"));

        dailyLogList.setLayoutManager(new LinearLayoutManager(getContext()));
        DailyLogAdapter dailyLogAdapter = new DailyLogAdapter(dailyLogModelList,getContext());

        dailyLogList.setAdapter(dailyLogAdapter);


        return view;
    }

}
