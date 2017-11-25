package com.harati.jeevanbikas.CashDeposit;


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
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.CGEditText;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.WithDrawlResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentPinDetailsFragment extends Fragment implements View.OnClickListener {
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler;
    ApiSessionHandler apiSessionHandler;
    ImageView agent_tick,demand_cross;
    String code,name,office ,photo,clientPin,clientCode;
    EditText agentPin;
    CGEditText deposoitAmt,deposoitRemarks,clientsPinEtxt,cd_mobile_no;

    CenturyGothicTextView depdetailsName,depositCode ,depositBranch,apd_mob_no;
    Bundle bundle;

    public AgentPinDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_agent_pin_details, container, false);

        apiSessionHandler = new ApiSessionHandler(getContext());
        sessionHandler = new SessionHandler(getContext());


        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        bundle = getArguments();

        Log.d("bfd","=-+"+bundle.toString());
        agent_tick=(ImageView)view.findViewById(R.id.agent_tick);

        agentPin=(EditText)view.findViewById(R.id.agentPin);
        deposoitAmt=(CGEditText) view.findViewById(R.id.deposoitAmt);
        deposoitRemarks=(CGEditText)view.findViewById(R.id.deposoitRemarks);
        clientsPinEtxt=(CGEditText)view.findViewById(R.id.clientsPinEtxt);
        cd_mobile_no=(CGEditText)view.findViewById(R.id.cd_mobile_no);

        apd_mob_no=(CenturyGothicTextView) view.findViewById(R.id.apd_mob_no);

        depdetailsName=(CenturyGothicTextView)view.findViewById(R.id.depdetailsName);
        depositCode=(CenturyGothicTextView)view.findViewById(R.id.depositCode);
        depositBranch=(CenturyGothicTextView)view.findViewById(R.id.depositBranch);


        depdetailsName.setText(bundle.getString("name"));
                depositCode.setText(bundle.getString("code"));
        depositBranch.setText(bundle.getString("office"));
        apd_mob_no.setText(bundle.getString("phone"));

        demand_cross=(ImageView)view.findViewById(R.id.demand_cross);
        agent_tick.setOnClickListener(this);
        demand_cross.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId){
            case  R.id.agent_tick:
                if (Integer.parseInt(deposoitAmt.getText().toString())==0){
                    Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                    intent.putExtra("msg","Zero amount cannot be deposited");
                    startActivity(intent);
                }else {
                    sendOtpForCashDeposit();


//                    sendOtpForCashDeposit();
                }
                break;
            case R.id.demand_cross:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }


    private void  sendOtpForCashDeposit(){
        sessionHandler.showProgressDialog("Sending Request .... ");
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.get("code"));
            jsonObject.put("finger",clientsPinEtxt.getText().toString());
            jsonObject.put("amount",deposoitAmt.getText().toString());
            jsonObject.put("agentpin",agentPin.getText().toString());
            jsonObject.put("mobile",cd_mobile_no.getText().toString());
            jsonObject.put("remark",deposoitRemarks.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));

        retrofit2.Call<WithDrawlResponse> call = apiInterface.sendWithdrawRequest(apiSessionHandler.getDEPOSIT_OTP(),body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<WithDrawlResponse>() {
            @Override
            public void onResponse(Call<WithDrawlResponse> call, Response<WithDrawlResponse> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    Fragment fragment= new AgentClientTransferFragment();
                    String message = response.body().getMessage();
                    bundle.putString("agentPin",agentPin.getText().toString());
                    bundle.putString("deposoitAmt",deposoitAmt.getText().toString());
                    bundle.putString("deposoitRemarks",deposoitRemarks.getText().toString());
                    bundle.putString("clientsPin",clientsPinEtxt.getText().toString());
                    bundle.putString("cd_mobile_no",cd_mobile_no.getText().toString());
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.commit();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }else {
                    try {

                        String jsonString = response.errorBody().string();

                        Log.d("here ","--=>"+jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",jsonObject.getString("message"));
                        startActivity(intent);
                        if (jsonObject.getString("message").equals("Sorry Invalid Agent Pincode...")){
                            agentPin.setError("Invalid Pincode");
                        }else if (jsonObject.getString("message").equals("Member Authentication failed...")){
                            clientsPinEtxt.setError("Invalid Pincode");
                        }else if (jsonObject.getString("message").equals("Member Mobile No. is not registered...")){
                            cd_mobile_no.setError("Invalid Pincode");
                        }
                    } catch (Exception e) {
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",("data mistake"));
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WithDrawlResponse> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });
    }

}
