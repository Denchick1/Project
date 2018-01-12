package com.example.root.metr.screens.garage.presentation;

import android.net.Uri;
import android.util.ArrayMap;

import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.view_model.QueryWrecker;
import com.example.root.metr.root.App;
import com.example.root.metr.screens.garage.business.GarageInteractor;
import com.example.root.metr.screens.garage.interfaces.AddEvacuatorView;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;

public class AddEvacuatorPresenter {

    @Inject
    ShedulerProvider shedulerProvider;
    @Inject
    GarageInteractor garageInteractor;
    @Inject
    ExeptionManager exeptionManager;

    private AddEvacuatorView mAddEvacuatorView;


    public AddEvacuatorPresenter() {
        App.INSTANCE.getComponent().AddEvacuatorPresenter(this);
    }

    public AddEvacuatorPresenter(ShedulerProvider shedulerProvider, GarageInteractor garageInteractor, ExeptionManager exeptionManager) {
        this.shedulerProvider = shedulerProvider;
        this.garageInteractor = garageInteractor;
        this.exeptionManager = exeptionManager;
    }

    public void attachView(AddEvacuatorView view) {
        mAddEvacuatorView = view;
    }

    public void onLoadCars() {
        garageInteractor.takeFullCar().subscribe(full_car -> {
            mAddEvacuatorView.initCarryingWreckerAdapter(full_car.getCarrying());
            mAddEvacuatorView.initMarkWreckerAdapter(full_car.getMark());
            mAddEvacuatorView.initTypeWreckerAdapter(full_car.getTypeCars());
        }, Throwable::printStackTrace);
    }

    public void onClickMark(String mark) {
        garageInteractor.takeFullCar().subscribe(full_car -> {
            mAddEvacuatorView.initModelWreckerAdapter(full_car.getModels(mark));
        }, Throwable::printStackTrace);
    }

    public void onAddOrEditWrecker(String id, String type, String mark, String model, String number,
                                   String carrying, boolean crane_winch, boolean rigid_coupling,
                                   boolean fastening_elements, boolean insured, File file, Uri uri) {

        QueryWrecker queryWrecker = new QueryWrecker(
                id, type, model, mark, carrying, number, insured,
                crane_winch, rigid_coupling, fastening_elements);

        garageInteractor.addOrEditWrecker(queryWrecker, file, uri)
                .doOnSubscribe(disposable -> mAddEvacuatorView.showProgressBar())
                .doOnComplete(mAddEvacuatorView::hideProgressBar)
                .doOnError(throwable -> {
                    mAddEvacuatorView.hideProgressBar();
                })
                .subscribe(wrecker -> mAddEvacuatorView.onSuccesActionWithWrecker(wrecker), throwable -> {
                    switch (exeptionManager.getfailure(throwable)) {
                        case -1:
                            mAddEvacuatorView.showConnectError();
                            break;
                        case 3231:
                            mAddEvacuatorView.showErrorFillField();
                            break;
                    }
                });
    }

    public void onAddOrEditWrecker(ArrayMap<String,String> arrayMap){
        JSONObject jsonObject=new JSONObject(arrayMap);
    }


    public void onEditWrecker(Wrecker wrecker) {
        if (wrecker != null) {
            garageInteractor.takeFullCar().subscribe(full_car -> mAddEvacuatorView.fillField(full_car, wrecker), Throwable::printStackTrace);

        }
    }

    public void onLoadParamsWrecker() {
        garageInteractor.takeWreckerParams()
                .subscribe(mAddEvacuatorView::inflatingView, throwable -> {
                    switch (exeptionManager.getfailure(throwable)) {
                        case -1:
                            mAddEvacuatorView.showConnectError();
                            break;
                        default:
                            throwable.printStackTrace();
                    }
                });
    }

    public void onDestroy() {
        mAddEvacuatorView = null;
    }
}
