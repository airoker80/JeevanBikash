package com.harati.jeevanbikas.Helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

/**
 * Created by Sameer on 10/11/2017.
 */

public class CGEditText extends android.support.v7.widget.AppCompatEditText {
    public CGEditText(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "cg.ttf");
        this.setTypeface(face);
    }

    public CGEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "cg.ttf");
        this.setTypeface(face);
    }

    public CGEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "cg.ttf");
        this.setTypeface(face);
    }
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }



}
