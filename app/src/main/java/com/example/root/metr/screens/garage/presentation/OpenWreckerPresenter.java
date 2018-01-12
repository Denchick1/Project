package com.example.root.metr.screens.garage.presentation;

import com.example.root.metr.root.App;
import com.example.root.metr.screens.garage.business.OpenWreckerInteractor;
import com.example.root.metr.screens.garage.interfaces.OpenWreckerView;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import javax.inject.Inject;

public class OpenWreckerPresenter {

    @Inject
    ShedulerProvider shedulerProvider;
    @Inject
    ExeptionManager exeptionManager;
    OpenWreckerInteractor mOpenWreckerInteractor;
    private OpenWreckerView mOpenWreckerView;

    public OpenWreckerPresenter() {
        App.INSTANCE.getComponent().OpenWreckerPresenter(this);

        mOpenWreckerInteractor = new OpenWreckerInteractor();


    }

    public void attachView(OpenWreckerView view) {
        mOpenWreckerView = view;
    }


    public void onDestroy() {
        mOpenWreckerView = null;
    }
}
