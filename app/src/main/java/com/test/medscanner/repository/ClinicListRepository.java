package com.test.medscanner.repository;

import com.test.medscanner.presenter.FavoritePresenter;
import com.test.medscanner.util.Service;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ClinicListRepository extends FavoriteRepository {
    Single<List<Service>> loadServicesFromRealm(String clinicID);
}
