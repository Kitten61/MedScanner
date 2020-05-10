package com.test.medscanner.presenterImpl;

import android.util.Log;

import com.test.medscanner.R;
import com.test.medscanner.presenter.SearchPresenter;
import com.test.medscanner.repository.SearchRepository;
import com.test.medscanner.respositoryImpl.SearchRepositoryImpl;
import com.test.medscanner.util.Service;
import com.test.medscanner.view.SearchFragmentView;
import com.yandex.mapkit.geometry.Point;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {
    private SearchFragmentView mView;
    private SearchRepository mRepository;

    public SearchPresenterImpl(SearchFragmentView mView) {
        this.mView = mView;
        this.mRepository = new SearchRepositoryImpl();
    }

    @Override
    public void searchServices(String query, long timestamp, Point location) {
        long tsLong = System.currentTimeMillis() / 1000;
        if (tsLong > timestamp) {
            Disposable disposable = mRepository.loadDataFromFirebase()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            s -> {
                                mView.updateTimeStamp(tsLong + 86400, query, location);
                            },
                            e -> {
                                mView.showErrorMessage(R.string.load_services_error);
                            }
                    );
        } else {
            loadServisesUsesLocalDB(query, location);
        }
    }

    @Override
    public void loadServisesUsesLocalDB(String query, Point location) {
        Disposable disposable = mRepository.loadDataFromLocalDB(query, location)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            mView.loadSearchQueryResult(s);
                        },
                        e -> {
                            mView.showErrorMessage(R.string.load_services_error);
                        }

                );
    }

    @Override
    public void addFavorite(Service service) {
        mRepository.addFavoriteInRealm(service)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void removeFavorite(Service service) {
        mRepository.removeFavoriteFromRealm(service)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
