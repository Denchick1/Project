package com.example.root.metr.data;


import com.example.root.metr.models.DTO.Car;
import com.example.root.metr.models.DTO.TypeCar;
import com.example.root.metr.models.DTO.WreckerCarrying;

import java.util.List;

import io.reactivex.Observable;

public interface IRepositoryCar {
    Observable<List<Car>> getCars();
    Observable<List<TypeCar>> getTypeCars();
    Observable<List<WreckerCarrying>> getWreckerCarring();
}
