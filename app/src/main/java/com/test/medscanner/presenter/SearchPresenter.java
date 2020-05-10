package com.test.medscanner.presenter;


import com.test.medscanner.util.Service;
import com.yandex.mapkit.geometry.Point;

public interface SearchPresenter {
    void searchServices(String query, long timestamp, Point location);
    void loadServisesUsesLocalDB(String query, Point location);
    void addFavorite(Service service);
    void removeFavorite(Service service);
}
