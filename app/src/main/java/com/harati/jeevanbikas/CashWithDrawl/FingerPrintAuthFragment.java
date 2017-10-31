package com.harati.jeevanbikas.CashWithDrawl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.R;


/**
 * Created by User on 8/28/2017.
 */

public class FingerPrintAuthFragment extends Fragment {
    ImageView fingerPrint;

    String code,name,office ,photo;

    public FingerPrintAuthFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        fingerPrint = (ImageView) view.findViewById(R.id.fingerPrint);
        Bundle bundle = getArguments();
        code = bundle.getString("code");
        name = bundle.getString("name");
        office = bundle.getString("office");
        photo = bundle.getString("photo");
        Log.d("bundel",bundle.toString());
        fingerPrint.setOnClickListener(view1 -> {
            Fragment fragment= new CashwithdrawlFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putString("code",code);
            bundle1.putString("name",name);
            bundle1.putString("office",office);
            bundle1.putString("photo",photo);
            fragment.setArguments(bundle1);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.contentFrame, fragment);
            transaction.commit();
        });
        return view;
    }



}
