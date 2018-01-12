package com.example.root.metr.data.request_models;

import com.bluelinelabs.logansquare.annotation.JsonField;

public class BodyAddWrecker {

    //TODO надо переделать

    @JsonField(name = "id")
    private String id;
    @JsonField(name="wrecker_type_id")
    private int wrecker_type_id;
    @JsonField(name="wrecker_car_id")
    private int wrecker_car_id;
    @JsonField(name="wreckers_carrying_id")
    private int wreckers_carrying_id;
    @JsonField(name="number_auto")
    private String number_auto;
    @JsonField(name="insured")
    private int insured;
    @JsonField(name="crane_winch")
    private int crane_winch;
    @JsonField(name="rigid_coupling")
    private int rigid_coupling;
    @JsonField(name="fastening_elements")
    private int fastening_elements;

    public void setWrecker_type_id(int wrecker_type_id) {
        this.wrecker_type_id = wrecker_type_id;
    }

    public void setWrecker_car_id(int wrecker_car_id) {
        this.wrecker_car_id = wrecker_car_id;
    }

    public void setWreckers_carrying_id(int wreckers_carrying_id) {
        this.wreckers_carrying_id = wreckers_carrying_id;
    }

    public void setNumber_auto(String number_auto) {
        this.number_auto = number_auto;
    }

    public void setInsured(int insured) {
        this.insured = insured;
    }

    public void setCrane_winch(int crane_winch) {
        this.crane_winch = crane_winch;
    }

    public void setRigid_coupling(int rigid_coupling) {
        this.rigid_coupling = rigid_coupling;
    }

    public void setFastening_elements(int fastening_elements) {
        this.fastening_elements = fastening_elements;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getWrecker_type_id() {
        return wrecker_type_id;
    }

    public int getWrecker_car_id() {
        return wrecker_car_id;
    }

    public int getWreckers_carrying_id() {
        return wreckers_carrying_id;
    }

    public String getNumber_auto() {
        return number_auto;
    }

    public int getInsured() {
        return insured;
    }

    public int getCrane_winch() {
        return crane_winch;
    }

    public int getRigid_coupling() {
        return rigid_coupling;
    }

    public int getFastening_elements() {
        return fastening_elements;
    }
}
