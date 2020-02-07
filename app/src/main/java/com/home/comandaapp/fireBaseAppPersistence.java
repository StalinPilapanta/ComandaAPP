package com.home.comandaapp;

import com.google.firebase.database.FirebaseDatabase;

public class fireBaseAppPersistence extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
