package com.example.root.metr.dagger;

import com.example.root.metr.ScopeD;
import com.example.root.metr.root.MainActivityPresenter;
import com.example.root.metr.screens.auth.business.AuthInteractor;
import com.example.root.metr.screens.auth.presentation.RegistrationActivityPresenter;
import com.example.root.metr.screens.auth.presentation.SendPhoneActivityPresenter;
import com.example.root.metr.screens.auth.presentation.SendSmsActivityPresenter;
import com.example.root.metr.screens.garage.business.GarageInteractor;
import com.example.root.metr.screens.garage.presentation.AddEvacuatorPresenter;
import com.example.root.metr.screens.garage.presentation.AddTarifPresenter;
import com.example.root.metr.screens.garage.presentation.GaragePresenter;
import com.example.root.metr.screens.garage.presentation.OpenWreckerPresenter;
import com.example.root.metr.screens.order.presentation.OrderPresenter;
import com.example.root.metr.screens.profile.presentation.ProfilePresenter;
import com.example.root.metr.screens.splash.presentation.SplashPresenter;

@dagger.Subcomponent(modules = {AuthModule.class,CoreModule.class,CarModule.class})
@ScopeD
public interface Component {
    void sendPhoneActivity(SendPhoneActivityPresenter sendPhoneActivityPresenter);
    void sendSmsActivityPresenter(SendSmsActivityPresenter sendSmsActivityPresenter);
    void registrationActivityPresenter(RegistrationActivityPresenter registrationActivityPresenter);
    void AuthInteractor(AuthInteractor authInteractor);
    void GaragePresenter(GaragePresenter garagePresenter);
    void AddEvacuatorPresenter(AddEvacuatorPresenter addEvacuatorPresenter);

    void GarageInteractor(GarageInteractor garageInteractor);

    void OrderPresenter(OrderPresenter orderPresenter);

    void SplashPresenter(SplashPresenter splashPresenter);

    void MainActivityPresenter(MainActivityPresenter mainActivityPresenter);

    void OpenWreckerPresenter(OpenWreckerPresenter openWreckerPresenter);

    void ProfilePresenter(ProfilePresenter profilePresenter);

    void AddTarifPresenter(AddTarifPresenter addTarifPresenter);
}

