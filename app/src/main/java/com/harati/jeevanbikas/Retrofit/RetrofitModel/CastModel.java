package com.harati.jeevanbikas.Retrofit.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sameer on 11/24/2017.
 */

public class CastModel {
    @SerializedName("memberCast")
    @Expose
    private String memberCast;

    public String getMemberCast() {
        return memberCast;
    }

    public void setMemberCast(String memberCast) {
        this.memberCast = memberCast;
    }
}
