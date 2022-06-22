package com.example.superlist.superlist;

import android.app.Application;

import com.example.superlist.superlist.Firebase.DataManager;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Initiate FireBase Managers
        DataManager.initHelper();
    }
}
