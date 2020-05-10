package com.test.medscanner.view;


import com.test.medscanner.util.Clinic;
import com.yandex.mapkit.geometry.Point;

import java.util.List;

public interface SearchFragmentView {
    void loadSearchQueryResult(List<Clinic> clinic);
    void showErrorMessage(int id);
    void updateTimeStamp(long timestamp, String query, Point location);
}
