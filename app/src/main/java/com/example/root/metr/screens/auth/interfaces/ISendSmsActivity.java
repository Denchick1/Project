package com.example.root.metr.screens.auth.interfaces;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.root.metr.models.business_model.RegistrationModel;

@StateStrategyType(SkipStrategy.class)
public interface ISendSmsActivity extends MvpView {
    void setTextChronometr(String time);
    void startMainActivity();
    void enableClickSendInfo();
    void disableClickSendInfo();
    void onVisibleSendInfo();
    void onInvisibleSendInfo();
    void showErrorConnect();
    void onVisibleBtnNext(int visible);
    void setTextInfo(int error_sms_code);
    void replaceRegistrationModel(RegistrationModel registrationModel);
}
