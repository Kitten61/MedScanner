package com.test.medscanner.repository;

import com.test.medscanner.util.Clinic;
import com.test.medscanner.util.Service;
import com.yandex.mapkit.geometry.Point;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SearchRepository extends FavoriteRepository {
    Single<Boolean> loadDataFromFirebase();
    Single<List<Clinic>> loadDataFromLocalDB(String query, Point location);
    Single<Boolean> addFavoriteInRealm(Service service);
    Single<Boolean> removeFavoriteFromRealm(Service service);
}
