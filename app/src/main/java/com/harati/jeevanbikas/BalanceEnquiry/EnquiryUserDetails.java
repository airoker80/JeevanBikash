package com.harati.jeevanbikas.BalanceEnquiry;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
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
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.Helper.imageLoader.ImageZoomActivity;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.EnquiryResponseModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnquiryUserDetails extends Fragment implements View.OnClickListener {
    ApiSessionHandler apiSessionHandler;
    SessionHandler sessionHandler;
    ApiInterface apiInterface;
    Retrofit retrofit;
    ImageView enquiry_submit, enquiry_cross, enquiryUserPhoto;
    TextView memberIdnnumber, branchName, accNoDetails, enquiryUserName, memberId, branch, accNo, be_mob_no;
    EditText client_pin_entry;
    String code, name, office, photo;

    public EnquiryUserDetails() {
        // Required empty public constructor
    }
//.setTypeface(MainActivity.centuryGothic);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enquiry_user_details, container, false);

        Bundle bundle = getArguments();

        code = bundle.getString("code");
        name = bundle.getString("name");
        office = bundle.getString("office");
        photo = bundle.getString("photo");

        enquiry_submit = (ImageView) view.findViewById(R.id.enquiry_submit);
        enquiryUserPhoto = (ImageView) view.findViewById(R.id.enquiryUserPhoto);
        enquiry_cross = (ImageView) view.findViewById(R.id.enquiry_cross);

        client_pin_entry = (EditText) view.findViewById(R.id.client_pin_entry);

        enquiry_submit.setOnClickListener(this);
        enquiry_cross.setOnClickListener(this);


        memberId = (TextView) view.findViewById(R.id.memberId);
        branch = (TextView) view.findViewById(R.id.branch);
        accNo = (TextView) view.findViewById(R.id.accNo);
        be_mob_no = (TextView) view.findViewById(R.id.be_mob_no);
//        enquiryUserName = (TextView) view.findViewById(R.id.enquiryUserName);
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        sessionHandler = new SessionHandler(getContext());
        apiSessionHandler = new ApiSessionHandler(getContext());

        memberIdnnumber = (TextView) view.findViewById(R.id.memberIdnnumber);
        branchName = (TextView) view.findViewById(R.id.branchName);
//        accNoDetails = (TextView) view.findViewById(R.id.accNoDetails);
        enquiryUserName = (TextView) view.findViewById(R.id.enquiryUserName);

        memberId.setTypeface(MainActivity.centuryGothic);
        branch.setTypeface(MainActivity.centuryGothic);
        accNo.setTypeface(MainActivity.centuryGothic);
        memberIdnnumber.setTypeface(MainActivity.centuryGothic);
        branchName.setTypeface(MainActivity.centuryGothic);
//        accNoDetails.setTypeface(MainActivity.centuryGothic);
        enquiryUserName.setTypeface(MainActivity.centuryGothic);

        enquiryUserName.setText(name);
        memberIdnnumber.setText(code);
        branchName.setText(office);
//        accNoDetails.setText(name);
        be_mob_no.setText(bundle.getString("phone"));

        try {
//            Picasso.with(getContext()).load(photo).into(enquiryUserPhoto);
            String[] splitString = photo.split(",");
            String base64Photo = splitString[1];
            byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
            Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            enquiryUserPhoto.setImageBitmap(userPhoto);

            Log.e("photo", "--->" + photo);

        } catch (Exception e) {
            e.printStackTrace();
        }


        enquiryUserPhoto.setOnClickListener(v -> {
            try {
                if (!bundle.getString("photo").toString().equals(null)) {
                    Intent intent = new Intent(getContext(), ImageZoomActivity.class);
                    intent.putExtra("photo", bundle.getString("photo"));
//                intent.putExtra("imageUrl",imageUrl);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "No Image Found", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.enquiry_submit:
/*                Fragment fragment = new NewFingerPrintFragment();
                Bundle bundle = new Bundle();
                bundle.putString("memberId",code);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();*/
                if (client_pin_entry.getText().toString().equals("")) {
                    client_pin_entry.setError("Member Pin is empty");
                } else {
                    sendBalanceEnquiryRequest();
                }

                break;
            case R.id.enquiry_cross:
                Intent intent =new Intent(getContext(), MainActivity.class);
                intent.putExtra("msg","x");
                startActivity(intent);
                break;
        }
    }

    public void sendBalanceEnquiryRequest() {

        final JSONObject jsonObject = new JSONObject();
//      startActivity(new Intent(InitialResetPassword.this,ResetPassword.class));
        JSONArray jsonArray = new JSONArray();
        try {
            Log.e("agentCode", "ac" + sessionHandler.getAgentCode());
            jsonObject.put("membercode", code);
            jsonObject.put("finger", client_pin_entry.getText().toString());

            jsonArray.put(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));
        io.reactivex.Observable<Response<EnquiryResponseModel>> call = apiInterface.sendBalanceRequest(apiSessionHandler.getBALANCE_ENQUIRY(), body,
                sessionHandler.getAgentToken(), "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json", apiSessionHandler.getAgentCode());

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<EnquiryResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        sessionHandler.showProgressDialog("Sending Request ....");
                    }
                    @Override
                    public void onNext(Response<EnquiryResponseModel> value) {
                        if (String.valueOf(value.code()).equals("200")) {
                            if (value.body().getStatus().equals("Success")) {
                                Intent intent = new Intent(getContext(), DialogActivity.class);
                                intent.putExtra("msg", value.body().getMessage());
                                intent.putExtra("print", "print");
                                intent.putExtra("printMsg", value.body().getPrint());
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                if (value.body().getMessage().equals("Member Authentication failed...")) {
                                    client_pin_entry.setError("Worng member Pin");
                                }
                                Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                                intent.putExtra("msg", value.body().getMessage());
                                startActivity(intent);
                            }
                        } else {
                            try {

                                String jsonString = value.errorBody().string();

                                Log.d("here ", "--=>" + jsonString);

                                JSONObject jsonObject = new JSONObject(jsonString);
                                Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                                intent.putExtra("msg", jsonObject.getString("message"));
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        sessionHandler.hideProgressDialog();
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        sessionHandler.hideProgressDialog();
                    }
                });
        /*call.enqueue(new Callback<SuccesResponseModel>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<SuccesResponseModel> call, Response<SuccesResponseModel> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    if (response.body().getStatus().equals("Success")){
                        Intent intent = new Intent(getContext(),DialogActivity.class);
                        intent.putExtra("msg",response.body().getMessage());
                        startActivity(intent);
                        getActivity().finish();
                    }else {
                        if (response.body().getMessage().equals("Member Authentication failed...")){
                            client_pin_entry.setError("Worng member Pin");
                        }
                        Intent intent = new Intent(getContext(),ErrorDialogActivity.class);
                        intent.putExtra("msg",response.body().getMessage());
                        startActivity(intent);
                    }
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

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFailure(Call<SuccesResponseModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }


}
