package com.test.medscanner.view;

import com.test.medscanner.util.Clinic;

import java.util.List;

public interface FavoriteView {
    void loadFavoritesInRecycler(List<Clinic> clinicList);
    void updateFavorites();
}
