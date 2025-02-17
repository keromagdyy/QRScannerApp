package com.ebe.qrScannerApp.ui.utils.app;

import android.app.Application;

import com.ebe.qrScannerApp.data.roomDB.AppDatabase;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.getDatabase(getApplicationContext());
    }

}
