package com.example.root.metr.root;

import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

public abstract class BaseActivityWithToolbar extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initToolbar(Toolbar toolbar,String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void makeToolbarTextOneLine(TextView textView){
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSingleLine(true);
        textView.setMarqueeRepeatLimit(-1);
        textView.setSelected(true);
    }

    public void setColorHomeButton(Toolbar toolbar,int color){
        DrawableCompat.setTint(toolbar.getNavigationIcon(),getResources().getColor(color));
    }
}
