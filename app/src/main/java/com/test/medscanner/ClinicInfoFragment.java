package com.test.medscanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.medscanner.util.Clinic;

public class ClinicInfoFragment extends Fragment {
    private Clinic clinic;

    public ClinicInfoFragment(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView name = view.findViewById(R.id.clinic_name);
        name.setText("Название: " + clinic.getName());

        TextView adress = view.findViewById(R.id.clinic_adress);
        adress.setText("Адрес: " + clinic.getAdress());

        TextView phone = view.findViewById(R.id.clinic_phone);
        phone.setText("Телефон: " + clinic.getPhoneNumber());

        ViewGroup schedule = view.findViewById(R.id.clinic_schedule);
        schedule.removeAllViews();
        for (String scheduleElement : clinic.getSchedule()) {
            TextView scheduleView = new TextView(getContext());
            scheduleView.setText(scheduleElement);
            schedule.addView(scheduleView);
        }

        Button submit = view.findViewById(R.id.clinic_submit);
        submit.setOnClickListener(
                l -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(clinic.getWebSite()));
                    startActivity(intent);
                }
        );

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clinic_info_layout, container, false);
    }
}
