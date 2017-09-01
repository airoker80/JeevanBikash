package com.harati.jeevanbikas.Helper.AutoCompleteHelper;

/**
 * Created by Sameer on 8/31/2017.
 */

public class AutoCompleteModel {
    String name,phoneNumber;
    int imageId;

    public AutoCompleteModel(String name, String phoneNumber, int imageId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
