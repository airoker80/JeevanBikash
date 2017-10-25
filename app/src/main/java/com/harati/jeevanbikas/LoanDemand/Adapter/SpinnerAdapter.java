package com.harati.jeevanbikas.LoanDemand.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoanDetailsModel;

import java.util.List;

/**
 * Created by Sameer on 10/25/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<LoanDetailsModel> {
    private  Context context;
    private  List<LoanDetailsModel> loanDetailsModels;


    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, Context context1, List<LoanDetailsModel> loanDetailsModels) {
        super(context, resource);
        this.context = context1;
        this.loanDetailsModels = loanDetailsModels;
    }
    @Override
    public int getCount(){
        return loanDetailsModels.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(loanDetailsModels.get(position).getLoanType());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }
}
