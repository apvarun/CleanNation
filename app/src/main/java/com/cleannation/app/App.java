package com.cleannation.app;

/**
 * Created by varun on 15/12/15.
 */

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "xODHk0yo4gkH6icHADqg4kCMIEpUgtEnro5hSWsf", "HfhpMzFiOdmE4L1zCsIYdp7leCAv7RduX04fjGhm");
        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore


        //Parse.initialize(this);
        //ParseAnalytics.trackAppOpened(getApplicationInfo().getIntent());
    }
}