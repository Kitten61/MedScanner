package com.test.medscanner.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.test.medscanner.ClinicInfoFragment;
import com.test.medscanner.ClinicListFragment;
import com.test.medscanner.ClinicMapFragment;
import com.test.medscanner.util.Clinic;

import java.util.List;

public class SectionsPageAdapter extends FragmentStateAdapter {
    Clinic clinic;

    List<Fragment> fragments;

    public SectionsPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public SectionsPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Clinic clinic) {
        super(fragmentManager, lifecycle);
        this.clinic = clinic;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ClinicListFragment(clinic.getId());
            case 1:
                return new ClinicInfoFragment(clinic);
            case 2:
                return new ClinicMapFragment(clinic);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}