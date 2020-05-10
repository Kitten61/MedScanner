package com.test.medscanner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.medscanner.util.Clinic;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.runtime.image.ImageProvider;

public class ClinicMapFragment extends Fragment {
    private Clinic mClinic;
    private MapView mMapView;

    public ClinicMapFragment() {}

    public ClinicMapFragment(Clinic clinic) {
        this.mClinic = clinic;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Point location = new Point(mClinic.getLatitude(), mClinic.getLongtitude());
        mMapView.getMap().setRotateGesturesEnabled(false);
        mMapView.getMap().setZoomGesturesEnabled(false);
        mMapView.getMap().setScrollGesturesEnabled(false);
        mMapView.getMap().setTiltGesturesEnabled(false);
        mMapView.getMap().move(
                new CameraPosition(location, 14.0f, 0.0f, 0.0f));

        MapObjectCollection mapObjects = mMapView.getMap().getMapObjects();
        mapObjects.addPlacemark(
                location,
                ImageProvider.fromResource(getContext(), R.drawable.search_result));
    }

    @Override
    public void onStart() {
        mMapView.onStart();
        MapKitFactory.getInstance().onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        mMapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MapKitFactory.setApiKey("4b48d1fe-f1c5-41e3-a0bc-48577c62d5cc");
        MapKitFactory.initialize(getActivity());
        SearchFactory.initialize(getActivity());
        View view = inflater.inflate(R.layout.clinic_map_fragment, container, false);
        mMapView = view.findViewById(R.id.map);
        return view;
    }

}