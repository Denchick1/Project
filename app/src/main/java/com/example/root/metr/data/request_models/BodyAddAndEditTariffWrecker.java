package com.example.root.metr.data.request_models;

import com.bluelinelabs.logansquare.annotation.JsonField;

public class BodyAddAndEditTariffWrecker {

    @JsonField(name="wrecker_id")
    public int wrecker_id;
    @JsonField(name="default_price")
    public int default_price;
    @JsonField(name="km_idling_price")
    public double km_idling_price;
    @JsonField(name="km_evacuation_price")
    public double km_evacuation_price;
    @JsonField(name="min_idling_price")
    public double min_idling_price;
    @JsonField(name="min_evacuation_price")
    public double min_evacuation_price;
    @JsonField(name="min_loading_price")
    public int min_loading_price;
    @JsonField(name="call_price")
    public double call_price;

    public BodyAddAndEditTariffWrecker setWrecker_id(int wrecker_id) {
        this.wrecker_id = wrecker_id;
        return this;
    }

    public BodyAddAndEditTariffWrecker setDefault_price(int default_price) {
        this.default_price = default_price;
        return this;
    }

    public BodyAddAndEditTariffWrecker setKm_idling_price(double km_idling_price) {
        this.km_idling_price = km_idling_price;
        return this;
    }

    public BodyAddAndEditTariffWrecker setKm_evacuation_price(double km_evacuation_price) {
        this.km_evacuation_price = km_evacuation_price;
        return this;
    }

    public BodyAddAndEditTariffWrecker setMin_idling_price(double min_idling_price) {
        this.min_idling_price = min_idling_price;
        return this;
    }

    public BodyAddAndEditTariffWrecker setMin_evacuation_price(double min_evacuation_price) {
        this.min_evacuation_price = min_evacuation_price;
        return this;
    }

    public BodyAddAndEditTariffWrecker setMin_loading_price(int min_loading_price) {
        this.min_loading_price = min_loading_price;
        return this;
    }

    public BodyAddAndEditTariffWrecker setCall_price(double call_price) {
        this.call_price = call_price;
        return this;
    }
}
