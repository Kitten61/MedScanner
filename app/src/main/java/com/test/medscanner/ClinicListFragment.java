package com.test.medscanner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.medscanner.adapters.ServicesRecyclerAdapter;
import com.test.medscanner.presenter.ClinicListPresenter;
import com.test.medscanner.presenterImpl.ClinicListPresenterImpl;
import com.test.medscanner.util.Service;
import com.test.medscanner.view.ClinicListView;

import java.util.List;


public class ClinicListFragment extends Fragment implements ClinicListView {
    private ClinicListPresenter mPresenter;

    private String mClinicId;

    private ServicesRecyclerAdapter mAdapter;

    public ClinicListFragment() {
        super();
    }

    public ClinicListFragment(String clinicID) {
        mClinicId = clinicID;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = new ClinicListPresenterImpl(this);
        mAdapter = new ServicesRecyclerAdapter(
                new ServicesRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Service service) {

                    }

                    @Override
                    public void onStarClick(Service service) {
                        if (service.isStarred()) {
                            mPresenter.addFavorite(service);
                        } else {
                            mPresenter.removeFavorite(service);
                        }
                    }
                }
        );
        RecyclerView recyclerView = view.findViewById(R.id.services_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        mPresenter.loadServices(mClinicId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.services_list_layout, container, false);
    }

    @Override
    public void loadServices(List<Service> serviceList) {
        mAdapter.clear();
        mAdapter.addAll(serviceList);
    }
}
