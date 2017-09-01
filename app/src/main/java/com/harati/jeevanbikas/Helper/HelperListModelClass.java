package com.harati.jeevanbikas.Helper;

/**
 * Created by Sameer on 9/1/2017.
 */

public class HelperListModelClass {
    String key ,value;

    public HelperListModelClass(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
