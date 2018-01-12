package com.example.root.metr.screens.integration;

import android.net.Uri;

import com.example.root.metr.data.IRepositoryCar;
import com.example.root.metr.data.IRepositoryWrecker;
import com.example.root.metr.data.request_models.BodyAddWrecker;
import com.example.root.metr.models.DTO.Car;
import com.example.root.metr.models.DTO.TypeCar;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.DTO.WreckerCarrying;
import com.example.root.metr.models.view_model.QueryWrecker;
import com.example.root.metr.screens.garage.business.GarageInteractor;
import com.example.root.metr.screens.garage.interfaces.AddEvacuatorView;
import com.example.root.metr.screens.garage.presentation.AddEvacuatorPresenter;
import com.example.root.metr.throwable.ThrowableManager;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

public class AddEvacuatorPresenterTest {

    @Mock
    ShedulerProvider shedulerProvider;
    @Mock
    AddEvacuatorView addEvacuatorView;
    @Mock
    IRepositoryCar iRepositoryCar;
    @Mock
    IRepositoryWrecker iRepositoryWrecker;
    @Mock
    File file;
    @Mock
    Uri uri;
    @Mock
    TypeCar typeCar;
    @Mock
    Car car;
    @Mock
    WreckerCarrying wreckerCarrying;
    @Mock
    Car.Models model;
    @Mock
    Wrecker wrecker;

    ThrowableManager throwableManager;

    GarageInteractor garageInteractor;

    ExeptionManager exeptionManager;

    TestScheduler testScheduler;

    AddEvacuatorPresenter addEvacuatorPresenter;

    QueryWrecker queryWrecker;

    List<TypeCar> typeCarList;
    List<Car> carList;
    List<WreckerCarrying> wreckerCarryingList;
    List<Car.Models> models;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        exeptionManager = new ExeptionManager();
        testScheduler = new TestScheduler();
        throwableManager = new ThrowableManager();
        Mockito.when(shedulerProvider.computation()).thenReturn(testScheduler);
        Mockito.when(shedulerProvider.io()).thenReturn(testScheduler);
        Mockito.when(shedulerProvider.main()).thenReturn(testScheduler);

        initRepository();

        garageInteractor = new GarageInteractor(iRepositoryCar, iRepositoryWrecker, throwableManager);
        addEvacuatorPresenter = new AddEvacuatorPresenter(shedulerProvider, garageInteractor, exeptionManager);
        addEvacuatorPresenter.attachView(addEvacuatorView);

    }


    private void initRepository() {
        typeCarList = new ArrayList<>();
        carList = new ArrayList<>();
        wreckerCarryingList = new ArrayList<>();
        models = new ArrayList<>();
        typeCarList.add(typeCar);
        carList.add(car);
        wreckerCarryingList.add(wreckerCarrying);
        models.add(model);

        Mockito.when(iRepositoryCar.getCars()).thenReturn(Observable.just(carList));
        Mockito.when(iRepositoryCar.getTypeCars()).thenReturn(Observable.just(typeCarList));
        Mockito.when(iRepositoryCar.getWreckerCarring()).thenReturn(Observable.just(wreckerCarryingList));

        Mockito.when(car.getBrand()).thenReturn("Газ");
        Mockito.when(car.getModels()).thenReturn(models);
        Mockito.when(model.getId()).thenReturn(1);
        Mockito.when(model.getModel()).thenReturn("3369");
        Mockito.when(typeCar.getName()).thenReturn("эвакуатор");
        Mockito.when(wreckerCarrying.getValue()).thenReturn("10000");
        Mockito.when(iRepositoryWrecker.addWrecker(Mockito.any(BodyAddWrecker.class))).thenReturn(Observable.just(wrecker));

        Mockito.when(wrecker.getId()).thenReturn(1);
        Mockito.when(iRepositoryWrecker.sendPhotoWrecker(1,file,uri)).thenReturn(Completable.complete());
    }

    @Test
    public void onLoadCar() {
        addEvacuatorPresenter.onLoadCars();
        Mockito.verify(addEvacuatorView).initCarryingWreckerAdapter(Mockito.any(String[].class));
        Mockito.verify(addEvacuatorView).initTypeWreckerAdapter(Mockito.any(String[].class));
        Mockito.verify(addEvacuatorView).initMarkWreckerAdapter(Mockito.any(String[].class));
    }

    @Test
    public void onCLickMark() {
        addEvacuatorPresenter.onClickMark("");
        Mockito.verify(addEvacuatorView).initModelWreckerAdapter(Mockito.any(String[].class));
    }

    @Test
    public void onAddOrEditWreckerErrorFillField() {
        addEvacuatorPresenter.onAddOrEditWrecker("", "Нажмите, чтобы выбрать", "", "", "sss",
                "1000", true, true, true, true, file, uri);

        Mockito.verify(addEvacuatorView).showProgressBar();
        Mockito.verify(addEvacuatorView).hideProgressBar();
        Mockito.verify(addEvacuatorView).showErrorFillField();

        addEvacuatorPresenter.onAddOrEditWrecker("", "", "Нажмите, чтобы выбрать", "", "sss",
                "1000", true, true, true, true, file, uri);

        Mockito.verify(addEvacuatorView, Mockito.times(2)).showProgressBar();
        Mockito.verify(addEvacuatorView, Mockito.times(2)).hideProgressBar();
        Mockito.verify(addEvacuatorView, Mockito.times(2)).showErrorFillField();

        addEvacuatorPresenter.onAddOrEditWrecker("", "", "", "", "sss",
                "1000", true, true, true, true, file, uri);

        Mockito.verify(addEvacuatorView, Mockito.times(3)).showProgressBar();
        Mockito.verify(addEvacuatorView, Mockito.times(3)).hideProgressBar();
        Mockito.verify(addEvacuatorView, Mockito.times(3)).showErrorFillField();

    }

    @Test
    public void anAddOnEditWrecker() {

        addEvacuatorPresenter.onAddOrEditWrecker("", "test", "test", "test", "sss",
                "1000", true, true, true, true, file, uri);

        Mockito.verify(addEvacuatorView).showProgressBar();
        Mockito.verify(addEvacuatorView).hideProgressBar();
        Mockito.verify(addEvacuatorView).onSuccesActionWithWrecker(wrecker);


    }


}