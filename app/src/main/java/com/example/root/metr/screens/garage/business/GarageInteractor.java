package com.example.root.metr.screens.garage.business;


import android.net.Uri;

import com.example.root.metr.data.IRepositoryCar;
import com.example.root.metr.data.IRepositoryWrecker;
import com.example.root.metr.data.request_models.BodyAddWrecker;
import com.example.root.metr.models.DTO.Car;
import com.example.root.metr.models.DTO.TypeCar;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.DTO.WreckerCarrying;
import com.example.root.metr.models.DTO.WreckerParams;
import com.example.root.metr.models.view_model.Full_car;
import com.example.root.metr.models.view_model.QueryWrecker;
import com.example.root.metr.root.App;
import com.example.root.metr.throwable.ThrowableManager;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class GarageInteractor {

    @Inject
    IRepositoryCar iRepositoryCar;
    @Inject
    IRepositoryWrecker iRepositoryWrecker;
    @Inject
    ThrowableManager throwableManager;

    Observable<Full_car> full_carObservable;

    public GarageInteractor() {
        App.INSTANCE.getComponent().GarageInteractor(this);
        initFullCar();
    }

    public GarageInteractor(IRepositoryCar iRepositoryCar, IRepositoryWrecker iRepositoryWrecker, ThrowableManager throwableManager) {
        this.iRepositoryCar = iRepositoryCar;
        this.iRepositoryWrecker = iRepositoryWrecker;
        this.throwableManager = throwableManager;
        initFullCar();
    }

    public Observable<WreckerParams> takeWreckerParams(){
        return iRepositoryWrecker.getWreckerParams();
    }

    public Observable<Full_car> takeFullCar() {
        return full_carObservable;
    }

    public Observable<List<Wrecker>> takeWreckersList() {
        return iRepositoryWrecker.getWreckers();
    }

    public Observable<Wrecker> addOrEditWrecker(QueryWrecker queryWrecker, File file, Uri uri) {

        if(queryWrecker.getWrecker_mark().equals("Нажмите, чтобы выбрать") ||
                queryWrecker.getWrecker_model().equals("Нажмите, чтобы выбрать") ||
                queryWrecker.getWrecker_type().equals("Нажмите, чтобы выбрать") ||
                queryWrecker.getWrecker_mark().equals("") ||
                queryWrecker.getWrecker_model().equals("") ||
                queryWrecker.getWrecker_type().equals("") ||
                queryWrecker.getNumber_auto().equals("")){
            return Observable.error(throwableManager.getErrorValidateTypeExeption());
        }

        return iRepositoryWrecker.addWrecker(initBodyAddWrecker(queryWrecker))
                .doOnNext(wrecker -> iRepositoryWrecker
                        .sendPhotoWrecker(wrecker.getId(), file, uri)
                        .onErrorResumeNext(throwable -> Completable.complete()));
    }

    private BodyAddWrecker initBodyAddWrecker(QueryWrecker queryWrecker) {

        Full_car full_car = takeFullCar().blockingFirst();

        BodyAddWrecker bodyAddWrecker = new BodyAddWrecker();
        bodyAddWrecker.setWrecker_type_id(full_car.getIdTypeCar(queryWrecker.getWrecker_type()));
        bodyAddWrecker.setWrecker_car_id(full_car.getIdMarkCar(queryWrecker.getWrecker_mark(), queryWrecker.getWrecker_model()));
        bodyAddWrecker.setWreckers_carrying_id(full_car.getIdCarryng(queryWrecker.getWreckers_carrying()));
        bodyAddWrecker.setNumber_auto(queryWrecker.getNumber_auto());
        bodyAddWrecker.setCrane_winch(queryWrecker.isCrane_winch() ? 1 : 0);
        bodyAddWrecker.setRigid_coupling(queryWrecker.isRigid_coupling() ? 1 : 0);
        bodyAddWrecker.setFastening_elements(queryWrecker.isFastening_elements() ? 1 : 0);
        bodyAddWrecker.setInsured(queryWrecker.isInsured() ? 1 : 0);
        bodyAddWrecker.setId(queryWrecker.getId());

        return bodyAddWrecker;
    }

    private io.reactivex.Observable<List<TypeCar>> takeTypeCar() {
        return iRepositoryCar.getTypeCars();
    }

    private io.reactivex.Observable<List<Car>> takeCar() {
        return iRepositoryCar.getCars();
    }

    private Observable<List<WreckerCarrying>> takeCarrying() {
        return iRepositoryCar.getWreckerCarring();
    }

    private void initFullCar() {
        full_carObservable = Observable.zip(
                takeCar(),
                takeTypeCar(),
                takeCarrying(),
                Full_car::new).cache();
    }

}
