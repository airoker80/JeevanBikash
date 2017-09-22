package com.harati.jeevanbikas.Helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.harati.jeevanbikas.MainPackage.MainActivity;

/**
 * Created by Sameer on 9/19/2017.
 */

public class CenturyGothicTextView  extends android.support.v7.widget.AppCompatTextView{

    public CenturyGothicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface centuryGothic=Typeface.createFromAsset(context.getAssets(), "cg.ttf");
        this.setTypeface(MainActivity.centuryGothic);
    }

    public CenturyGothicTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setTypeface(MainActivity.centuryGothic);
    }

    public CenturyGothicTextView(Context context) {
        super(context);
        this.setTypeface(MainActivity.centuryGothic);
    }
}
