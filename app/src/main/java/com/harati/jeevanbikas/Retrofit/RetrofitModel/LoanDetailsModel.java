package com.harati.jeevanbikas.Retrofit.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sameer on 10/25/2017.
 */

public class LoanDetailsModel {

    @SerializedName("loanTypeID")
    @Expose
    private Integer loanTypeID;
    @SerializedName("loanType")
    @Expose
    private String loanType;

    public Integer getLoanTypeID() {
        return loanTypeID;
    }

    public void setLoanTypeID(Integer loanTypeID) {
        this.loanTypeID = loanTypeID;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
}
