package com.example.root.metr.data.request_models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.example.root.metr.models.DTO.Wrecker;

public class BodyEditWrecker {

    @JsonField(name = "id")
    private int id;
    @JsonField(name = "wrecker_type_id")
    private int wrecker_type_id;
    @JsonField(name = "wrecker_car_id")
    private int wrecker_car_id;
    @JsonField(name = "wreckers_carrying_id")
    private int wreckers_carrying_id;
    @JsonField(name = "number_auto")
    private String number_auto;
    @JsonField(name = "insured")
    private int insured;
    @JsonField(name = "crane_winch")
    private int crane_winch;
    @JsonField(name = "rigid_coupling")
    private int rigid_coupling;
    @JsonField(name = "fastening_elements")
    private int fastening_elements;

    public BodyEditWrecker() {
    }

    public BodyEditWrecker(Wrecker wrecker) {
        id = wrecker.getId();
        wrecker_type_id = wrecker.getWrecker_type_id();
        wrecker_car_id = wrecker.getWrecker_car_id();
        wreckers_carrying_id = wrecker.getWreckers_carrying_id();
        number_auto = wrecker.getNumber_auto();
        insured = wrecker.getInsured();
        crane_winch = wrecker.getCrane_winch();
        rigid_coupling = wrecker.getRigid_coupling();
        fastening_elements = wrecker.getFastening_elements();
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
