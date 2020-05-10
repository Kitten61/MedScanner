package com.test.medscanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class SettingsFragment extends Fragment {
    private FloatingActionButton mThemeBlueButton;
    private FloatingActionButton mThemePurpleButton;
    private FloatingActionButton mThemeOrangeButton;
    private FloatingActionButton mThemeGreenButton;

    private LinearLayout mNavBarLayout;

    private Toolbar mToolbar;

    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        mToolbar = getActivity().findViewById(R.id.toolbar);

        mNavBarLayout = getActivity().findViewById(R.id.nav_header_main);

        mThemeBlueButton = root.findViewById(R.id.bBl);
        mThemePurpleButton = root.findViewById(R.id.bPu);
        mThemeOrangeButton = root.findViewById(R.id.bOr);
        mThemeGreenButton = root.findViewById(R.id.bGr);

        final int[][] states = new int[][] {
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked},
        };

        final NavigationView navigationView = getActivity().findViewById(R.id.nav_view);

        final SharedPreferences preferences = getActivity().getSharedPreferences("notes", Context.MODE_PRIVATE);

        mThemeBlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorBl));
                mNavBarLayout.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_bl));
                preferences.edit().putInt("colorPrimary", R.color.colorBl).putInt("colorGradient", R.drawable.side_nav_bar_bl).apply();

                ColorStateList colorStateList = new ColorStateList(
                        states,
                        new int[] {
                                getResources().getColor(R.color.colorBl),
                                getResources().getColor(R.color.colorBl),
                                getResources().getColor(R.color.nav_text_color),
                        }
                );
                navigationView.setItemTextColor(colorStateList);
                navigationView.setItemIconTintList(colorStateList);
            }
        });


        mThemeOrangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorOr));
                mNavBarLayout.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_or));
                preferences.edit().clear().putInt("colorPrimary", R.color.colorOr).putInt("colorGradient", R.drawable.side_nav_bar_or).apply();

                ColorStateList colorStateList = new ColorStateList(
                        states,
                        new int[] {
                                getResources().getColor(R.color.colorOr),
                                getResources().getColor(R.color.colorOr),
                                getResources().getColor(R.color.nav_text_color),
                        }
                );
                navigationView.setItemTextColor(colorStateList);
                navigationView.setItemIconTintList(colorStateList);
            }
        });


        mThemeGreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorGr));
                mNavBarLayout.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_gr));
                preferences.edit().clear().putInt("colorPrimary", R.color.colorGr).putInt("colorGradient", R.drawable.side_nav_bar_gr).apply();

                ColorStateList colorStateList = new ColorStateList(
                        states,
                        new int[] {
                                getResources().getColor(R.color.colorGr),
                                getResources().getColor(R.color.colorGr),
                                getResources().getColor(R.color.nav_text_color),
                        }
                );
                navigationView.setItemTextColor(colorStateList);
                navigationView.setItemIconTintList(colorStateList);
            }
        });


        mThemePurpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPu));
                mNavBarLayout.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_pu));
                preferences.edit().clear().putInt("colorPrimary", R.color.colorPu).putInt("colorGradient", R.drawable.side_nav_bar_pu).apply();

                ColorStateList colorStateList = new ColorStateList(
                        states,
                        new int[] {
                                getResources().getColor(R.color.colorPu),
                                getResources().getColor(R.color.colorPu),
                                getResources().getColor(R.color.nav_text_color),
                        }
                );
                navigationView.setItemTextColor(colorStateList);
                navigationView.setItemIconTintList(colorStateList);
            }
        });

        return root;
    }
}