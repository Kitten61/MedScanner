package com.test.medscanner.respositoryImpl;

import com.test.medscanner.repository.ClinicListRepository;
import com.test.medscanner.util.FavoriteService;
import com.test.medscanner.util.Service;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.realm.Realm;

public class ClinicListRepositoryImpl extends FavoriteRepositoryImpl implements ClinicListRepository {
    @Override
    public Single<List<Service>> loadServicesFromRealm(String clinicID) {
        return Single.create(
                subscriber -> {
                    Realm realm = Realm.getDefaultInstance();

                    List<Service> resultList = realm.copyFromRealm(realm.where(Service.class).equalTo("hospital_id", clinicID).findAll());

                    for (Service service : resultList) {
                        FavoriteService favService = realm.where(FavoriteService.class).equalTo("id", service.getId()).findFirst();
                        if (favService != null) {
                            service.setStarred(true);
                        }
                    }
                    subscriber.onSuccess(resultList);
                }
        );
    }
}
