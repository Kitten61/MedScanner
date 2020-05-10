package com.test.medscanner.respositoryImpl;

import com.test.medscanner.repository.MapRepository;
import com.test.medscanner.util.Clinic;
import com.test.medscanner.util.FavoriteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.realm.Realm;

public class MapRepositoryImpl implements MapRepository {
    @Override
    public Single<List<Clinic>> loadAllClinics() {
        return Single.create(
                subscriber -> {
                    Realm realm = Realm.getDefaultInstance();
                    List<Clinic> resultList = realm.copyFromRealm(
                            realm.where(Clinic.class).findAll()
                    );
                    subscriber.onSuccess(resultList);
                }
        );
    }

    @Override
    public Single<List<Clinic>> loadFavClinics() {
        return Single.create(
                subscriber -> {

                    Realm realm = Realm.getDefaultInstance();
                    List<FavoriteService> favServices = realm.copyFromRealm(
                            realm.where(FavoriteService.class).findAll()
                    );

                    HashMap<String, Clinic> resultMap = new HashMap<>();

                    for (FavoriteService service : favServices) {
                        if (resultMap.containsKey(service.getHospital_id())) continue;
                        resultMap.put(service.getHospital_id(),
                                realm.copyFromRealm(
                                    realm.where(Clinic.class).equalTo("id", service.getHospital_id()).findFirst()
                                )
                        );
                    }
                    subscriber.onSuccess(new ArrayList<>(resultMap.values()));
                }
        );
    }
}
