package com.harati.jeevanbikas.Login;

/**
 * Created by Sameer on 9/14/2017.
 */

public class LoginResponseModel {
    String token,code,branch,balance;

    public LoginResponseModel(String token, String code, String branch, String balance) {
        this.token = token;
        this.code = code;
        this.branch = branch;
        this.balance = balance;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
