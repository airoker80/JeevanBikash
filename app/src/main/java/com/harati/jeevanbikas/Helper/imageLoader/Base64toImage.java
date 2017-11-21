package com.harati.jeevanbikas.Helper.imageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.nio.charset.StandardCharsets;

/**
 * Created by Sameer on 11/15/2017.
 */

public class Base64toImage {
    Context context;
    Bitmap bitmap;

    public Base64toImage(Context context, Bitmap bitmap) {
        this.context = context;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmapImage(String base64Code){
        byte [] decodeImage = Base64.decode(base64Code,Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
        return decodedImage;
    }
}
