package com.test.medscanner.presenterImpl;

import com.test.medscanner.presenter.MapPresenter;
import com.test.medscanner.repository.MapRepository;
import com.test.medscanner.respositoryImpl.MapRepositoryImpl;
import com.test.medscanner.view.MapFragmentView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapPresenterImpl implements MapPresenter {
    private MapFragmentView mView;
    private MapRepository mRepository;

    public MapPresenterImpl(MapFragmentView view) {
        mView = view;
        mRepository = new MapRepositoryImpl();
    }

    @Override
    public void loadFavoriteGeoPoints() {
        Disposable disposable = mRepository.loadFavClinics()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            mView.loadPoints(s);
                        },
                        e -> {}
                );
    }

    @Override
    public void loadAllGeoPoints() {
        Disposable disposable = mRepository.loadAllClinics()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            mView.loadPoints(s);
                        },
                        e -> {

                        }
                );
    }
}
