package com.example.root.metr.screens.auth.presentation;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.root.metr.R;
import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.root.App;
import com.example.root.metr.screens.auth.business.AuthInteractor;
import com.example.root.metr.screens.auth.interfaces.ISendSmsActivity;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@InjectViewState
public class SendSmsActivityPresenter extends MvpPresenter<ISendSmsActivity> {

    @Inject
    AuthInteractor authInteractor;
    @Inject
    ShedulerProvider shedulerProvider;
    @Inject
    ExeptionManager exeptionManager;

    Disposable disposable = Disposables.empty();

    long chronometrSecund;

    public SendSmsActivityPresenter() {
        App.INSTANCE.getComponent().sendSmsActivityPresenter(this);
    }

    public SendSmsActivityPresenter(AuthInteractor authInteractor, ShedulerProvider shedulerProvider, ExeptionManager exeptionManager) {
        this.authInteractor = authInteractor;
        this.shedulerProvider = shedulerProvider;
        this.exeptionManager = exeptionManager;
    }

    public void retrySendPhone(String phone){
        authInteractor.sendPhone(phone)
                .subscribe(registrationModel -> {
                    getViewState().replaceRegistrationModel(registrationModel);
                },Throwable::printStackTrace);
    }

    public void sendSmsPassword(RegistrationModel registrationModel,String code) {
        authInteractor.sendSms(registrationModel.getUuid(), code)
                .observeOn(shedulerProvider.main())
                .subscribe(() -> getViewState().startMainActivity(), this::sendViewError);
    }

    public void startChronometr(int seconds) {
        authInteractor
                .startChronometer(seconds, shedulerProvider.computation())
                .doOnSubscribe(disposable -> {
                    this.disposable = disposable;
                    firstInitView();
                })
                .observeOn(shedulerProvider.main())
                .onErrorResumeNext(throwable -> {
                    if(chronometrSecund>1)
                    return authInteractor.startChronometer(chronometrSecund,shedulerProvider.computation());
                    else return Observable.error(throwable);
                })
                .subscribe(secunds ->{
                         getViewState().setTextChronometr(String.valueOf(secunds));
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.ROOT);
                            Date date=simpleDateFormat.parse(secunds);
                            chronometrSecund= date.getTime();
                        }
                        , Throwable::printStackTrace,
                        this::stopChronometr);
    }

    public void onValidateCode(String code){
        if(code.length()==4)getViewState().onVisibleBtnNext(View.VISIBLE);
        else getViewState().onVisibleBtnNext(View.INVISIBLE);
    }

    private void stopChronometr() {
        getViewState().setTextChronometr("Повторная отправка");
        getViewState().enableClickSendInfo();
        getViewState().onInvisibleSendInfo();
    }

    private void firstInitView() {
        getViewState().disableClickSendInfo();
        getViewState().setTextInfo(R.string.repeated_request);
        getViewState().onVisibleSendInfo();
    }

    private void sendViewError(Throwable throwable) {
        try {
            switch (exeptionManager.getfailure(throwable)) {
                case -1:
                    getViewState().showErrorConnect();
                    break;
                case 4240:
                    getViewState().setTextInfo(R.string.error_sms_code);
                    break;
                case 111:
                    throwable.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putTimeInCash(String time){
        if(!time.equals("Повторная отправка")){
            int times= Integer.parseInt(time.substring(0,2));
            times*=60;
            times+=Integer.parseInt(time.substring(3,5));
            RegistrationCash.getInstance().setTimeChronometr((long) times);
        }
    }




}
