package com.test.medscanner.respositoryImpl;

import com.test.medscanner.repository.FavoriteRepository;
import com.test.medscanner.util.Clinic;
import com.test.medscanner.util.FavoriteService;
import com.test.medscanner.util.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteRepositoryImpl implements FavoriteRepository {
    @Override
    public Single<List<Clinic>> loadFavoriteFromRealm() {
        return Single.create(
                subscriber -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<FavoriteService> favServRealmResults = realm.where(FavoriteService.class).findAll();

                    HashMap<String, Clinic> resultMap = new HashMap<>();

                    for (FavoriteService favService : favServRealmResults) {
                        String hospitalId = favService.getHospital_id();
                        Clinic clinic;
                        if (!resultMap.containsKey(hospitalId)) {
                            clinic = realm.copyFromRealm(realm.where(Clinic.class).equalTo("id", hospitalId).findFirst());
                            resultMap.put(hospitalId, clinic);
                        } else {
                            clinic = resultMap.get(hospitalId);
                        }

                        Service service = realm.copyFromRealm(realm.where(Service.class).equalTo("id", favService.getId()).findFirst());
                        service.setStarred(true);
                        clinic.putService(service);
                    }

                    subscriber.onSuccess(new ArrayList<>(resultMap.values()));
                }
        );
    }

    @Override
    public Single<Boolean> addFavoriteInRealm(Service service) {
        return Single.create(
                subscriber -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(r -> {
                        FavoriteService favoriteService = r.createObject(FavoriteService.class);
                        favoriteService.setHospital_id(service.getHospital_id());
                        favoriteService.setId(service.getId());
                    });
                    subscriber.onSuccess(true);
                }
        );
    }

    @Override
    public Single<Boolean> removeFavoriteFromRealm(Service service) {
        return Single.create(
                subscriber -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(
                            r -> {
                                realm.where(FavoriteService.class).equalTo("id", service.getId()).findFirst().deleteFromRealm();
                            }
                    );
                    subscriber.onSuccess(true);
                }
        );
    }
}
