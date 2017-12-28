package com.harati.jeevanbikas.FundTransfer;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.harati.jeevanbikas.Helper.imageLoader.ImageZoomActivity;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.TransferModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.WithDrawlResponse;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferTransactionDetailFragment extends Fragment {

    int otpCount = 0;
    ImageButton resend_otp;
    public String otpValue = "";
    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler;
    LinearLayout topPanel2;
    CenturyGothicTextView name, memberIdnnumber, branchName, shownDepositAmt, amountType, sendOtpAgain;
    CenturyGothicTextView nameBeneficiary, memberIdnnumberBeneficiary, branchNameBeneficiary, title;
    PinEntryEditText act_otp_tf;
    ImageView agent_client_tick, demand_cross, image, benif_image, act_mem_photo;
    Bundle bundle;

    public TransferTransactionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = getArguments();

        Log.d("bundle", "==00--+>" + bundle.toString());
        apiSessionHandler = new ApiSessionHandler(getContext());
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        sessionHandler = new SessionHandler(getContext());
        View view = inflater.inflate(R.layout.fragment_agent_client_transfer, container, false);


        topPanel2 = (LinearLayout) view.findViewById(R.id.topPanel2);
        topPanel2.setVisibility(View.VISIBLE);

        name = (CenturyGothicTextView) view.findViewById(R.id.name);
        memberIdnnumber = (CenturyGothicTextView) view.findViewById(R.id.memberIdnnumber);
        branchName = (CenturyGothicTextView) view.findViewById(R.id.branchName);

        nameBeneficiary = (CenturyGothicTextView) view.findViewById(R.id.nameBeneficiary);
        memberIdnnumberBeneficiary = (CenturyGothicTextView) view.findViewById(R.id.memberIdnnumberBeneficiary);
        branchNameBeneficiary = (CenturyGothicTextView) view.findViewById(R.id.branchNameBeneficiary);
        title = (CenturyGothicTextView) view.findViewById(R.id.title);

        act_otp_tf = (PinEntryEditText) view.findViewById(R.id.act_otp_tf);

        shownDepositAmt = (CenturyGothicTextView) view.findViewById(R.id.shownDepositAmt);
        title.setText("Fund Transfer");
        amountType = (CenturyGothicTextView) view.findViewById(R.id.amountType);
        sendOtpAgain = (CenturyGothicTextView) view.findViewById(R.id.sendOtpAgain);

        amountType.setText("Transfer amount");
        shownDepositAmt.setText(getResources().getString(R.string.currency_np) + " " + bundle.getString("transfer_amount"));

        name.setText(bundle.getString("name") + "(Sender)");
        memberIdnnumber.setText(bundle.getString("code"));
        branchName.setText(bundle.getString("office"));

        nameBeneficiary.setText(bundle.getString("nameBenificiary") + " (Reciver)");
        memberIdnnumberBeneficiary.setText(bundle.getString("codeBenificiary"));
        branchNameBeneficiary.setText(bundle.getString("officeBenificiary"));

//        sendOtpForFundTransfer();
        resend_otp = (ImageButton) view.findViewById(R.id.resend_otp);
        image = (ImageView) view.findViewById(R.id.image);
        benif_image = (ImageView) view.findViewById(R.id.benif_image);
        act_mem_photo = (ImageView) view.findViewById(R.id.act_mem_photo);

        agent_client_tick = (ImageView) view.findViewById(R.id.agent_client_tick);
        demand_cross = (ImageView) view.findViewById(R.id.demand_cross);

        try {
            String[] splitString = bundle.getString("photo").split(",");
            String base64Photo = splitString[1];
            byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
            Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            act_mem_photo.setImageBitmap(userPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] splitString = bundle.getString("photoBenificiary").split(",");
            String base64Photo = splitString[1];
            byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
            Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            benif_image.setImageBitmap(userPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }


        act_mem_photo.setOnClickListener(v -> {
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

        benif_image.setOnClickListener(v -> {
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
        });
        agent_client_tick.setOnClickListener(v -> {

            if (!act_otp_tf.getText().toString().equals("")) {
                sendTransferPostRequest();
            } else {
                act_otp_tf.setError("Enter OTP first");
            }
//                startActivity(new Intent(getContext(), DialogActivity.class));
        });
        demand_cross.setOnClickListener(view1 -> confirmBackCross());

//        new Thread(task1).start();

//        getActivity().runOnUiThread(task1);
        Observable.fromCallable(callable).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                doOnSubscribe(disposable ->
                        resend_otp.setEnabled(false)
                ).
                subscribe(getDisposableObserver());


        image.setOnClickListener(view1 -> {
            confirmBack();
        });
        sendOtpAgain.setOnClickListener(v -> {
            final AlertDialog builder = new AlertDialog.Builder(getContext())
                    .setPositiveButton("OK", null)
                    .setNegativeButton("CANCEL", null)
                    .setTitle("Send Otp request Again?")
                    .create();
            builder.setOnShowListener(dialog -> {

                final Button btnAccept = builder.getButton(
                        AlertDialog.BUTTON_POSITIVE);

                btnAccept.setOnClickListener(v1 -> {
//                    sendOtpForFundTransfer();
                    sendOtpForFundTransfer();
                    builder.dismiss();

                });

                final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

                btnDecline.setOnClickListener(v1 -> builder.dismiss()
                );
            });
            builder.show();
        });

        resend_otp.setOnClickListener(view1 -> sendOtpForFundTransfer());
        return view;
    }


    private void sendOtpForFundTransfer() {
        sessionHandler.showProgressDialog("Sending Request .... ");
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("membercode", bundle.get("code"));
            jsonObject.put("finger", bundle.get("fiClienttPin"));
            jsonObject.put("amount", bundle.getString("transfer_amount"));
            jsonObject.put("agentpin", bundle.getString("transfer_pin"));
            jsonObject.put("mobile", bundle.get("transfer_mobile"));
            jsonObject.put("beneficiary", bundle.getString("transfer_beneficiary_no"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        retrofit2.Call<WithDrawlResponse> call = apiInterface.sendWithdrawRequest(apiSessionHandler.getFUND_TRANSFER_OTP(), body,
                sessionHandler.getAgentToken(), "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json", apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<WithDrawlResponse>() {
            @Override
            public void onResponse(Call<WithDrawlResponse> call, Response<WithDrawlResponse> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")) {
                    String message = response.body().getMessage();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                    getOtpValue();
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
                        intent.putExtra("msg", ("data mistake"));
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


    private void getOtpValue() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_get_otp, null);
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)
                .setView(view)
                .setTitle("Enter Otp")
                .create();

        CGEditText getOpt = (CGEditText) view.findViewById(R.id.getOpt);

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                otpValue = getOpt.getText().toString();
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss()
            );
        });
        builder.show();
    }

    void sendTransferPostRequest() {
        sessionHandler.showProgressDialog("sending Request ... ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("membercode", bundle.getString("code"));
            jsonObject.put("finger", "1234");
            jsonObject.put("amount", bundle.getString("transfer_amount"));
            jsonObject.put("agentpin", bundle.getString("transfer_pin"));
            jsonObject.put("beneficiary", bundle.getString("transfer_beneficiary_no"));
            jsonObject.put("otp", act_otp_tf.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));
        retrofit2.Call<TransferModel> call = apiInterface.sendFundTransferRequest(apiSessionHandler.getFUND_TRANSFER(), body,
                sessionHandler.getAgentToken(), "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json", apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<TransferModel>() {
            @Override
            public void onResponse(Call<TransferModel> call, Response<TransferModel> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")) {
                    getActivity().finish();
                    Intent intent = new Intent(getContext(), DialogActivity.class);
                    intent.putExtra("msg", response.body().getMessage());
                    intent.putExtra("print","print");
                    startActivity(intent);
                } else {
                    try {
                        if (otpCount < 3) {
                            otpCount++;
                            String jsonString = response.errorBody().string();
                            Log.d("here ", "--=>" + jsonString);
                            JSONObject jsonObject = new JSONObject(jsonString);
                            Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                            intent.putExtra("msg", jsonObject.getString("message"));
                            startActivity(intent);
                            act_otp_tf.setText("");
                        } else {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.putExtra("msg", "x");
                            startActivity(intent);
                            Toast.makeText(getContext(), "Too many entries of wrong otp", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<TransferModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent(getContext(), DialogActivity.class);
                intent.putExtra("msg", "Connection Error");
                startActivity(intent);
            }
        });
    }

    private DisposableObserver<String> getDisposableObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onComplete() {
                resend_otp.setEnabled(true);
            }

            @Override
            public void onError(Throwable e) {
                resend_otp.setEnabled(true);
            }

            @Override
            public void onNext(String message) {
            }
        };
    }


    @Override
    public void onDestroyView() {
        interrupted();
        super.onDestroyView();
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
                ((FundTransferActivity) getActivity()).backpress();
                Log.e("backpressed", "bp");
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }

    void confirmBackCross() {
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
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("msg", "x");
                startActivity(intent);
                Log.e("backpressed", "bp");
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }

    Callable callable = () -> {
        SystemClock.sleep(60000);
        return null;
    };
}
