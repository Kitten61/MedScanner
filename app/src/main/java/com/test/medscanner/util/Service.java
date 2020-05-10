package com.test.medscanner.util;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Service extends RealmObject implements Parcelable {
    private String category;
    private String hospital_id;
    private String id;
    private String name;
    private long price;
    private boolean isStarred;

    public Service() {}

    protected Service(Parcel in) {
        category = in.readString();
        hospital_id = in.readString();
        id = in.readString();
        name = in.readString();
        price = in.readLong();
        isStarred = in.readByte() != 0;
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(category);
        parcel.writeString(hospital_id);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeLong(price);
        parcel.writeByte((byte) (isStarred ? 1 : 0));
    }
}