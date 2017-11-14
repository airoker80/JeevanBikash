package com.harati.jeevanbikas.Retrofit.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sameer on 11/9/2017.
 */

public class SystemApiResponseModel {
    @SerializedName("apiName")
    @Expose
    private String apiName;
    @SerializedName("baseUrl")
    @Expose
    private String baseUrl;
    @SerializedName("taskID")
    @Expose
    private Integer taskID;
    @SerializedName("task")
    @Expose
    private Object task;
    @SerializedName("changedBy")
    @Expose
    private Integer changedBy;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("aDRegisteredAt")
    @Expose
    private String aDRegisteredAt;
    @SerializedName("bSRegisteredAt")
    @Expose
    private String bSRegisteredAt;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public Object getTask() {
        return task;
    }

    public void setTask(Object task) {
        this.task = task;
    }

    public Integer getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Integer changedBy) {
        this.changedBy = changedBy;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getADRegisteredAt() {
        return aDRegisteredAt;
    }

    public void setADRegisteredAt(String aDRegisteredAt) {
        this.aDRegisteredAt = aDRegisteredAt;
    }

    public String getBSRegisteredAt() {
        return bSRegisteredAt;
    }

    public void setBSRegisteredAt(String bSRegisteredAt) {
        this.bSRegisteredAt = bSRegisteredAt;
    }
}
