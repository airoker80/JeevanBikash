package com.harati.jeevanbikas.Retrofit.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sameer on 10/23/2017.
 */

public class WithDrawlResponse {
    @SerializedName("agentCode")
    @Expose
    private String agentCode;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("print")
    @Expose
    private String print;

    @SerializedName("balance")
    @Expose
    private Double balance;

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
