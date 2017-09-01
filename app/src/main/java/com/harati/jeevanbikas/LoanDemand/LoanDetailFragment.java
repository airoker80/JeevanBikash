package com.harati.jeevanbikas.LoanDemand;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.R;


/**
 * Created by User on 8/28/2017.
 */

public class LoanDetailFragment extends Fragment {
    ImageView submit;
    Spinner spinner;
    EditText loanAmt;
    public LoanDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_loan_detail, container, false);
        submit = (ImageView)view.findViewById(R.id.submit);
        loanAmt = (EditText) view.findViewById(R.id.loanAmt);
        spinner= (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.loantype, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loanAmt.getText().toString().equals("")|spinner.getSelectedItem().toString().equals("")){
                    Intent intent= new Intent(getContext(), ErrorDialogActivity.class);
                    getActivity().startActivity(intent);
                }else {
                    Intent intent= new Intent(getContext(), DialogActivity.class);
                    getActivity().startActivity(intent);
                }

            }
        });
        return view;
    }
}
