package com.test.medscanner;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;

    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(LOCATION_PERMS, 1337);
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_notes,
                R.id.nav_map, R.id.nav_settings, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, mNavController);


        SharedPreferences preferences = getSharedPreferences("notes", Context.MODE_PRIVATE);
        int colorPrimary = preferences.getInt("colorPrimary", R.color.colorOr);
        int colorGradient = preferences.getInt("colorGradient", R.drawable.side_nav_bar_or);

        View mNavBarLayout = navigationView.getHeaderView(0);
        mNavBarLayout.setBackground(getResources().getDrawable(colorGradient));

        toolbar.setBackgroundColor(getResources().getColor(colorPrimary));

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_checked},
                        new int[]{-android.R.attr.state_checked},
                },
                new int[] {
                        getResources().getColor(colorPrimary),
                        getResources().getColor(colorPrimary),
                        getResources().getColor(R.color.nav_text_color),
                }
        );

        navigationView.setItemTextColor(colorStateList);
        navigationView.setItemIconTintList(colorStateList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

}
