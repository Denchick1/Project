package com.example.root.metr.data.request_models;

import com.bluelinelabs.logansquare.annotation.JsonField;

public class BodySendPhoto {
    @JsonField(name="id")
    int id;

    public BodySendPhoto(int id) {
        this.id = id;
    }
}
