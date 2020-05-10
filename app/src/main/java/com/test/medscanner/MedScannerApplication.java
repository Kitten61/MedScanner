package com.test.medscanner;

import androidx.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MedScannerApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("dbname.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
