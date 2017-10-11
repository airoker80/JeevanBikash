package com.harati.jeevanbikas.Helper.AutoCompleteHelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

/**
 * Created by Sameer on 10/11/2017.
 */

public class CustomACTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    public CustomACTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "cg.ttf");
        this.setTypeface(face);
    }

    public CustomACTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "cg.ttf");
        this.setTypeface(face);
    }

    public CustomACTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "cg.ttf");
        this.setTypeface(face);
    }
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }
}
