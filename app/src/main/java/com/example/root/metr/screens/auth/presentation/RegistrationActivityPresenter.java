package com.example.root.metr.screens.auth.presentation;


import com.example.root.metr.root.App;
import com.example.root.metr.screens.auth.business.AuthInteractor;
import com.example.root.metr.screens.auth.interfaces.IRegistrationActivity;
import com.example.root.metr.throwable.ErrorValidateNameExeption;
import com.example.root.metr.throwable.ErrorValideEmailExeption;
import com.example.root.metr.throwable.ErrorValidePhoneExeption;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class RegistrationActivityPresenter {

    @Inject
    AuthInteractor authInteractor;
    @Inject
    ShedulerProvider shedulerProvider;
    @Inject
    ExeptionManager exeptionManager;

    IRegistrationActivity iRegistrationActivity;
    Disposable disposable = Disposables.empty();

    public RegistrationActivityPresenter() {
        App.INSTANCE.getComponent().registrationActivityPresenter(this);
    }

    public RegistrationActivityPresenter(AuthInteractor authInteractor, ShedulerProvider shedulerProvider, IRegistrationActivity iRegistrationActivity) {
        this.authInteractor = authInteractor;
        this.shedulerProvider = shedulerProvider;
        this.iRegistrationActivity = iRegistrationActivity;
    }

    public void attachView(IRegistrationActivity iRegistrationActivity) {
        this.iRegistrationActivity = iRegistrationActivity;
    }

    public void onRegistration(String name, String phone, String email) {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        authInteractor.startRegistration(name, phone, email)
                .observeOn(shedulerProvider.main())
                .doOnSubscribe(disposable -> {
                    this.disposable = disposable;
                    iRegistrationActivity.showProgressBar();
                })
                .doAfterTerminate(iRegistrationActivity::hideProgressBar)
                .subscribe(iRegistrationActivity::successRegistration,
                        throwable -> {
                            if (throwable instanceof ErrorValideEmailExeption) {
                                iRegistrationActivity.showErrorValidEmail();
                            } else if (throwable instanceof ErrorValidePhoneExeption) {
                                iRegistrationActivity.showErrorValidPhone();
                            }else if(throwable instanceof ErrorValidateNameExeption){
                                iRegistrationActivity.showErrorValidName();
                            }else {
                                switch (exeptionManager.getfailure(throwable)) {
                                    case -1:
                                      iRegistrationActivity.showConnectError();
                                      break;
                                    case 3234:
                                        iRegistrationActivity.showErrorUserExist();
                                }
                            }
                        });
    }

    public void validateField(String name, String phone, String email){
        authInteractor.validateField(name, phone, email)
                .subscribe(aBoolean -> {
                    if(aBoolean)iRegistrationActivity.onEnableBtGo();
                    else iRegistrationActivity.onDisableBtGo();
                },Throwable::printStackTrace);
    }
}
