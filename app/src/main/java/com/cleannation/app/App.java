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
        Parse.initialize(this, "Application ID", "Client ID");
    }
}
