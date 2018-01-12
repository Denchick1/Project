package com.example.root.metr.screens.profile.presentation;

import com.example.root.metr.data.IUserRepository;
import com.example.root.metr.root.App;
import com.example.root.metr.screens.profile.interfaces.ProfileView;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import javax.inject.Inject;

public class ProfilePresenter {

    @Inject
    ShedulerProvider shedulerProvider;
    @Inject
    ExeptionManager exeptionManager;
    @Inject
    IUserRepository iUserRepository;

    private ProfileView mProfileView;


    public ProfilePresenter() {
        App.INSTANCE.getComponent().ProfilePresenter(this);
    }

    public void attachView(ProfileView view) {
        mProfileView = view;
    }


    public void onDestroy() {
        mProfileView = null;
    }

    public void create(){
        mProfileView.initUser(iUserRepository.getUser());
    }
}
