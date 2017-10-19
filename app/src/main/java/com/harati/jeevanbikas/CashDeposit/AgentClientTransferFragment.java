package com.harati.jeevanbikas.CashDeposit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;

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

    ImageView agent_client_tick;

    public AgentClientTransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        apiInterface= RetrofitClient.getApiService();
        sessionHandler = new SessionHandler(getContext());
        View view= inflater.inflate(R.layout.fragment_agent_client_transfer, container, false);
        agent_client_tick=(ImageView)view.findViewById(R.id.agent_client_tick);
        agent_client_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*sendDepositRequest()*/
                startActivity(new Intent(getContext(), DialogActivity.class));
            }
        });
        return  view;
    }

    private void sendDepositRequest(){
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode","M0670001");
            jsonObject.put("finger","1234");
            jsonObject.put("amount","1234");
            jsonObject.put("agentpin","1234");
            jsonObject.put("remark","testing");
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));

        retrofit2.Call<String> call = apiInterface.sendDepositRequest(body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

}
