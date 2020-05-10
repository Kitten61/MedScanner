package com.test.medscanner.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Clinic extends RealmObject implements Parcelable {
    private String adress;
    private double latitude;
    private double longtitude;
    private String id;
    private String name;
    private String phoneNumber;

    private String photo;
    private RealmList<String> schedule;
    private String webSite;

    private RealmList<Service> services;

    public void putService(Service service) {
        if (services == null ) services = new RealmList<>();
        services.add(service);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(RealmList<String> schedule) {
        this.schedule = schedule;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(RealmList<Service> services) {
        this.services = services;
    }

    public Clinic() {}

    protected Clinic(Parcel in) {
        adress = in.readString();
        id = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        photo = in.readString();
        webSite = in.readString();
    }

    public static final Creator<Clinic> CREATOR = new Creator<Clinic>() {
        @Override
        public Clinic createFromParcel(Parcel in) {
            return new Clinic(in);
        }

        @Override
        public Clinic[] newArray(int size) {
            return new Clinic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(adress);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(phoneNumber);
        parcel.writeString(photo);
        parcel.writeString(webSite);
    }
}
