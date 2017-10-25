package com.harati.jeevanbikas.CashDeposit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.CGEditText;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.TransferModel;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentClientTransferFragment extends Fragment {
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;
    CenturyGothicTextView name,memberIdnnumber,branchName,shownDepositAmt;
    ImageView agent_client_tick;
    Bundle bundle;
    public AgentClientTransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle =getArguments();
        apiInterface= RetrofitClient.getApiService();
        sessionHandler = new SessionHandler(getContext());
        View view= inflater.inflate(R.layout.fragment_agent_client_transfer, container, false);

        name=(CenturyGothicTextView)view.findViewById(R.id.name);
        memberIdnnumber=(CenturyGothicTextView)view.findViewById(R.id.memberIdnnumber);
        branchName=(CenturyGothicTextView)view.findViewById(R.id.branchName);
        shownDepositAmt=(CenturyGothicTextView)view.findViewById(R.id.shownDepositAmt);

        name.setText(bundle.getString("name"));
        memberIdnnumber.setText(bundle.getString("code"));
        branchName.setText(bundle.getString("office"));
        shownDepositAmt.setText(bundle.getString("deposoitAmt"));
        agent_client_tick=(ImageView)view.findViewById(R.id.agent_client_tick);
        agent_client_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDepositRequest();
//                startActivity(new Intent(getContext(), DialogActivity.class));
            }
        });
        return  view;
    }

    private void sendDepositRequest(){
        sessionHandler.showProgressDialog("Sending Request");
        final JSONObject jsonObject = new JSONObject();
        sessionHandler.showProgressDialog("Sending Request..");
        try{
            jsonObject.put("membercode",bundle.getString("code"));
            jsonObject.put("finger","1234");
            jsonObject.put("amount",bundle.getString("deposoitAmt"));
            jsonObject.put("agentpin",bundle.getString("agentPin"));
            jsonObject.put("remark",bundle.getString("deposoitRemarks"));
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));

        retrofit2.Call<TransferModel> call = apiInterface.sendDepositRequest(body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");
        call.enqueue(new Callback<TransferModel>() {
            @Override
            public void onResponse(Call<TransferModel> call, Response<TransferModel> response) {
                sessionHandler.hideProgressDialog();
                if (response.isSuccessful()){
                    Intent intent = new Intent( getContext(),DialogActivity.class);
                    intent.putExtra("msg",response.body().getMessage());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent( getContext(),ErrorDialogActivity.class);
                    intent.putExtra("msg","Some Field may be Wrong");
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<TransferModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent( getContext(),ErrorDialogActivity.class);
                intent.putExtra("msg","connection Error");
                startActivity(intent);
            }
        });

    }

}
