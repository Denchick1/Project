package com.example.root.metr.screens.splash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.root.metr.root.BaseActivity;
import com.example.root.metr.root.ConnectErrorDialog;
import com.example.root.metr.root.MainActivity;
import com.example.root.metr.screens.auth.ui.StartLoginActivity;
import com.example.root.metr.screens.splash.interfaces.SplashView;
import com.example.root.metr.screens.splash.presentation.SplashPresenter;

public class SplashActivity extends BaseActivity implements SplashView {

    private SplashPresenter mPresenter;

    private ConnectErrorDialog connectErrorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup viewGroup = null;
        connectErrorDialog = new ConnectErrorDialog(this, viewGroup, view -> {
            connectErrorDialog.dismiss();
        });
        mPresenter = new SplashPresenter();
        mPresenter.attachView(this);
        mPresenter.createView();
    }



    @Override
    public void errorConnect() {
        connectErrorDialog.show();
    }

    @Override
    public void startLogin() {
        startActivity(new Intent(this, StartLoginActivity.class));
        finish();
    }

    @Override
    public void startMain() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
