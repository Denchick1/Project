package com.example.root.metr.models.DTO;


import polanski.option.Option;

public class TypeCar {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return Option.ofObj(name).orDefault(() -> "");
    }
}
