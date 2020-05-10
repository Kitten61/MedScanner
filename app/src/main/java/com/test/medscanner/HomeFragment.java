package com.test.medscanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.medscanner.adapters.SearchRecyclerAdapter;
import com.test.medscanner.presenter.FavoritePresenter;
import com.test.medscanner.presenterImpl.FavoritePresenterImpl;
import com.test.medscanner.util.Clinic;
import com.test.medscanner.util.Service;
import com.test.medscanner.view.FavoriteView;

import java.util.List;


public class HomeFragment extends Fragment implements FavoriteView {
    private FavoritePresenter mPresenter;

    private RecyclerView mFavoriteRecycler;

    private SearchRecyclerAdapter mAdapter;

    private NavController mNavController;

    private ViewGroup mPlaceholder;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = new FavoritePresenterImpl(this);
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        mFavoriteRecycler = view.findViewById(R.id.favorite_recycler);

        mPlaceholder = view.findViewById(R.id.favorite_placeholder);

        mAdapter = new SearchRecyclerAdapter(
                new SearchRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Clinic clinic) {
                        Bundle args = new Bundle();
                        args.putParcelable("clinic", clinic);
                        mNavController.navigate(R.id.action_nav_home_to_clinicFragment, args);
                    }

                    @Override
                    public void onStarClick(Service service) {
                        mPresenter.removeFavorite(service);
                    }
                }
                , getLayoutInflater());
        mFavoriteRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mFavoriteRecycler.setAdapter(mAdapter);

        mPresenter.loadFavorites();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
   }

    @Override
    public void loadFavoritesInRecycler(List<Clinic> clinicList) {
        if (clinicList.size() == 0) {
            mPlaceholder.setVisibility(View.VISIBLE);
        } else {
            mPlaceholder.setVisibility(View.INVISIBLE);
        }
        mAdapter.clear();
        mAdapter.addAll(clinicList);
    }

    @Override
    public void updateFavorites() {
        mPresenter.loadFavorites();
    }
}