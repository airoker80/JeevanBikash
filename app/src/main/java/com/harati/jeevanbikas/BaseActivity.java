package com.harati.jeevanbikas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Sameer on 12/15/2017.
 */

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Handler handler;
    Runnable r;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler;
    ApiSessionHandler apiSessionHandler;
    public DrawerLayout drawer;
    public ImageView drawer_icon;
    public CircleImageView user_logo;
    public TextView nav_username,agent_code,branch_office ,agent_balance,trasaction_odlimit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sessionHandler = new SessionHandler(this);
        apiSessionHandler = new ApiSessionHandler(this);
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        handler = new Handler();
        r=() -> logout();
        startHandler();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_icon = (ImageView) findViewById(R.id.drawer_icon);

//        nav_username.setText("adadad");//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        drawer_icon.setOnClickListener(v -> {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }else {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        nav_username = (TextView) header.findViewById(R.id.nav_username);
        user_logo = (CircleImageView) header.findViewById(R.id.user_logo);
        agent_code = (TextView) header.findViewById(R.id.agent_code);
        branch_office = (TextView) header.findViewById(R.id.branch_office);
        agent_balance = (TextView) header.findViewById(R.id.agent_balance);
        trasaction_odlimit = (TextView) header.findViewById(R.id.trasaction_odlimit);

    }
    @Override
    public void onUserInteraction() {
        stopHandler();
        startHandler();
    }
    void logout(){
        io.reactivex.Observable<SuccesResponseModel> call = apiInterface.sendLogoutRequest(sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SuccesResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        sessionHandler.showProgressDialog("Logging Out..");
                    }

                    @Override
                    public void onNext(SuccesResponseModel value) {
                        Log.v("logout",value.getMessage()+value.getStatus());
                    }

                    @Override
                    public void onError(Throwable e) {
                        sessionHandler.hideProgressDialog();
                        sessionHandler.logoutUser();
                    }

                    @Override
                    public void onComplete() {
                        sessionHandler.hideProgressDialog();
                        sessionHandler.logoutUser();
                    }
                });
    }
    public void startHandler() {
        Log.v("start","handler");
        handler.postDelayed(r, (long) (10*1000));
//        handler.postDelayed(r, (long) (2*60*1000));
    }
    public void stopHandler() {
        Log.e("MainHandler","Stoped");
        handler.removeCallbacks(r);
    }
    @Override
    protected void onDestroy() {
        Log.v("dadad","destroyed");
        stopHandler();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.v("stoped","for a while");
        stopHandler();
        super.onStop();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setNav(String image_code,String code,String name,String branch,String balance,String odlimit){

        nav_username.setText(name);
        agent_code.setText("Code : "+code);
        branch_office.setText(branch);
        agent_balance.setText("Balance : रु "+balance);
        trasaction_odlimit.setText("Odlimit : रु "+odlimit);

        Observable.just(image_code)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    try {
                        String[] splitString = image_code.split(",");
                        String base64Photo = splitString[1];
                        byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
                        Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        user_logo.setImageBitmap(userPhoto);

                        Log.e("photo", "--->" + image_code);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).subscribe();



    }


}
