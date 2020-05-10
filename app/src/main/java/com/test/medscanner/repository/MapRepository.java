package com.test.medscanner.repository;

import com.test.medscanner.util.Clinic;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MapRepository {
    Single<List<Clinic>> loadAllClinics();
    Single<List<Clinic>> loadFavClinics();
}
