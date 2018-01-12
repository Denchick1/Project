package com.example.root.metr.models.DTO;


import java.util.ArrayList;
import java.util.List;

import polanski.option.Option;

public class Car {


    private String brand;
    private List<Models> models;

    public String getBrand() {
        return Option.ofObj(brand).orDefault(() -> "");
    }

    public List<Models> getModels() {
        return Option.ofObj(models).orDefault(ArrayList::new);
    }

    public static class Models {
        private int id;
        private String model;

        public int getId() {
            return id;
        }

        public String getModel() {
            return Option.ofObj(model).orDefault(() -> "");
        }
    }
}
