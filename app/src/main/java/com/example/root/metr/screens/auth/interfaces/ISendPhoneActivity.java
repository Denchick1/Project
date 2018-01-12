package com.example.root.metr.screens.auth.interfaces;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.root.metr.models.business_model.RegistrationModel;

@StateStrategyType(SkipStrategy.class)
public interface ISendPhoneActivity extends MvpView {

    void initVisibleButtonNext(int visibilityBtNext);
    void initVisivileProgressBar(int visibilityProgressBar);
    void showErrorConnect();
    void onVisibleSendInfo();
    void smsAlready(RegistrationModel registrationModel, long time);
    void startSmsEnter(RegistrationModel registrationModel);
}
