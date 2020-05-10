package com.test.medscanner.presenter;

import com.test.medscanner.util.Service;

public interface FavoritePresenter {
    void loadFavorites();
    void removeFavorite(Service service);
}
