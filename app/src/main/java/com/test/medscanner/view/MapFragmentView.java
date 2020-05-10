package com.test.medscanner.view;

import com.test.medscanner.util.Clinic;

import java.util.List;

public interface MapFragmentView {
    void loadPoints(List<Clinic> clinicList);
}
