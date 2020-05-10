package com.test.medscanner.util;

import io.realm.RealmObject;

public class FavoriteService extends RealmObject {
    private String hospital_id;
    private String id;
    public String getHospital_id() {
        return hospital_id;
    }
    public String getId() {
        return id;
    }
    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
