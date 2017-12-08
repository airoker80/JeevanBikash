package com.harati.jeevanbikas.FundTransfer;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Enrollment.EnrollmentActivity;
import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.AutoCompleteHelper.CustomACTextView;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.Helper.imageLoader.ImageZoomActivity;
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
    ImageView image;
    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler;
    CenturyGothicTextView fundName, fundmemberId, fundOffice, senderMid;
    CenturyGothicTextView fundNameBenf, fundmemberIdBenf, fundOfficeBenf, mobileBenf;
    EditText benf_ft_etx;
    LinearLayout down_ll, mid_ll;
    ImageView fund_tick, fund_cross, fst_mem_ft_photo, ben_photo_ft;
    Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_fund_details, container, false);
        bundle = getArguments();

        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        apiSessionHandler = new ApiSessionHandler(getContext());
        sessionHandler = new SessionHandler(getContext());


        down_ll = (LinearLayout) view.findViewById(R.id.down_ll);
        mid_ll = (LinearLayout) view.findViewById(R.id.mid_ll);

        fundName = (CenturyGothicTextView) view.findViewById(R.id.fundName);
        fundmemberId = (CenturyGothicTextView) view.findViewById(R.id.fundmemberId);
        fundOffice = (CenturyGothicTextView) view.findViewById(R.id.fundOffice);
        senderMid = (CenturyGothicTextView) view.findViewById(R.id.senderMid);
        mobileBenf = (CenturyGothicTextView) view.findViewById(R.id.mobileBenf);

        fundNameBenf = (CenturyGothicTextView) view.findViewById(R.id.fundNameBenf);
        fundmemberIdBenf = (CenturyGothicTextView) view.findViewById(R.id.fundmemberIdBenf);
        fundOfficeBenf = (CenturyGothicTextView) view.findViewById(R.id.fundOfficeBenf);

        fst_mem_ft_photo = (ImageView) view.findViewById(R.id.fst_mem_ft_photo);
        ben_photo_ft = (ImageView) view.findViewById(R.id.ben_photo_ft);

        benf_ft_etx = (EditText) view.findViewById(R.id.benf_ft_etx);

        if (bundle.get("goto").equals("beif")) {
            fundName.setText(bundle.getString("name"));
            fundmemberId.setText(bundle.getString("code"));
            fundOffice.setText(bundle.getString("office"));
            senderMid.setText(bundle.getString("phone"));
            try {
                String[] splitString = bundle.getString("photo").split(",");
                String base64Photo = splitString[1];
                byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
                Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                fst_mem_ft_photo.setImageBitmap(userPhoto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (bundle.get("goto").equals("info")) {
            fundName.setText(bundle.getString("nameBenificiary"));
            fundmemberId.setText(bundle.getString("codeBenificiary"));
            fundOffice.setText(bundle.getString("officeBenificiary"));
            Log.e("fad", "===>" + bundle.getString("phoneBenificiary"));
            mobileBenf.setText(bundle.getString("phoneBenificiary"));
        }

        fund_tick = (ImageView) view.findViewById(R.id.fund_tick);
        fund_cross = (ImageView) view.findViewById(R.id.fund_cross);
        image = (ImageView) view.findViewById(R.id.image);

        fund_tick.setOnClickListener(this);
        fund_cross.setOnClickListener(this);
        image.setOnClickListener(this);
        fst_mem_ft_photo.setOnClickListener(this);
        ben_photo_ft.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.fund_tick:
                if (mid_ll.getVisibility() != View.GONE) {
                    if (benf_ft_etx.getText().toString().equals(fundmemberId.getText().toString())) {
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg", "Same Member Codes");
                        startActivity(intent);
                    } else {
                        if (benf_ft_etx.getText().toString().equals("")){
                            benf_ft_etx.setError("This field is empty");
                        }else {
                            Log.e("Ok", "okkkkk" + benf_ft_etx.getText().toString());
                            getMemberList(benf_ft_etx.getText().toString());
                        }
                    }

                } else {
                    Fragment fragment = new FundInfoFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.fund_cross:
                confirmBack();
                break;
            case R.id.image:
                confirmBack();
                break;
            case R.id.fst_mem_ft_photo:
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
                break;
            case R.id.ben_photo_ft:
                try {
                    if (!bundle.getString("photoBenificiary").toString().equals(null)) {
                        Intent intent = new Intent(getContext(), ImageZoomActivity.class);
                        intent.putExtra("photo", bundle.getString("photoBenificiary"));
//                intent.putExtra("imageUrl",imageUrl);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "No Image Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void getMemberList(String mobile_no) {
        Log.e("Ok", "okkkkk");
        sessionHandler.showProgressDialog("sending Request ....");
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<SearchModel> call = apiInterface.sendMemberSearchRequest(apiSessionHandler.getMEMBER_SEARCH(), mobile_no, sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json", apiSessionHandler.getAgentCode());
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                sessionHandler.hideProgressDialog();
//                Log.d("DADAD0","ADA");
                if (String.valueOf(response.code()).equals("200")) {
                    mid_ll.setVisibility(View.GONE);
                    down_ll.setVisibility(View.VISIBLE);
//                    bundle.putString("goto","info");
                    bundle.putString("codeBenificiary", response.body().getCode());
                    bundle.putString("nameBenificiary", response.body().getName());
                    bundle.putString("officeBenificiary", response.body().getOffice());
//                    bundle.putString("office",response.body().getCode());
                    bundle.putString("photoBenificiary", response.body().getPhoto());
                    bundle.putString("phoneBenificiary", response.body().getMobileno());

                    fundNameBenf.setText(response.body().getName());
                    fundmemberIdBenf.setText(response.body().getCode());
                    fundOfficeBenf.setText(response.body().getOffice());
                    mobileBenf.setText(response.body().getMobileno());
                    try {
                        String[] splitString = response.body().getPhoto().split(",");
                        String base64Photo = splitString[1];
                        byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
                        Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        ben_photo_ft.setImageBitmap(userPhoto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        String jsonString = response.errorBody().string();

                        Log.d("here ", "--=>" + jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg", jsonObject.getString("message"));
                        startActivity(intent);
                    } catch (Exception e) {
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg", "Wrong Credential");
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


    void confirmBackCross() {
        Log.e("backpressed", "bp");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_ask_permission, null);
        TextView askPermission = (TextView) view.findViewById(R.id.askPermission);
        askPermission.setText("Are you Sure you want to Cancel??");
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("Yes", null)
                .setNegativeButton("CANCEL", null)
                .setTitle("Are you Sure you want Cancel?")
                .create();

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                if (mid_ll.getVisibility() == View.VISIBLE) {
                    ((FundTransferActivity) getActivity()).backpress();
                } else {
                    mid_ll.setVisibility(View.VISIBLE);
                    down_ll.setVisibility(View.GONE);
                }
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }

    void confirmBack() {
        Log.e("backpressed", "bp");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_ask_permission, null);
        TextView askPermission = (TextView) view.findViewById(R.id.askPermission);
        askPermission.setText("Are you Sure you want to go back??");
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("Yes", null)
                .setNegativeButton("CANCEL", null)
                .setTitle("Are you Sure you want to go back?")
                .create();

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                if (mid_ll.getVisibility() == View.VISIBLE) {
                    ((FundTransferActivity) getActivity()).backpress();
                    Log.e("backpressed", "bp");
                } else {
                    Log.e("backpressed", "gone");
                    mid_ll.setVisibility(View.VISIBLE);
                    down_ll.setVisibility(View.GONE);
                }
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }

}
