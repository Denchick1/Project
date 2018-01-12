package com.example.root.metr.screens.auth.presentation;


import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.root.metr.root.App;
import com.example.root.metr.screens.auth.business.AuthInteractor;
import com.example.root.metr.screens.auth.interfaces.ISendPhoneActivity;
import com.example.root.metr.screens.auth.interfaces.LiveCiclePresenter;
import com.example.root.metr.utils.EditTextValidator;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@InjectViewState
public class SendPhoneActivityPresenter extends MvpPresenter<ISendPhoneActivity> implements LiveCiclePresenter {

    @Inject
    AuthInteractor authInteractor;
    @Inject
    EditTextValidator editTextValidator;
    @Inject
    ShedulerProvider shedulerProvider;
    @Inject
    ExeptionManager exeptionManager;

    private Disposable disposable = Disposables.empty();

    public SendPhoneActivityPresenter() {
        App.INSTANCE.getComponent().sendPhoneActivity(this);
    }

    public SendPhoneActivityPresenter(AuthInteractor authInteractor, EditTextValidator editTextValidator,
                                      ShedulerProvider shedulerProvider, ExeptionManager exeptionManager) {
        this.authInteractor = authInteractor;
        this.editTextValidator = editTextValidator;
        this.shedulerProvider = shedulerProvider;
        this.exeptionManager = exeptionManager;
    }

    public void onSendPhone(String phone) {
        sendPhone(editTextValidator.phoneConvertation(phone));
    }

    public void phoneValidate(String phone) {
        boolean phoneValid = editTextValidator.phoneValidateNew(phone);
        if (phoneValid) getViewState().initVisibleButtonNext(View.VISIBLE);
        else getViewState().initVisibleButtonNext(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }

    private void sendPhone(String phone) {

        authInteractor.sendPhone(phone)
                .doOnSubscribe(disposable1 -> {
                    getViewState().initVisibleButtonNext(View.INVISIBLE);
                    getViewState().initVisivileProgressBar(View.VISIBLE);
                })
                .doAfterTerminate(() -> {
                    getViewState().initVisivileProgressBar(View.INVISIBLE);
                })
                .subscribe(registrationModel -> {
                    getViewState().initVisibleButtonNext(View.VISIBLE);
                    registrationModel.setPhone(phone);
                    getViewState().startSmsEnter(registrationModel);
                    RegistrationCash.getInstance().setRegistrationModel(registrationModel);
                }, this::sendViewError);
    }

    private void sendViewError(Throwable throwable) {
        //TODO убрать трай кэтч , поправить
        try {
            switch (exeptionManager.getfailure(throwable)) {
                case -1:
                    getViewState().showErrorConnect();
                    break;
                case 4241:
                    getViewState().onVisibleSendInfo();
                    break;
                case 4242:
                    long time=RegistrationCash.getInstance().getTime();
                    getViewState().smsAlready(RegistrationCash.getInstance().getRegistrationModel(),time);
                    break;
                case 111:
                    throwable.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
