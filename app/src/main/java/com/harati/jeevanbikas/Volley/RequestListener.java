package com.harati.jeevanbikas.Volley;


/**
 * Created by User on 9/20/2017.
 */

public interface RequestListener {
    void onSuccess(String response);
    void onFailure(String response);
}
