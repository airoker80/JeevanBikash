package com.harati.jeevanbikas.BalanceEnquiry;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.R;


/**
 * Created by User on 8/28/2017.
 */

public class FingerPrintFragment extends Fragment {
    ImageView fingerPrint;

    public FingerPrintFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        fingerPrint = (ImageView) view.findViewById(R.id.fingerPrint);
        fingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }


    public void showDialog() {
//        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//                .create();
//
//        final LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
//        Button ok = (Button) dialogView.findViewById(R.id.ok);
//        alertDialog.setView(dialogView);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//
//
//        alertDialog.show();
        Intent intent= new Intent(getContext(), DialogActivity.class);
        getActivity().startActivity(intent);

    }
}
