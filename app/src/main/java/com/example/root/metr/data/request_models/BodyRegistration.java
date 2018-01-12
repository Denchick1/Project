package com.example.root.metr.data.request_models;

import com.bluelinelabs.logansquare.annotation.JsonField;

public class BodyRegistration {

    @JsonField(name = "phone")
    public String phone;
    @JsonField(name = "login")
    public String login;
    @JsonField(name = "type_user")
    public int type_user=1;
    @JsonField(name = "email")
    public String email;

    public BodyRegistration(String phone, String login,String email) {
        this.phone = phone;
        this.login = login;
        this.email=email;
    }
}
