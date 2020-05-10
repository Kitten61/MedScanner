package com.test.medscanner.presenter;

import com.test.medscanner.util.Service;

public interface ClinicListPresenter {
    void loadServices(String clinicID);
    void addFavorite(Service service);
    void removeFavorite(Service service);
}
