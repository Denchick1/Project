package com.example.root.metr.screens.garage.presentation;

import com.example.root.metr.data.IUserRepository;
import com.example.root.metr.root.App;
import com.example.root.metr.screens.garage.business.GarageInteractor;
import com.example.root.metr.screens.garage.interfaces.GarageView;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class GaragePresenter {

    private final int PHISIC_USER = 0;
    private final int JURIDIC_USER = 1;

    @Inject
    IUserRepository iUserRepository;
    @Inject
    ShedulerProvider shedulerProvider;
    @Inject
    ExeptionManager exeptionManager;
    @Inject
    GarageInteractor garageInteractor;

    private GarageView mGarageView;

    Disposable disposableFullCar = Disposables.empty();


    public GaragePresenter() {
        App.INSTANCE.getComponent().GaragePresenter(this);
    }

    public GaragePresenter(IUserRepository iUserRepository, ShedulerProvider shedulerProvider, ExeptionManager exeptionManager, GarageInteractor garageInteractor) {
        this.iUserRepository = iUserRepository;
        this.shedulerProvider = shedulerProvider;
        this.exeptionManager = exeptionManager;
        this.garageInteractor = garageInteractor;
    }

    public void onCreate() {

    }

    public void onLoadListWreckers() {
        garageInteractor
                .takeWreckersList()
                .subscribe(wreckers -> {
                    int typeUser = iUserRepository.getUser().getType();
                    try {
                        if (typeUser == PHISIC_USER) {
                            if (wreckers.size() > 0) mGarageView.hideFab();
                            else mGarageView.showFab();
                        }
                        mGarageView.setupRecycler(wreckers);
                    } catch (NullPointerException ignored) {
                        //ignored if the server came null
                    }

                }, throwable -> {
                    switch (exeptionManager.getfailure(throwable)) {
                        case -1:
                            mGarageView.showConnectError();
                            break;
                    }
                });
    }

    public void attachView(GarageView view) {
        mGarageView = view;
    }

    public void onLoadFullCar() {
        if(!disposableFullCar.isDisposed())
            disposableFullCar.dispose();

        garageInteractor.takeFullCar()
                .doOnSubscribe(disposable -> disposableFullCar=disposable)
                .subscribe(full_car -> {
                }, throwable -> {
                    switch (exeptionManager.getfailure(throwable)) {
                        case -1:
                            mGarageView.showConnectError();
                            break;
                    }
                });
    }

    public void onDestroy() {
        mGarageView = null;
    }
}
