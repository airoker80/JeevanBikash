package com.harati.jeevanbikas.CashDeposit;


import android.content.Intent;
import android.os.Bundle;
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
import com.harati.jeevanbikas.LoanDemand.DemandDetailsFragment;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 8/28/2017.
 */

public class DepositFragment extends Fragment implements View.OnClickListener{
    List<AutoCompleteModel> autoCompleteModelList = new ArrayList<AutoCompleteModel>();
     ImageView imageView,deposit_cross;
    AutoCompleteTextView input;
    public DepositFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fund, container, false);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        deposit_cross = (ImageView)view.findViewById(R.id.deposit_cross);
        input = (AutoCompleteTextView) view.findViewById(R.id.input);

        autoCompleteModelList.add(new AutoCompleteModel("Sameer","9843697320",R.drawable.ic_username));
        autoCompleteModelList.add(new AutoCompleteModel("arjun","9844400099",R.drawable.ic_username));
        autoCompleteModelList.add(new AutoCompleteModel("Binaya","9841012346",R.drawable.ic_username));
        input.setDropDownBackgroundResource(R.drawable.shape_transparent);
        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(getContext(),autoCompleteModelList);
        input.setAdapter(autoCompleteAdapter);
        imageView.setOnClickListener(this);
        deposit_cross.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int vid= v.getId();
        switch (vid){
            case R.id.imageView:
                if (input.getText().toString().equals("")){
                    input.setError("Please Enter the Phone Number");
                }else {
                    Fragment fragment = new DepositDetailsFragment();
//                    Fragment fragment = new FingerPrintAuthDepositFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.commit();
                }
                break;

            case R.id.deposit_cross:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }

    }
}
