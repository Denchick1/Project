package com.example.root.metr.models.DTO;

import polanski.option.Option;

public class WreckerCarrying {


    private int id;
    private String name;
    private String value;

    public int getId() {
        return id;
    }

    public String getName() {
        return Option.ofObj(name).orDefault(() -> "");
    }

    public String getValue() {
        return Option.ofObj(value).orDefault(() -> "");
    }
}
