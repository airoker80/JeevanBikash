package com.harati.jeevanbikas.ActivityLogPackage.dailyLogs;

/**
 * Created by Sameer on 10/31/2017.
 */

public class DailyLogModel {
    String time ,title,amount;

    public DailyLogModel(String time, String title, String amount) {
        this.time = time;
        this.title = title;
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
