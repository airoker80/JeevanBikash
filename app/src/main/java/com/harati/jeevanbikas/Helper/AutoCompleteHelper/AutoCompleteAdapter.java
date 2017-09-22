package com.harati.jeevanbikas.Helper.AutoCompleteHelper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 8/31/2017.
 */

public class AutoCompleteAdapter extends ArrayAdapter<AutoCompleteModel> {
    List<AutoCompleteModel> autoCompleteModelList ;
    List<AutoCompleteModel>  tempCustomer, suggestions;

    public AutoCompleteAdapter(Context context, List<AutoCompleteModel> autoCompleteModelList) {
        super(context, android.R.layout.simple_list_item_1, autoCompleteModelList);
        this.autoCompleteModelList = autoCompleteModelList;
        this.tempCustomer = new ArrayList<AutoCompleteModel>(autoCompleteModelList);
        this.suggestions = new ArrayList<AutoCompleteModel>(autoCompleteModelList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AutoCompleteModel autoCompleteModel = autoCompleteModelList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_list_items, parent, false);
        }
        ImageView userImg = (ImageView) convertView.findViewById(R.id.userImg);
        TextView  userNo = (TextView) convertView.findViewById(R.id.userNo);
        TextView  accNo = (TextView) convertView.findViewById(R.id.accNo);

//        userImg.setImageResource(autoCompleteModel.getImageId());
        userNo.setText(autoCompleteModel.getPhoneNumber());
        userNo.setTypeface(MainActivity.centuryGothic);
        accNo.setText(autoCompleteModel.getName());
        accNo.setTypeface(MainActivity.centuryGothic);
//        return super.getView(position, convertView, parent);
        return  convertView;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            AutoCompleteModel customer = (AutoCompleteModel) resultValue;
            return customer.getPhoneNumber();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (AutoCompleteModel people : tempCustomer) {
                    if (people.getPhoneNumber().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<AutoCompleteModel> c = (List<AutoCompleteModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (AutoCompleteModel cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
