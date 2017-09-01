package com.harati.jeevanbikas.BalanceEnquiry;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.AutoCompleteHelper.AutoCompleteAdapter;
import com.harati.jeevanbikas.Helper.AutoCompleteHelper.AutoCompleteModel;
import com.harati.jeevanbikas.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 8/28/2017.
 */

public class BalanceFragment extends Fragment {
    List<AutoCompleteModel> autoCompleteModelList = new ArrayList<AutoCompleteModel>();
    AutoCompleteTextView input;
     ImageView imageView;
    public BalanceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_balance, container, false);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        input = (AutoCompleteTextView) view.findViewById(R.id.input);

        autoCompleteModelList.add(new AutoCompleteModel("Sameer","9843697320",R.drawable.ic_username));
        autoCompleteModelList.add(new AutoCompleteModel("arjun","9844400099",R.drawable.ic_username));
        autoCompleteModelList.add(new AutoCompleteModel("Binaya","9841012346",R.drawable.ic_username));
        input.setDropDownBackgroundResource(R.drawable.shape_transparent);
        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(getContext(),autoCompleteModelList);
        input.setAdapter(autoCompleteAdapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().equals("")){
                    input.setError("Please Enter the Phone Number");
                }else {
                    Fragment fragment= new FingerPrintFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.commit();
                }
            }
        });
        return view;
    }
}
