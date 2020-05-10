package com.test.medscanner.presenterImpl;

import android.util.Log;

import com.test.medscanner.HomeFragment;
import com.test.medscanner.presenter.FavoritePresenter;
import com.test.medscanner.repository.FavoriteRepository;
import com.test.medscanner.respositoryImpl.SearchRepositoryImpl;
import com.test.medscanner.util.Service;
import com.test.medscanner.view.FavoriteView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenterImpl implements FavoritePresenter {
    private FavoriteView mView;
    private FavoriteRepository mRepository;

    public FavoritePresenterImpl(HomeFragment mView) {
        this.mView = mView;
        this.mRepository = new SearchRepositoryImpl();
    }

    @Override
    public void loadFavorites() {
        Disposable disposable = mRepository.loadFavoriteFromRealm()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            mView.loadFavoritesInRecycler(s);
                        },
                        e -> {
                            Log.e("TESSST", e.getMessage());
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
                            mView.updateFavorites();
                        },
                        e -> {
                            Log.e("TESST", e.getMessage());
                        }
                );
    }
}
