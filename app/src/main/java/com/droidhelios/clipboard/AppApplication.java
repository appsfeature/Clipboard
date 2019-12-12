package com.droidhelios.clipboard;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.droidhelios.clipboard.database.DatabaseManager;

public class AppApplication extends Application {


    private static AppApplication instance;
    public static DatabaseManager databaseManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        databaseManager = DatabaseManager.newInstance(this);
    }

    public static AppApplication getInstance() {
        return instance;
    }

    public SharedPreferences getDefaultSharedPref() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }


}
