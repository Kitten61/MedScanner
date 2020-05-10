package com.test.medscanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.medscanner.adapters.SearchRecyclerAdapter;
import com.test.medscanner.presenter.SearchPresenter;
import com.test.medscanner.presenterImpl.SearchPresenterImpl;
import com.test.medscanner.util.Clinic;
import com.test.medscanner.util.Service;
import com.test.medscanner.view.SearchFragmentView;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class SearchFragment extends Fragment implements SearchFragmentView {
    private SearchPresenter mPresenter;

    private RecyclerView mSearchRecycler;
    private SearchView mSearchView;

    private SharedPreferences mSharedPreferences;

    private SearchRecyclerAdapter mSearchAdapter;

    private NavController mNavController;

    private View mRoot;

    private ViewGroup mPlaceholder;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("result", mSearchAdapter.getAll());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = new SearchPresenterImpl(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_search, container, false);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);


        Realm.init(getContext());

        mPlaceholder = mRoot.findViewById(R.id.placeholder);

        mSharedPreferences = getActivity().getSharedPreferences("comtestmedscanner", Context.MODE_PRIVATE);

        mSearchView = mRoot.findViewById(R.id.search_text);
        mSearchRecycler = mRoot.findViewById(R.id.search_results);
        mSearchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchAdapter = new SearchRecyclerAdapter(new SearchRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Clinic clinic) {
                Bundle args = new Bundle();
                args.putParcelable("clinic", clinic);
                mNavController.navigate(R.id.action_nav_search_to_clinicFragment, args);
            }

            @Override
            public void onStarClick(Service service) {
                if (service.isStarred()) {
                    mPresenter.addFavorite(service);
                } else {
                    mPresenter.removeFavorite(service);
                }
            }
        }, getLayoutInflater());
        mSearchRecycler.setAdapter(mSearchAdapter);

        if (savedInstanceState != null) {
            ArrayList<Clinic> list = savedInstanceState.getParcelableArrayList("result");
            mPlaceholder.setVisibility(View.INVISIBLE);
            mSearchAdapter.addAll(list);
        }

        MapKitFactory.setApiKey("4b48d1fe-f1c5-41e3-a0bc-48577c62d5cc");
        MapKitFactory.initialize(getActivity());
        LocationManager locationManager = MapKitFactory.getInstance().createLocationManager();
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                initSearcher(location.getPosition());
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {}
        };

        locationManager.requestSingleUpdate(locationListener);

        return mRoot;
    }

    private void initSearcher(Point location) {
        TextView[] popViews = {
                mRoot.findViewById(R.id.text_pop1),
                mRoot.findViewById(R.id.text_pop2),
                mRoot.findViewById(R.id.text_pop3),
                mRoot.findViewById(R.id.text_pop4),
                mRoot.findViewById(R.id.text_pop5),
        };
        for (TextView popView : popViews) {
            popView.setOnClickListener(pop -> mSearchView.setQuery(popView.getText(), true));
        }
        mSearchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        long timestamp = mSharedPreferences.getLong("timestamp", 0);
                        mPresenter.searchServices(s, timestamp, location);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                }
        );
    }

    @Override
    public void loadSearchQueryResult(List<Clinic> clinic) {
        if (clinic.size() == 0) {
            mPlaceholder.setVisibility(View.VISIBLE);
        } else {
            mPlaceholder.setVisibility(View.INVISIBLE);
        }
        mSearchAdapter.clear();
        mSearchAdapter.addAll(clinic);
    }

    @Override
    public void showErrorMessage(int id) {
        Toast.makeText(getContext(), getString(id), Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateTimeStamp(long timestamp, String query, Point location) {
        mSharedPreferences.edit().putLong("timestamp", timestamp).apply();
        mPresenter.loadServisesUsesLocalDB(query, location);
    }
}