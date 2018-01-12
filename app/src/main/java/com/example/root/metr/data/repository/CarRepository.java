package com.example.root.metr.data.repository;


import com.example.root.metr.data.IRepositoryCar;
import com.example.root.metr.data.MetrRetrofit;
import com.example.root.metr.models.DTO.Car;
import com.example.root.metr.models.DTO.TypeCar;
import com.example.root.metr.models.DTO.WreckerCarrying;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class CarRepository implements IRepositoryCar {

    @Override
    public Observable<List<Car>> getCars() {
        return MetrRetrofit
                .getApiService()
                .getCars()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<TypeCar>> getTypeCars() {
        return MetrRetrofit
                .getApiService()
                .getTypeCar()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<WreckerCarrying>> getWreckerCarring() {
        return MetrRetrofit
                .getApiService()
                .getWreckersCarring()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
