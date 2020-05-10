package com.test.medscanner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.medscanner.presenter.MapPresenter;
import com.test.medscanner.presenterImpl.MapPresenterImpl;
import com.test.medscanner.util.Clinic;
import com.test.medscanner.view.MapFragmentView;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateSource;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.VisibleRegion;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements MapFragmentView, CameraListener {
    private MapPresenter mPresenter;

    private MapView mMapView;

    private CheckBox mFavCheckBox;

    List<Clinic> mClinics;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = new MapPresenterImpl(this);
        mClinics = new ArrayList<>();
        mMapView = view.findViewById(R.id.map);
        mMapView.getMap().setRotateGesturesEnabled(false);
        mMapView.getMap().addCameraListener(this);

        mFavCheckBox = view.findViewById(R.id.only_fav_checkbox);

        mFavCheckBox.setOnCheckedChangeListener(
                (compoundButton, b) -> {
                    if (b) {
                        mPresenter.loadFavoriteGeoPoints();
                    } else {
                        mPresenter.loadAllGeoPoints();
                    }
                }
        );

        LocationManager locationManager = MapKitFactory.getInstance().createLocationManager();
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                mMapView.getMap().move(
                        new CameraPosition(location.getPosition(), 14.0f, 0.0f, 0.0f));
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {

            }
        };

        locationManager.requestSingleUpdate(locationListener);

        mPresenter.loadAllGeoPoints();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MapKitFactory.setApiKey("4b48d1fe-f1c5-41e3-a0bc-48577c62d5cc");
        MapKitFactory.initialize(getActivity());
        return inflater.inflate(R.layout.fragment_map, container, false);
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

    @Override
    public void loadPoints(List<Clinic> clinicList) {
        this.mClinics.clear();
        this.mClinics.addAll(clinicList);
        drawPoints();
    }

    boolean isPointInVisibleRegion(Point point, VisibleRegion visibleRegion) {
        return Math.abs(visibleRegion.getBottomLeft().getLongitude()) < Math.abs(point.getLongitude())
                && Math.abs(visibleRegion.getTopRight().getLongitude()) > Math.abs(point.getLongitude())
                && Math.abs(visibleRegion.getBottomLeft().getLatitude()) < Math.abs(point.getLatitude())
                && Math.abs(visibleRegion.getTopRight().getLatitude()) > Math.abs(point.getLatitude());
    };

    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateSource cameraUpdateSource, boolean b) {
        drawPoints();
    }

    private void drawPoints() {
        MapObjectCollection mapObjects = mMapView.getMap().getMapObjects();
        double zoom = mMapView.getMap().getCameraPosition().getZoom();
        mapObjects.clear();
        VisibleRegion visibleRegion = mMapView.getFocusRegion();
        for (Clinic clinic : mClinics) {
            Point location = new Point(clinic.getLatitude(), clinic.getLongtitude());
            if (!isPointInVisibleRegion(location, visibleRegion)) continue;
            if (zoom > 16.5) {
                View view = getLayoutInflater().inflate(R.layout.object_card, null, false);
                TextView t = view.findViewById(R.id.name);
                t.setText(clinic.getName());
                ViewProvider viewProvider = new ViewProvider(view);
                mapObjects.addPlacemark(location, viewProvider);
            } else {
                mapObjects.addPlacemark(
                        location,
                        ImageProvider.fromResource(getContext(), R.drawable.search_result));
            }
        }
    }
}