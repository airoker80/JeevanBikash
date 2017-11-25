package com.harati.jeevanbikas.FundTransfer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.AutoCompleteHelper.CustomACTextView;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFundDetailsFragment extends Fragment implements View.OnClickListener {

    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;
    CenturyGothicTextView fundName,fundmemberId,fundOffice,senderMid;
    CenturyGothicTextView fundNameBenf,fundmemberIdBenf,fundOfficeBenf,mobileBenf;
    EditText benf_ft_etx;
    LinearLayout down_ll,mid_ll;
    ImageView fund_tick,fund_cross;
    Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_fund_details, container, false);
        bundle = getArguments();

        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        apiSessionHandler = new ApiSessionHandler(getContext());
        sessionHandler = new SessionHandler(getContext());


        down_ll =(LinearLayout) view.findViewById(R.id.down_ll);
        mid_ll =(LinearLayout) view.findViewById(R.id.mid_ll);

        fundName =(CenturyGothicTextView)view.findViewById(R.id.fundName);
        fundmemberId =(CenturyGothicTextView)view.findViewById(R.id.fundmemberId);
        fundOffice =(CenturyGothicTextView)view.findViewById(R.id.fundOffice);
        senderMid =(CenturyGothicTextView)view.findViewById(R.id.senderMid);
        mobileBenf =(CenturyGothicTextView)view.findViewById(R.id.mobileBenf);

        fundNameBenf =(CenturyGothicTextView)view.findViewById(R.id.fundNameBenf);
        fundmemberIdBenf =(CenturyGothicTextView)view.findViewById(R.id.fundmemberIdBenf);
        fundOfficeBenf =(CenturyGothicTextView)view.findViewById(R.id.fundOfficeBenf);

        benf_ft_etx =(EditText) view.findViewById(R.id.benf_ft_etx);

        if (bundle.get("goto").equals("beif")){
            fundName.setText(bundle.getString("name"));
            fundmemberId.setText(bundle.getString("code"));
            fundOffice.setText(bundle.getString("office"));
            senderMid.setText(bundle.getString("phone"));
        }else if (bundle.get("goto").equals("info")){
            fundName.setText(bundle.getString("nameBenificiary"));
            fundmemberId.setText(bundle.getString("codeBenificiary"));
            fundOffice.setText(bundle.getString("officeBenificiary"));
            Log.e("fad","===>"+bundle.getString("phoneBenificiary"));
            mobileBenf.setText(bundle.getString("phoneBenificiary"));
        }

        fund_tick=(ImageView)view.findViewById(R.id.fund_tick);
        fund_cross=(ImageView)view.findViewById(R.id.fund_cross);
        fund_tick.setOnClickListener(this);
        fund_cross.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId){
            case R.id.fund_tick:
                if (mid_ll.getVisibility() !=View.GONE){
                    if (benf_ft_etx.getText().toString().equals(fundmemberId.getText().toString())){
                        Intent intent = new Intent(getContext(),ErrorDialogActivity.class);
                        intent.putExtra("msg","Same Member Codes");
                        startActivity(intent);
                    }else {
                        Log.e("Ok","okkkkk"+benf_ft_etx.getText().toString());
                        getMemberList(benf_ft_etx.getText().toString());
                    }

                }else {
                    Fragment fragment = new FundInfoFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.fund_cross:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }

    private void getMemberList(String mobile_no){
        Log.e("Ok","okkkkk");
        sessionHandler.showProgressDialog("sending Request ....");
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<SearchModel> call = apiInterface.sendMemberSearchRequest(apiSessionHandler.getMEMBER_SEARCH(),mobile_no,sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                sessionHandler.hideProgressDialog();
//                Log.d("DADAD0","ADA");
                if (String.valueOf(response.code()).equals("200")){
                    mid_ll.setVisibility(View.GONE);
                    down_ll.setVisibility(View.VISIBLE);
//                    bundle.putString("goto","info");
                    bundle.putString("codeBenificiary",response.body().getCode());
                    bundle.putString("nameBenificiary",response.body().getName());
                    bundle.putString("officeBenificiary",response.body().getOffice());
//                    bundle.putString("office",response.body().getCode());
//                    bundle.putString("photoBenificiary",response.body().getOffice());
                    bundle.putString("phoneBenificiary",response.body().getMobileno());

                    fundNameBenf.setText(response.body().getName());
                            fundmemberIdBenf.setText(response.body().getCode());
                    fundOfficeBenf.setText(response.body().getOffice());
                    mobileBenf.setText(response.body().getMobileno());

                }else {
                    try {
                        String jsonString = response.errorBody().string();

                        Log.d("here ","--=>"+jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",jsonObject.getString("message"));
                        startActivity(intent);
                    } catch (Exception e) {
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg","Wrong Credential");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
