package com.example.root.metr.data.request_models;

import com.bluelinelabs.logansquare.annotation.JsonField;

public class BodyAuth {

    @JsonField(name = "phone")
    public String phone;

    public BodyAuth(String phone) {
        this.phone = phone;
    }
}
