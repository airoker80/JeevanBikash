package com.harati.jeevanbikas.Retrofit.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sameer on 10/16/2017.
 */


/*{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBMDA1MDAwMSIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTUwODEzNzkwNTk1NywiZXhwIjoxNTA4NzQyNzA1fQ.vyw8j7oLBrHbRLaWWREOML2qAYFwfmAwi7HyAQefdfjlRkQjvvyisCpWbZuI8tjFimoEqDjv1Rnzj0u5GJd1Rw",
    "code": "A0050001",
    "name": "Mira Sah",
    "branch": "Branch Office Dainiya",
    "balance": null,
    "passwordChangeReqd": true,
    "pinChangeReqd": true
}*/
public class LoginModel {
    @SerializedName("token")
    private String token;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("branch")
    private String branch;
    @SerializedName("balance")
    private Object balance;
    @SerializedName("passwordChangeReqd")
    private Boolean passwordChangeReqd;
    @SerializedName("pinChangeReqd")
    private Boolean pinChangeReqd;

    public LoginModel(String token, String code, String name, String branch, Object balance, Boolean passwordChangeReqd, Boolean pinChangeReqd) {
        this.token = token;
        this.code = code;
        this.name = name;
        this.branch = branch;
        this.balance = balance;
        this.passwordChangeReqd = passwordChangeReqd;
        this.pinChangeReqd = pinChangeReqd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Object getBalance() {
        return balance;
    }

    public void setBalance(Object balance) {
        this.balance = balance;
    }

    public Boolean getPasswordChangeReqd() {
        return passwordChangeReqd;
    }

    public void setPasswordChangeReqd(Boolean passwordChangeReqd) {
        this.passwordChangeReqd = passwordChangeReqd;
    }

    public Boolean getPinChangeReqd() {
        return pinChangeReqd;
    }

    public void setPinChangeReqd(Boolean pinChangeReqd) {
        this.pinChangeReqd = pinChangeReqd;
    }
}
