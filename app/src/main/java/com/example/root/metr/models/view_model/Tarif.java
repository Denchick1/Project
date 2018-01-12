package com.example.root.metr.models.view_model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tarif implements Parcelable {

    int basePrice;
    int kmHolost;
    int kmWreckering;
    int minHolost;
    int minWreckering;
    int minPogrus;
    int priceCall;

    public Tarif(int basePrice, int kmHolost, int kmWreckering, int minHolost, int minWreckering, int minPogrus, int priceCall) {
        this.basePrice = basePrice;
        this.kmHolost = kmHolost;
        this.kmWreckering = kmWreckering;
        this.minHolost = minHolost;
        this.minWreckering = minWreckering;
        this.minPogrus = minPogrus;
        this.priceCall = priceCall;
    }

    protected Tarif(Parcel in) {
        basePrice = in.readInt();
        kmHolost = in.readInt();
        kmWreckering = in.readInt();
        minHolost = in.readInt();
        minWreckering = in.readInt();
        minPogrus = in.readInt();
        priceCall = in.readInt();
    }

    public static final Creator<Tarif> CREATOR = new Creator<Tarif>() {
        @Override
        public Tarif createFromParcel(Parcel in) {
            return new Tarif(in);
        }

        @Override
        public Tarif[] newArray(int size) {
            return new Tarif[size];
        }
    };

    public int getBasePrice() {
        return basePrice;
    }

    public int getKmHolost() {
        return kmHolost;
    }

    public int getKmWreckering() {
        return kmWreckering;
    }

    public int getMinHolost() {
        return minHolost;
    }

    public int getMinWreckering() {
        return minWreckering;
    }

    public int getMinPogrus() {
        return minPogrus;
    }

    public int getPriceCall() {
        return priceCall;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(basePrice);
        parcel.writeInt(kmHolost);
        parcel.writeInt(kmWreckering);
        parcel.writeInt(minHolost);
        parcel.writeInt(minWreckering);
        parcel.writeInt(minPogrus);
        parcel.writeInt(priceCall);
    }
}
