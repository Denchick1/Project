package com.example.root.metr.models.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import polanski.option.Option;

public class Wrecker implements Parcelable{


    private int id;
    private String number_auto;
    private int wrecker_type_id;
    private int wrecker_car_id;
    private int wreckers_carrying_id;
    private int insured;
    private int crane_winch;
    private int rigid_coupling;
    private int fastening_elements;
    private int status;
    private String url_avatar;

    protected Wrecker(Parcel in) {
        id = in.readInt();
        number_auto = in.readString();
        wrecker_type_id = in.readInt();
        wrecker_car_id = in.readInt();
        wreckers_carrying_id = in.readInt();
        insured = in.readInt();
        crane_winch = in.readInt();
        rigid_coupling = in.readInt();
        fastening_elements = in.readInt();
        status = in.readInt();
        url_avatar = in.readString();
    }

    public static final Creator<Wrecker> CREATOR = new Creator<Wrecker>() {
        @Override
        public Wrecker createFromParcel(Parcel in) {
            return new Wrecker(in);
        }

        @Override
        public Wrecker[] newArray(int size) {
            return new Wrecker[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNumber_auto() {
        return Option.ofObj(number_auto).orDefault(() -> "");
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

    public int getStatus() {
        return status;
    }

    public String getUrl_avatar() {
        return Option.ofObj(url_avatar).orDefault(() -> "");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(number_auto);
        parcel.writeInt(wrecker_type_id);
        parcel.writeInt(wrecker_car_id);
        parcel.writeInt(wreckers_carrying_id);
        parcel.writeInt(insured);
        parcel.writeInt(crane_winch);
        parcel.writeInt(rigid_coupling);
        parcel.writeInt(fastening_elements);
        parcel.writeInt(status);
        parcel.writeString(url_avatar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wrecker wrecker = (Wrecker) o;

        if (id != wrecker.id) return false;
        if (wrecker_type_id != wrecker.wrecker_type_id) return false;
        if (wrecker_car_id != wrecker.wrecker_car_id) return false;
        if (wreckers_carrying_id != wrecker.wreckers_carrying_id) return false;
        if (insured != wrecker.insured) return false;
        if (crane_winch != wrecker.crane_winch) return false;
        if (rigid_coupling != wrecker.rigid_coupling) return false;
        if (fastening_elements != wrecker.fastening_elements) return false;
        if (status != wrecker.status) return false;
        if (number_auto != null ? !number_auto.equals(wrecker.number_auto) : wrecker.number_auto != null)
            return false;
        return url_avatar != null ? url_avatar.equals(wrecker.url_avatar) : wrecker.url_avatar == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number_auto != null ? number_auto.hashCode() : 0);
        result = 31 * result + wrecker_type_id;
        result = 31 * result + wrecker_car_id;
        result = 31 * result + wreckers_carrying_id;
        result = 31 * result + insured;
        result = 31 * result + crane_winch;
        result = 31 * result + rigid_coupling;
        result = 31 * result + fastening_elements;
        result = 31 * result + status;
        result = 31 * result + (url_avatar != null ? url_avatar.hashCode() : 0);
        return result;
    }
}
