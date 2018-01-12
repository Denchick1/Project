package com.example.root.metr.models.DTO;


import polanski.option.Option;

public class User {

    private int id;
    private String token;
    private String f;
    private String i;
    private String o;
    private String phone;
    private String inn;
    private String expires_at;
    private int status;
    private int type_user;

    public int getType() {
        return type_user;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return Option.ofObj(token).orDefault(() -> "");
    }

    public String getF() {
        return Option.ofObj(f).orDefault(() -> "");
    }

    public String getI() {
        return Option.ofObj(i).orDefault(() -> "");
    }

    public String getO() {
        return Option.ofObj(o).orDefault(() -> "");
    }

    public String getPhone() {
        return Option.ofObj(phone).orDefault(() -> "");
    }

    public String getInn() {
        return Option.ofObj(inn).orDefault(() -> "");
    }

    public String getExpires_at() {
        return Option.ofObj(expires_at).orDefault(() -> "");
    }

    public int getStatus() {
        return status;
    }

    public void setType_user(int type_user) {
        this.type_user = type_user;
    }
}
