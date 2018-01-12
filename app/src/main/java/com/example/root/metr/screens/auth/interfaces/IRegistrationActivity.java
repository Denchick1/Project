package com.example.root.metr.screens.auth.interfaces;


import com.example.root.metr.models.business_model.RegistrationModel;

public interface IRegistrationActivity {
    void successRegistration(RegistrationModel registrationModel);
    void showProgressBar();
    void hideProgressBar();
    void showErrorValidEmail();
    void showErrorValidPhone();
    void showErrorValidName();
    void onEnableBtGo();
    void onDisableBtGo();
    void showConnectError();
    void showErrorUserExist();
}
