package com.example.root.metr.data.request_models;

import com.bluelinelabs.logansquare.annotation.JsonField;

public class BodyRequestCodeSms {

    @JsonField(name = "uuid")
    public String uuid;
    @JsonField(name = "code")
    public String code;

    public BodyRequestCodeSms(String uuid, String code) {
        this.uuid = uuid;
        this.code = code;
    }
}
