package com.example.root.metr.models.business_model;

import android.os.Parcel;
import android.os.Parcelable;

public class RegistrationModel implements Parcelable{

    private String uuid;
    private String code;
    private String expires_at;
    private int expires_after;
    private String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    protected RegistrationModel(Parcel in) {
        uuid = in.readString();
        code = in.readString();
        expires_at = in.readString();
        expires_after = in.readInt();
        phone=in.readString();
    }

    public static final Creator<RegistrationModel> CREATOR = new Creator<RegistrationModel>() {
        @Override
        public RegistrationModel createFromParcel(Parcel in) {
            return new RegistrationModel(in);
        }

        @Override
        public RegistrationModel[] newArray(int size) {
            return new RegistrationModel[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public String getCode() {
        return code;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public int getExpires_after() {
        return expires_after;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public void setExpires_after(int expires_after) {
        this.expires_after = expires_after;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(code);
        parcel.writeString(expires_at);
        parcel.writeInt(expires_after);
        parcel.writeString(phone);
    }
}
