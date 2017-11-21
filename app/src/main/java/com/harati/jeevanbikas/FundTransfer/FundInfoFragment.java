package com.harati.jeevanbikas.FundTransfer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.TransferModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by User on 8/28/2017.
 */

public class FundInfoFragment extends Fragment {
    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit ;
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;

    CenturyGothicTextView fundtransfername,fundMemberCode ,fundBranchName;

    Bundle bundle;
    ImageView submit;
    EditText BenificiaryaccNo,confirmAccNo ,transferAmt,agentPin ,fundMobile;
    String BenificiaryaccNoTxt,confirmAccNoTxt ,deposoitAmtTxt,agentPinTxt ,fundMobileTxt;
    public FundInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fund_info, container, false);

        apiSessionHandler = new ApiSessionHandler(getContext());
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        sessionHandler = new SessionHandler(getContext());

        submit = (ImageView)view.findViewById(R.id.submit);


        fundtransfername = (CenturyGothicTextView) view.findViewById(R.id.fundtransfername);
        fundMemberCode = (CenturyGothicTextView) view.findViewById(R.id.fundMemberCode);
        fundBranchName = (CenturyGothicTextView) view.findViewById(R.id.fundBranchName);

        bundle = getArguments();


        fundtransfername.setText(bundle.getString("name"));
        fundMemberCode.setText(bundle.getString("code"));
        fundBranchName.setText(bundle.getString("office"));

        BenificiaryaccNo=(EditText)view.findViewById(R.id.BenificiaryaccNo) ;
        confirmAccNo=(EditText)view.findViewById(R.id.confirmAccNo) ;
        transferAmt=(EditText)view.findViewById(R.id.transferAmt) ;
        agentPin=(EditText)view.findViewById(R.id.agentPin) ;
        fundMobile=(EditText)view.findViewById(R.id.fundMobile) ;
        submit.setOnClickListener(view1 -> {
  /*          Intent intent= new Intent(getContext(), DialogActivity.class);
            getActivity().startActivity(intent);*/

            BenificiaryaccNoTxt=BenificiaryaccNo.getText().toString();
            confirmAccNoTxt=confirmAccNo.getText().toString();
            deposoitAmtTxt=transferAmt.getText().toString();
            agentPinTxt=agentPin.getText().toString();
            fundMobileTxt=fundMobile.getText().toString();

            if (BenificiaryaccNoTxt.equals("")|confirmAccNoTxt.equals("")|deposoitAmtTxt.equals("")|
            agentPinTxt.equals("")|fundMobileTxt.equals("")){
                getActivity().startActivity(new Intent(getContext(), ErrorDialogActivity.class));
            }else {
                Fragment fragment= new TransferTransactionDetailFragment();
                bundle.putString("transfer_amount",transferAmt.getText().toString());
                bundle.putString("transfer_pin",agentPin.getText().toString());
                bundle.putString("transfer_beneficiary_no",BenificiaryaccNo.getText().toString());
                bundle.putString("transfer_mobile",fundMobile.getText().toString());

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.contentFrame, fragment);
                transaction.commit();
//                sendTransferPostRequest();
            }
        });
        return view;
    }

    void sendTransferPostRequest(){
        sessionHandler.showProgressDialog("sending Request ... ");
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.getString("code"));
            jsonObject.put("finger","1234");
            jsonObject.put("amount",transferAmt.getText().toString());
            jsonObject.put("agentpin",agentPin.getText().toString());
            jsonObject.put("beneficiary",BenificiaryaccNo.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<TransferModel> call = apiInterface.sendFundTransferRequest(apiSessionHandler.getFUND_TRANSFER(),body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<TransferModel>() {
            @Override
            public void onResponse(Call<TransferModel> call, Response<TransferModel> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    Intent intent = new Intent(getContext(),DialogActivity.class);
                    intent.putExtra("msg",response.body().getMessage());
                    startActivity(intent);
                }else {
                    try {

                        String jsonString = response.errorBody().string();

                        Log.d("here ","--=>"+jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",jsonObject.getString("message"));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<TransferModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent(getContext(),DialogActivity.class);
                intent.putExtra("msg","Connection Error");
                startActivity(intent);
            }
        });
    }
}
