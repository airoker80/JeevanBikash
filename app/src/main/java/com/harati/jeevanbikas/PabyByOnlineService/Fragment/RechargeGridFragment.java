package com.harati.jeevanbikas.PabyByOnlineService.Fragment;

import android.database.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.PabyByOnlineService.Adapter.JBParentRVAdapter;
import com.harati.jeevanbikas.PabyByOnlineService.Adapter.ParentRecycleViewAdapter;
import com.harati.jeevanbikas.PabyByOnlineService.Model.ServiceCatagoryDetails;
import com.harati.jeevanbikas.PabyByOnlineService.Model.ServiceType;
import com.harati.jeevanbikas.PabyByOnlineService.config.PayByOnlineConfig;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import com.paybyonline.ebiz.serverdata.PboServerRequestHandler;
//import com.paybyonline.ebiz.serverdata.PboServerRequestListener;

/**
 * Created by Anish on 9/26/2016.
 */
public class RechargeGridFragment extends Fragment {

    SessionHandler sessionHandler;
    CoordinatorLayout coordinatorLayout;
    RecyclerView rechargeDetailsListView;
    ParentRecycleViewAdapter parentRecycleViewAdapter;
    JBParentRVAdapter jbParentRVAdapter;
    // URL to get JSON Array
    private List<ServiceType> serviceTypeList;
    private List<ServiceCatagoryDetails> serviceCategoryList;
    private static String url;

    String servCatName = "";
    String servCatId = "";
    String servTypeName = "";
    String servTypeId = "";
    String scstId = "";

    Retrofit retrofit;
    ApiInterface apiInterface;

    //    PboServerRequestHandler handler;
    FloatingActionButton rechargeDetailReport;


    public RechargeGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recharge_grid,
                container, false);


        retrofit = MyApplication.getRetrofitInstance(PayByOnlineConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
        rechargeDetailsListView = (RecyclerView) view
                .findViewById(R.id.rechargeDetailsListView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rechargeDetailsListView.setHasFixedSize(true);
        rechargeDetailsListView.setLayoutManager(mLayoutManager);

        sessionHandler = new SessionHandler(getContext());
        url = PayByOnlineConfig.SERVER_URL;

        Bundle bundle = getArguments();
        if (bundle != null) {

            servCatName = bundle.getString("servCatName");
            servCatId = bundle.getString("servCatId");
            servTypeName = bundle.getString("servTypeName");
            servTypeId = bundle.getString("servTypeId");
            scstId = bundle.getString("serviceCategory");

            Log.e("bundle grid ","==>"+bundle.toString());
        }

        rechargeDetailReport = (FloatingActionButton) view.findViewById(R.id.rechargeDetailReport);

        getAvailableServices();

        return view;
    }

    public void getAvailableServices() {
        Log.v("thread1","thread ===>"+ String.valueOf(Thread.currentThread().getId()));

        Map<String, String> params = new HashMap<>();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "getRechargeServiceDetails");
        params.put("servCatId", servCatId);
        Log.d("serviceID===", servCatId);
        params.put("userCode", sessionHandler.getUserCode());
        params.put("authenticationCode", sessionHandler.getAuthCode());
        params.put("accessToken", "");

        sessionHandler.showProgressDialog("Processing ..");
        Call<ResponseBody> call = apiInterface.getRechargeServiceDetails(PayByOnlineConfig.SERVER_URL+"authenticateAppUser"
                                                                               ,params);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("thread2","thread ===>"+ String.valueOf(Thread.currentThread().getId()));
                sessionHandler.hideProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    io.reactivex.Observable.just(jsonObject)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<JSONObject>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    Log.v("onSubscribe","thread ===>"+ String.valueOf(Thread.currentThread().getId()));
                                    try {
                                        handleGetAvailableServicesResponse(jsonObject);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onNext(JSONObject value) {
                                    Log.v("onNext","thread ===>"+ String.valueOf(Thread.currentThread().getId()));

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.v("thread2","thread ===>"+ String.valueOf(Thread.currentThread().getId()));
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
//                    handleGetAvailableServicesResponse(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });
    }


    public void handleGetAvailableServicesResponse(JSONObject response) throws JSONException {

        try {
            JSONArray jsonData = response.getJSONArray("data");
            Log.i("data", "" + jsonData);

            serviceCategoryList = new ArrayList<ServiceCatagoryDetails>();

            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject object = jsonData.getJSONObject(i);

                JSONArray jsonData2 = object.getJSONArray("serviceTypeList");

                serviceTypeList = new ArrayList<ServiceType>();
                for (int j = 0; j < jsonData2.length(); j++) {
                    JSONObject object2 = jsonData2.getJSONObject(j);

                    serviceTypeList.add(new ServiceType(object2.getString("startsWith"), object2.getString("minVal"),
                            object2.getString("maxVal"), object2.getString("iLabel"), object2.getString("isPinRechargeService"),
                            object2.getString("categoryLength"), object2.getString("serviceTypeName"),
                            object2.getString("serviceTypeId"), object2.getString("pCountry"),
                            PayByOnlineConfig.BASE_URL + "CategoryTypeLogo/" +
                                    Uri.encode((object2.has("logoName") ? object2.getString("logoName") : "")),
                            object2.getString("scstAmountType"),
                            object2.getString("currency"), object2.getString("scstAmountValue"), object2.getString("isProductEnable"), object2.getString("enablePromoCode")));
                }

                serviceCategoryList.add(new ServiceCatagoryDetails(object.getString("serviceCategoryId")
                        , object.getString("serviceCategoryName"), PayByOnlineConfig.BASE_URL + "ServiceLogo/categoryTypeLogo/" +
                        Uri.encode(object.getString("serviceCategoryLogo"))
                        , serviceTypeList));

            }

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rechargeDetailsListView.setHasFixedSize(true);
            rechargeDetailsListView.setLayoutManager(mLayoutManager);

/*            jbParentRVAdapter = new JBParentRVAdapter(getActivity(),serviceCategoryList, R.layout.child_recycleview_layout, coordinatorLayout, servTypeId);

            rechargeDetailsListView.setAdapter(jbParentRVAdapter);*/

            parentRecycleViewAdapter = new ParentRecycleViewAdapter(getActivity(),serviceCategoryList, R.layout.child_recycleview_layout, coordinatorLayout, servTypeId);

            rechargeDetailsListView.setAdapter(parentRecycleViewAdapter);

        } catch (Exception e) {
            Log.i("Exception", "" + e);
        }
    }

    @Override
    public void onResume() {


        super.onResume();
    }
}
