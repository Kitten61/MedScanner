package com.test.medscanner.repository;

import com.test.medscanner.util.Clinic;
import com.test.medscanner.util.Service;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface FavoriteRepository {
    Single<List<Clinic>> loadFavoriteFromRealm();

    Single<Boolean> addFavoriteInRealm(Service service);
    Single<Boolean> removeFavoriteFromRealm(Service service);
}
