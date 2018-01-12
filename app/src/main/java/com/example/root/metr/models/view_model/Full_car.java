package com.example.root.metr.models.view_model;


import android.text.TextUtils;

import com.annimon.stream.Stream;
import com.example.root.metr.models.DTO.Car;
import com.example.root.metr.models.DTO.TypeCar;
import com.example.root.metr.models.DTO.WreckerCarrying;

import java.util.List;
import java.util.NoSuchElementException;

import polanski.option.Option;

public class Full_car {

    private String[] arrayTypeCars;
    private String [] mark;
    private String [] carrying;
    private List<Car> cars;
    private List<WreckerCarrying> wreckerCarrings;
    private List<TypeCar> typeCars;

    public Full_car(List<Car> cars, List<TypeCar> typeCars, List<WreckerCarrying> wreckerCarrings) {
        this.cars=cars;
        this.wreckerCarrings=wreckerCarrings;
        this.typeCars=typeCars;
        arrayTypeCars=new String[typeCars.size()];
        mark=new String[cars.size()];
        carrying=new String[wreckerCarrings.size()];
        Stream.of(typeCars).map(TypeCar::getName).toList().toArray(arrayTypeCars);
        Stream.of(cars).map(Car::getBrand).toList().toArray(mark);
        Stream.of(wreckerCarrings).map(WreckerCarrying::getValue).toList().toArray(carrying);
    }

    public String[] getCarrying(){return carrying;}

    public int getIdCarryng(String value) {
        try {
            return Stream.of(wreckerCarrings)
                    .filter(wreckerCarrings -> wreckerCarrings.getValue().equals(value)).findFirst()
                    .mapToInt(WreckerCarrying::getId).getAsInt();
        }catch (NoSuchElementException e){
            return -1;
        }

    }

    public String[] getMark() {
        return mark;
    }

    public String[] getTypeCars() {
            return arrayTypeCars;
    }

    public String[] getModels(String mark){
        try {
            Car car=Stream.of(cars).filter(value -> value.getBrand().equals(mark)).single();
            String[] model = new String[car.getModels().size()];
            Stream.of(car.getModels()).map(Car.Models::getModel).toList().toArray(model);
            return model;
        }catch (Exception e){
            return new String[0];
        }

    }

    public int getIdTypeCar(String type){
        try {
            return Stream.of(typeCars)
                    .filter(value -> value.getName().equals(type))
                    .mapToInt(TypeCar::getId)
                    .single();
        }catch (NoSuchElementException e){
            return -1;
        }

    }

    public int getIdMarkCar(String mark,String model){
        try {
            return Stream.of(cars)
                    .filter(value -> value.getBrand().equals(mark))
                    .flatMap(car -> Stream.of(car.getModels()))
                    .filter(value -> value.getModel().equals(model))
                    .mapToInt(Car.Models::getId)
                    .single();
        }catch (NoSuchElementException e){
            return -1;
        }

    }

    public boolean validationType(String type){
        return Stream.of(getTypeCars()).filter(value -> value.equals(type)).count()!=0;
    }

    public boolean validationMark(String mark){
        return Stream.of(getMark()).filter(value -> value.equals(mark)).count()!=0;
    }

    public boolean validationModel(String mark,String model){
        return Stream.of(getModels(mark)).filter(value -> value.equals(model)).count()!=0;
    }

    public boolean validationNumber(String number){
        return !TextUtils.isEmpty(number);
    }

    public String getType(int id){
        return Option.ofObj(Stream.of(typeCars)
                .filter(value -> id==value.getId())
                .map(TypeCar::getName).single()).orDefault(() -> "");
    }

    public String getValueCarrying(int id){
        return Option.ofObj(Stream.of(wreckerCarrings)
                .filter(value -> id==value.getId())
                .map(WreckerCarrying::getValue).single()).orDefault(() -> "");
    }



}
