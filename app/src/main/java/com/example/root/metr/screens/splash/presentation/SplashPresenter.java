package com.example.root.metr.screens.splash.presentation;

import com.example.root.metr.data.IUserRepository;
import com.example.root.metr.root.App;
import com.example.root.metr.screens.splash.interfaces.SplashView;
import com.example.root.metr.utils.ShedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SplashPresenter {

    @Inject
    ShedulerProvider shedulerProvider;
    @Inject
    IUserRepository iUserRepository;

    private SplashView mSplashView;


    public SplashPresenter() {
        App.INSTANCE.getComponent().SplashPresenter(this);
    }

    public void attachView(SplashView view) {
        mSplashView = view;
    }


    public void onDestroy() {
        mSplashView = null;
    }

    public void createView() {
        Observable
                .just(iUserRepository.hasUser())
                .observeOn(shedulerProvider.main())
                .delay(1, TimeUnit.SECONDS)
                .subscribe(s -> {
                    if(s) mSplashView.startMain();
                    else mSplashView.startLogin();
                }, Throwable::printStackTrace);
    }
}
