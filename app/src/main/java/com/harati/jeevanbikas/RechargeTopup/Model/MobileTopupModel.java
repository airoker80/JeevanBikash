package com.harati.jeevanbikas.RechargeTopup.Model;

/**
 * Created by Sameer on 9/4/2017.
 */

public class MobileTopupModel {
    String topupName;
    int imageId;

    public MobileTopupModel(String topupName, int imageId) {
        this.topupName = topupName;
        this.imageId = imageId;
    }

    public String getTopupName() {
        return topupName;
    }

    public void setTopupName(String topupName) {
        this.topupName = topupName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
