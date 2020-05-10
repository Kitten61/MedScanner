package com.test.medscanner.presenterImpl;

import android.util.Log;

import com.test.medscanner.presenter.ClinicListPresenter;
import com.test.medscanner.repository.ClinicListRepository;
import com.test.medscanner.respositoryImpl.ClinicListRepositoryImpl;
import com.test.medscanner.util.Service;
import com.test.medscanner.view.ClinicListView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ClinicListPresenterImpl implements ClinicListPresenter {
    private ClinicListView mView;
    private ClinicListRepository mRepository;

    public ClinicListPresenterImpl(ClinicListView view) {
        mView = view;
        mRepository = new ClinicListRepositoryImpl();
    }

    @Override
    public void loadServices(String clinicID) {
        Disposable disposable = mRepository.loadServicesFromRealm(clinicID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            mView.loadServices(s);
                        },
                        e -> {

                        }
                );
    }

    @Override
    public void addFavorite(Service service) {
        mRepository.addFavoriteInRealm(service)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            Log.e("TESST", "Added star");
                        },
                        e -> {
                            Log.e("TESST", e.getMessage());
                        }
                );
    }

    @Override
    public void removeFavorite(Service service) {
        mRepository.removeFavoriteFromRealm(service)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            Log.e("TESST", "Removed star");
                        },
                        e -> {
                            Log.e("TESST", e.getMessage());
                        }
                );
    }
}
