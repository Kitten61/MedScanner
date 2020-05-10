package com.test.medscanner.view;

import com.test.medscanner.util.Service;

import java.util.List;

public interface ClinicListView {
    void loadServices(List<Service> serviceList);
}
