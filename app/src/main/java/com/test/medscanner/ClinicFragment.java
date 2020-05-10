package com.test.medscanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;
import com.test.medscanner.adapters.SectionsPageAdapter;
import com.test.medscanner.util.Clinic;

public class ClinicFragment extends Fragment {
    private Clinic mClinic;

    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        mClinic = args.getParcelable("clinic");

        String name = mClinic.getName();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(name);

        TextView clinicName = view.findViewById(R.id.clinic_name);
        TextView clinicAdress = view.findViewById(R.id.clinic_adress);
        ImageView clinicPhoto = view.findViewById(R.id.clinic_photo);


        Picasso.get().load(mClinic.getPhoto()).into(clinicPhoto);
        clinicAdress.setText(mClinic.getWebSite().replace("https://", ""). replace("http://", ""));
        clinicName.setText(name);

        mViewPager = view.findViewById(R.id.viewpager);
        mTabLayout = view.findViewById(R.id.tabLayout);
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager(), getLifecycle(), mClinic);
        mViewPager.setAdapter(adapter);

        String[] lables = {
                "Все услуги",
                "Информация",
                "Карта"
        };


        new TabLayoutMediator(mTabLayout, mViewPager,
                (tab, position) ->
                        tab.setText(lables[position])
        ).attach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clinic, container, false);
    }
}
